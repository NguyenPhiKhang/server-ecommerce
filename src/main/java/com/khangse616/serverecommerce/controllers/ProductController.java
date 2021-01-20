package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.CosineSimilarityDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.RatingRSDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.RecommendForUser;
import com.khangse616.serverecommerce.mapper.ProductDetailMapper;
import com.khangse616.serverecommerce.mapper.ProductItemDTOMapper;
import com.khangse616.serverecommerce.mapper.RatingRSDTOMapper;
import com.khangse616.serverecommerce.services.ProductService;
import com.khangse616.serverecommerce.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDetailDTO> getProductById(@PathVariable int id) {
        ProductDetailDTO productDetailDTO = new ProductDetailMapper().mapRow(productService.findProductById(id));
        return ResponseEntity.ok().body(productDetailDTO);
    }

    @GetMapping("/cat/{path}/products")
    public ResponseEntity<List<ProductItemDTO>> getProductsByCategoryPath(@PathVariable("path") String path, @RequestParam("p") int page) {
        List<ProductItemDTO> list = productService.findProductByCategory(path, (page - 1) * 20).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/recommend/top-rating/{userId}")
    public ResponseEntity<List<ProductItemDTO>> getProductTopRating(@PathVariable("userId") int userId, @RequestParam(value = "p", defaultValue = "1") int page) {
        long startTime = new Date().getTime();
        List<ProductItemDTO> list = (userId == 0 || !(ratingService.checkUserIsRated(userId) > 0) ?
                productService.productTopRating((page - 1) * 10).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList()) :
                productService.productRecommendForUser(userId).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList()));

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        return ResponseEntity.ok().body(list);
    }


    public void recommendproductforuser() {
        //            int n_users = ratingService.numberUserInRatings();
//            int n_products = ratingService.numberProductInRatings();

        List<Integer> list_users = ratingService.getUsersRated();
        List<Integer> list_product = ratingService.getProductsRated();

//            List<Integer> list_users = new ArrayList<>();
//            list_users.add(0);
//            list_users.add(1);
//            list_users.add(2);
//            list_users.add(3);
//            list_users.add(4);
//            list_users.add(5);
//            list_users.add(6);
//            List<Integer> list_product = new ArrayList<>();
//            list_product.add(0);
//            list_product.add(1);
//            list_product.add(2);
//            list_product.add(3);
//            list_product.add(4);
        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> new RatingRSDTOMapper().mapRow(value)).collect(Collectors.toList());
//            List<RatingRSDTO> listRatingRS = new ArrayList<>();
//            listRatingRS.add(new RatingRSDTO(0, 0, 5.));
//            listRatingRS.add(new RatingRSDTO(0, 1, 4.));
//            listRatingRS.add(new RatingRSDTO(0, 3, 2.));
//            listRatingRS.add(new RatingRSDTO(0, 4, 2.));
//            listRatingRS.add(new RatingRSDTO(1, 0, 5.));
//            listRatingRS.add(new RatingRSDTO(1, 2, 4.));
//            listRatingRS.add(new RatingRSDTO(1, 3, 2.));
//            listRatingRS.add(new RatingRSDTO(1, 4, 1.));
//            listRatingRS.add(new RatingRSDTO(2, 0, 2.));
//            listRatingRS.add(new RatingRSDTO(2, 2, 1.));
//            listRatingRS.add(new RatingRSDTO(2, 3, 3.));
//            listRatingRS.add(new RatingRSDTO(2, 4, 4.));
//            listRatingRS.add(new RatingRSDTO(3, 0, 1.));
//            listRatingRS.add(new RatingRSDTO(3, 1, 1.));
//            listRatingRS.add(new RatingRSDTO(3, 3, 4.));
//            listRatingRS.add(new RatingRSDTO(4, 0, 1.));
//            listRatingRS.add(new RatingRSDTO(4, 3, 4.));
//            listRatingRS.add(new RatingRSDTO(5, 1, 2.));
//            listRatingRS.add(new RatingRSDTO(5, 2, 1.));
//            listRatingRS.add(new RatingRSDTO(6, 2, 1.));
//            listRatingRS.add(new RatingRSDTO(6, 3, 4.));
//            listRatingRS.add(new RatingRSDTO(6, 4, 5.));


        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

        HashMap<Integer, Float> list_avgProductRated = new HashMap<>();

        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();
