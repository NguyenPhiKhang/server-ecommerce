package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.CosineSimilarityDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.RatingRSDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
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
        List<ProductItemDTO> list = new ArrayList<>();
        if (userId == 0 || !(ratingService.checkUserIsRated(userId) > 0))
            list = productService.productTopRating((page - 1) * 10).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList());
        else {
            int n_users = ratingService.numberUserInRatings();
            int n_products = ratingService.numberProductInRatings();

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
            for (AVGRatedProductDTO product_avg : listAVG) {
                for (int user_id : list_users) {
                    int index = listRatingNormalized.indexOf(new RatingRSDTO(user_id, product_avg.getProductId()));
                    if (index > -1) {
                        RatingRSDTO rt = listRatingNormalized.get(index);
                        rt.setValue(rt.getValue() - product_avg.getAvgRated());
                        System.out.println(rt);
                    }
                }
            }

            List<CosineSimilarityDTO> cosSimilarities = new ArrayList<>();

            // calc cosine similarity
            for (int i = 0; i < list_product.size() - 1; i++) {
                int product_id1 = list_product.get(i);
                List<RatingRSDTO> user_rated_product1 = listRatingNormalized.stream().filter(value -> value.getProductId() == product_id1).collect(Collectors.toList());
                for (int j = i + 1; j < list_product.size(); j++) {
                    int product_id2 = list_product.get(j);
                    List<RatingRSDTO> user_rated_product2 = listRatingNormalized.stream().filter(value -> value.getProductId() == product_id2).collect(Collectors.toList());

                    double pd1_dot_pd2 = 0.0;
                    for (RatingRSDTO a : user_rated_product1) {
                        Optional<RatingRSDTO> ratingRS2 = user_rated_product2.stream().filter(value -> value.getUserId() == a.getUserId()).findFirst();
                        if (ratingRS2.isPresent()) {
                            pd1_dot_pd2 += (a.getValue() * ratingRS2.get().getValue());
                        }
                    }

                    double square_root_pd1 = Math.sqrt(user_rated_product1.stream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
                    double square_root_pd2 = Math.sqrt(user_rated_product2.stream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
                    double norm_pd1_mul_norm_pd2 = square_root_pd1 * square_root_pd2;

                    if (norm_pd1_mul_norm_pd2 == 0.0)
                        System.out.println(pd1_dot_pd2 / norm_pd1_mul_norm_pd2);
                }
            }


        }
        return ResponseEntity.ok().body(list);
    }
}