//            List<AVGRatedProductDTO> listAVG = new ArrayList<>();
//            listAVG.add(new AVGRatedProductDTO(0, 2.8));
//            listAVG.add(new AVGRatedProductDTO(1, 7.0 / 3.0));
//            listAVG.add(new AVGRatedProductDTO(2, 1.75));
//            listAVG.add(new AVGRatedProductDTO(3, 19.0 / 6.0));
//            listAVG.add(new AVGRatedProductDTO(4, 3.0));

        // normalized utility matrix
        System.out.println(listAVG.size());
        int size_list_avg = listAVG.size();
        int size_list_user = list_users.size();
        for (int i = 0; i < size_list_avg; i++) {
            int pd_id = list_product.get(i);
//                List<RatingRSDTO> list_user_rated_pd = listRatingNormalized.parallelStream().filter(value->value.getProductId()==pd_id).collect(Collectors.toList());
//                int size_list_user_rated_pd = list_user_rated_pd.size();
//                for (int j = 0; j < size_list_user_rated_pd; j++) {
////                    Optional<RatingRSDTO> index = listRatingNormalized.stream().filter(value -> value.equals(new RatingRSDTO(user_id, product_avg.getProductId()))).findFirst();
////                    int index = listRatingNormalized.indexOf(new RatingRSDTO(list_users.get(j), listAVG.get(i).getProductId()));
////                    if (index > -1) {
//                        RatingRSDTO rt = list_user_rated_pd.get(j);
//                        rt.setValue(rt.getValue() - listAVG.get(i).getAvgRated());
//                        System.out.println(rt);
////                    }
//                }
            double avg_pd = listAVG.get(i).getAvgRated();
            listRatingNormalized.parallelStream().filter(value -> value.getProductId() == pd_id).forEach(value -> {
//                    RatingRSDTO rt = value;
                value.setValue(value.getValue() - avg_pd);
                System.out.println(value);
            });
        }

        List<CosineSimilarityDTO> cosSimilarities = new ArrayList<>();

        // calc cosine similarity
        for (int i = 0; i < size_list_avg - 1; i++) {
            int product_id1 = list_product.get(i);
            List<RatingRSDTO> user_rated_product1 = listRatingNormalized.parallelStream().filter(value -> value.getProductId() == product_id1).collect(Collectors.toList());
            for (int j = i + 1; j < size_list_avg; j++) {
                int product_id2 = list_product.get(j);
                List<RatingRSDTO> user_rated_product2 = listRatingNormalized.parallelStream().filter(value -> value.getProductId() == product_id2).collect(Collectors.toList());

                double pd1_dot_pd2 = 0.0;
                int size_user_rated_pd1 = user_rated_product1.size();
                for (int n = 0; n < size_user_rated_pd1; n++) {
                    RatingRSDTO user_rated = user_rated_product1.get(n);
                    Optional<RatingRSDTO> ratingRS2 = user_rated_product2.parallelStream().filter(value -> value.getUserId() == user_rated.getUserId()).findFirst();
                    if (ratingRS2.isPresent()) {
                        pd1_dot_pd2 += (user_rated.getValue() * ratingRS2.get().getValue());
                    }
                }

                double square_root_pd1 = Math.sqrt(user_rated_product1.parallelStream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
                double square_root_pd2 = Math.sqrt(user_rated_product2.parallelStream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
                double norm_pd1_mul_norm_pd2 = square_root_pd1 * square_root_pd2;

                double cosineSimilarity = norm_pd1_mul_norm_pd2 != 0.0 ? pd1_dot_pd2 / norm_pd1_mul_norm_pd2 : -1;

                CosineSimilarityDTO a = new CosineSimilarityDTO(product_id1, product_id2, cosineSimilarity);
                System.out.println(a);
                cosSimilarities.add(a);
            }
        }

        for (CosineSimilarityDTO cs : cosSimilarities) {
            System.out.println(cs.toString());
        }

        List<RecommendForUser> recommendForUserList = new ArrayList<>();

        // Rating Prediction
        for (int i = 0; i < size_list_user; i++) {
            int user_id = list_users.get(i);
            List<Integer> list_pd_user_rated = list_product.parallelStream().filter(value -> listRatingNormalized.contains(new RatingRSDTO(user_id, value))).collect(Collectors.toList());
            List<Integer> list_pd_user_unrated = list_product.parallelStream().filter(value -> !list_pd_user_rated.contains(value)).collect(Collectors.toList());
            int size_unrated = list_pd_user_unrated.size();
            int size_rated = list_pd_user_rated.size();
            StringBuilder listProductRS = new StringBuilder();
            for (int j = 0; j < size_unrated; j++) {
//                    for(int k = 0; k<size_rated; k++){
//                        int pd_id = list_pd_user_rated.get(k).getProductId();
                int pd_unrated_id = list_pd_user_unrated.get(j);
                List<CosineSimilarityDTO> list_cos_of_user = cosSimilarities.parallelStream().
                        filter(value -> (value.getRow() == pd_unrated_id && list_pd_user_rated.contains(value.getColumn())) || (list_pd_user_rated.contains(value.getRow()) && value.getColumn() == pd_unrated_id)).
                        sorted(Comparator.comparingDouble(CosineSimilarityDTO::getSimilarity).reversed()).
                        limit(2).collect(Collectors.toList());

                List<RatingRSDTO> list_normalize_of_user = listRatingNormalized.parallelStream().
                        filter(value -> value.getUserId() == user_id && (list_cos_of_user.contains(new CosineSimilarityDTO(pd_unrated_id, value.getProductId())) ||
                                list_cos_of_user.contains(new CosineSimilarityDTO(value.getProductId(), pd_unrated_id)))).
                        limit(2).collect(Collectors.toList());

                double a = list_cos_of_user.get(0).getRow() == list_normalize_of_user.get(0).getProductId() || list_cos_of_user.get(0).getColumn() == list_normalize_of_user.get(0).getProductId() ?
                        list_cos_of_user.get(0).getSimilarity() * list_normalize_of_user.get(0).getValue() +
                                list_cos_of_user.get(1).getSimilarity() * list_normalize_of_user.get(1).getValue() :
                        list_cos_of_user.get(0).getSimilarity() * list_normalize_of_user.get(1).getValue() +
                                list_cos_of_user.get(1).getSimilarity() * list_normalize_of_user.get(0).getValue();
                double b = Math.abs(list_cos_of_user.get(0).getSimilarity()) + Math.abs(list_cos_of_user.get(1).getSimilarity());

//                    System.out.println(a/b);
                if (a / b > 0) {
                    listProductRS.append(pd_unrated_id).append(" (").append((double) Math.round((a / b) * 100) / 100).append("), ");
                }
            }
            recommendForUserList.add(new RecommendForUser(user_id, listProductRS.toString()));
        }

        recommendForUserList.forEach(System.out::println);
        System.out.println("done");
    }
}
