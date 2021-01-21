package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.models.CosineSimilarity;
import com.khangse616.serverecommerce.dto.RecommendSystem.RatingRSDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.RecommendForUser;
import com.khangse616.serverecommerce.mapper.ProductDetailMapper;
import com.khangse616.serverecommerce.mapper.ProductItemDTOMapper;
import com.khangse616.serverecommerce.mapper.RatingRSDTOMapper;
import com.khangse616.serverecommerce.models.RecommendRating;
import com.khangse616.serverecommerce.services.CosineSimilarityService;
import com.khangse616.serverecommerce.services.ProductService;
import com.khangse616.serverecommerce.services.RatingService;
import com.khangse616.serverecommerce.services.RecommendRatingService;
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

    @Autowired
    private CosineSimilarityService cosineSimilarityService;

    @Autowired
    private RecommendRatingService recommendRatingService;

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
        List<ProductItemDTO> list = new ArrayList<>();

        if (userId == 0 || !(ratingService.checkUserIsRated(userId) > 0)) {
            list = productService.productTopRating((page - 1) * 10).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList());
        } else {
            if (!recommendRatingService.checkExistUser(userId)) {
                recommend_product_for_user(userId);
            }
            list = productService.productRecommendForUser(userId).stream().map(value -> new ProductItemDTOMapper().mapRow(value)).collect(Collectors.toList());
        }


        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/recommend/create-cosine-similarity")
    public ResponseEntity<String> createCosineSimilarity() {
        List<Integer> list_users = ratingService.getUsersRated();
        List<Integer> list_product = ratingService.getProductsRated();

        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> new RatingRSDTOMapper().mapRow(value)).collect(Collectors.toList());

        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();

        int size_list_avg = listAVG.size();
        int size_list_user = list_users.size();
        for (int i = 0; i < size_list_avg; i++) {
            int pd_id = list_product.get(i);
            double avg_pd = listAVG.get(i).getAvgRated();
            listRatingNormalized.parallelStream().filter(value -> value.getProductId() == pd_id).forEach(value -> {
                value.setValue(value.getValue() - avg_pd);
            });
        }

        List<CosineSimilarity> cosSimilarities = new ArrayList<>();

        // calc cosine similarity
        for (int i = size_list_avg - 1; i > 0; i--) {
            int product_id1 = list_product.get(i);
            List<RatingRSDTO> user_rated_product1 = calcUserRatedProduct(listRatingNormalized, product_id1);
            for (int j = i - 1; j >= 0; j--) {
                int product_id2 = list_product.get(j);
                List<RatingRSDTO> user_rated_product2 = calcUserRatedProduct(listRatingNormalized, product_id2);

                double pd1_dot_pd2 = p1_dot_p2(user_rated_product1, user_rated_product2);
                double norm_pd1 = calc_norm(user_rated_product1);
                double norm_pd2 = calc_norm(user_rated_product2);
                double norm_pd1_mul_norm_pd2 = norm_pd1 * norm_pd2;
//
                double cosineSimilarity = norm_pd1_mul_norm_pd2 != 0.0 ? pd1_dot_pd2 / norm_pd1_mul_norm_pd2 : -1;
//
                CosineSimilarity a = new CosineSimilarity(product_id1, product_id2, cosineSimilarity);
                cosSimilarities.add(a);
                System.out.println("i: " + i + " - j: " + j);
            }
        }

        cosineSimilarityService.removeAll();
        cosineSimilarityService.saveAll(cosSimilarities);
        return ResponseEntity.ok().body("done");
    }

    @GetMapping("/create-prediction-rating/{userId}")
    public ResponseEntity<RecommendRating> recommend_product_for_user(@PathVariable("userId") int user_id) {
        long startTime = new Date().getTime();

        List<Integer> list_users = ratingService.getUsersRated();
        List<Integer> list_product = ratingService.getProductsRated();

        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> new RatingRSDTOMapper().mapRow(value)).collect(Collectors.toList());

        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();

        // normalized utility matrix
        System.out.println(listAVG.size());
        int size_list_avg = listAVG.size();
        for (int i = 0; i < size_list_avg; i++) {
            int pd_id = list_product.get(i);
            double avg_pd = listAVG.get(i).getAvgRated();
            listRatingNormalized.parallelStream().filter(value -> value.getProductId() == pd_id).forEach(value -> {
                value.setValue(value.getValue() - avg_pd);
            });
        }

        List<CosineSimilarity> cosSimilarities = cosineSimilarityService.getAll();
        Map<Integer, Double> mapPredictionProduct = new HashMap<>();
//        // Rating Prediction
        List<Integer> list_pd_user_rated = new ArrayList<>();
        List<Integer> list_pd_user_unrated = new ArrayList<>();
        divide_rated_unrated(listRatingNormalized, list_product, user_id, list_pd_user_rated, list_pd_user_unrated);
        StringBuilder listProductRS = new StringBuilder();
        StringBuilder listProductRS_Show = new StringBuilder();

        list_pd_user_unrated.parallelStream().forEach(value -> {
            List<CosineSimilarity> list_cos_of_user = new ArrayList<>();
            List<RatingRSDTO> list_normalize_of_user = new ArrayList<>();
            list_cos_of_user = top_k_cosine_similarity_of_user(2, cosSimilarities, value, list_pd_user_rated);

            list_normalize_of_user = top_k_normalized_corresponding_top_k_cosine_similarity(
                    user_id, list_cos_of_user, listRatingNormalized, value);

            double a = list_cos_of_user.get(0).getRow() == list_normalize_of_user.get(0).getProductId() || list_cos_of_user.get(0).getColumn() == list_normalize_of_user.get(0).getProductId() ?
                    list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(0).getValue() +
                            list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(1).getValue() :
                    list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(1).getValue() +
                            list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(0).getValue();
            double b = Math.abs(list_cos_of_user.get(0).getCosSimilarity()) + Math.abs(list_cos_of_user.get(1).getCosSimilarity());

            if (a / b > 0) {
                mapPredictionProduct.put(value, a / b);
            }
        });

        mapPredictionProduct.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).forEach(value -> {
            listProductRS.append(value.getKey()).append("-");
            listProductRS_Show.append(value.getKey()).append(" - ").append(value.getValue()).append(";");
        });
        if (!recommendRatingService.checkExistUser(user_id))
            recommendRatingService.save(new RecommendRating(user_id, listProductRS.deleteCharAt(listProductRS.length() - 1).toString()));
        else{
            RecommendRating recommendRating = recommendRatingService.getById(user_id);
            recommendRating.setProducts(listProductRS.toString());
            recommendRatingService.save(recommendRating);
        }

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        System.out.println("done");

        return ResponseEntity.ok().body(new RecommendRating(user_id, listProductRS_Show.toString()));
    }

    @GetMapping("/test-recommend-movie")
    public ResponseEntity<List<RecommendForUser>> recommend_product_for_user() {
        long startTime = new Date().getTime();

//        List<Integer> list_users = ratingService.getUsersRated();
//        List<Integer> list_product = ratingService.getProductsRated();

        List<Integer> list_users = new ArrayList<>();
        list_users.add(0);
        list_users.add(1);
        list_users.add(2);
        list_users.add(3);
        list_users.add(4);
        list_users.add(5);
        list_users.add(6);
        List<Integer> list_product = new ArrayList<>();
        list_product.add(0);
        list_product.add(1);
        list_product.add(2);
        list_product.add(3);
        list_product.add(4);
//        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> new RatingRSDTOMapper().mapRow(value)).collect(Collectors.toList());
        List<RatingRSDTO> listRatingRS = new ArrayList<>();
        listRatingRS.add(new RatingRSDTO(0, 0, 5.));
        listRatingRS.add(new RatingRSDTO(0, 1, 4.));
        listRatingRS.add(new RatingRSDTO(0, 3, 2.));
        listRatingRS.add(new RatingRSDTO(0, 4, 2.));
        listRatingRS.add(new RatingRSDTO(1, 0, 5.));
        listRatingRS.add(new RatingRSDTO(1, 2, 4.));
        listRatingRS.add(new RatingRSDTO(1, 3, 2.));
        listRatingRS.add(new RatingRSDTO(1, 4, 1.));
        listRatingRS.add(new RatingRSDTO(2, 0, 2.));
        listRatingRS.add(new RatingRSDTO(2, 2, 1.));
        listRatingRS.add(new RatingRSDTO(2, 3, 3.));
        listRatingRS.add(new RatingRSDTO(2, 4, 4.));
        listRatingRS.add(new RatingRSDTO(3, 0, 1.));
        listRatingRS.add(new RatingRSDTO(3, 1, 1.));
        listRatingRS.add(new RatingRSDTO(3, 3, 4.));
        listRatingRS.add(new RatingRSDTO(4, 0, 1.));
        listRatingRS.add(new RatingRSDTO(4, 3, 4.));
        listRatingRS.add(new RatingRSDTO(5, 1, 2.));
        listRatingRS.add(new RatingRSDTO(5, 2, 1.));
        listRatingRS.add(new RatingRSDTO(6, 2, 1.));
        listRatingRS.add(new RatingRSDTO(6, 3, 4.));
        listRatingRS.add(new RatingRSDTO(6, 4, 5.));


        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

//        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();
        List<AVGRatedProductDTO> listAVG = new ArrayList<>();
        listAVG.add(new AVGRatedProductDTO(0, 2.8));
        listAVG.add(new AVGRatedProductDTO(1, 7.0 / 3.0));
        listAVG.add(new AVGRatedProductDTO(2, 1.75));
        listAVG.add(new AVGRatedProductDTO(3, 19.0 / 6.0));
        listAVG.add(new AVGRatedProductDTO(4, 3.0));

        // normalized utility matrix
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
//                System.out.println(value);
            });
        }

        List<CosineSimilarity> cosSimilarities = new ArrayList<>();

        // calc cosine similarity
        for (int i = size_list_avg - 1; i > 0; i--) {
            int product_id1 = list_product.get(i);
//            List<RatingRSDTO> list_normalized1 = new ArrayList<>(listRatingNormalized);
//            List<RatingRSDTO> user_rated_product1 = listRatingNormalized.stream().filter(value -> value.getProductId() == product_id1).collect(Collectors.toList());
            List<RatingRSDTO> user_rated_product1 = calcUserRatedProduct(listRatingNormalized, product_id1);
//            List<RatingRSDTO> list_normalized2 = new ArrayList<>(list_normalized1);
            for (int j = i - 1; j >= 0; j--) {
                int product_id2 = list_product.get(j);
                List<RatingRSDTO> user_rated_product2 = calcUserRatedProduct(listRatingNormalized, product_id2);

//                double pd1_dot_pd2 = user_rated_product1.stream().map(value -> user_rated_product2.stream().filter(s -> s.getUserId() == value.getUserId()).findFirst().
//                        map(ratingRSDTO -> (value.getValue() * ratingRSDTO.getValue())).orElse(0.0)).reduce(0.0, Double::sum);
                double pd1_dot_pd2 = p1_dot_p2(user_rated_product1, user_rated_product2);
//                double square_root_pd1 = Math.sqrt(user_rated_product1.stream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
//                double square_root_pd2 = Math.sqrt(user_rated_product2.stream().filter(value -> value.getValue() != 0.0).map(value -> Math.pow(value.getValue(), 2)).mapToDouble(Double::doubleValue).sum());
                double norm_pd1 = calc_norm(user_rated_product1);
                double norm_pd2 = calc_norm(user_rated_product2);
                double norm_pd1_mul_norm_pd2 = norm_pd1 * norm_pd2;
//
                double cosineSimilarity = norm_pd1_mul_norm_pd2 != 0.0 ? pd1_dot_pd2 / norm_pd1_mul_norm_pd2 : -1;
//
                CosineSimilarity a = new CosineSimilarity(product_id1, product_id2, cosineSimilarity);
//                System.out.println(a);
                cosSimilarities.add(a);
                System.out.println("i: " + i + " - j: " + j);
//                System.out.println(cosineSimilarity);
            }
        }

        for (CosineSimilarity cs : cosSimilarities) {
            System.out.println(cs.toString());
        }

        cosSimilarities.forEach(System.out::println);

        List<RecommendForUser> recommendForUserList = new ArrayList<>();
//
//        // Rating Prediction
        List<Integer> list_pd_user_rated = new ArrayList<>();
        List<Integer> list_pd_user_unrated = new ArrayList<>();
//        List<CosineSimilarity> list_cos_of_user = new ArrayList<>();
//        List<RatingRSDTO> list_normalize_of_user = new ArrayList<>();
        for (int i = 0; i < size_list_user; i++) {
            int user_id = list_users.get(i);
            divide_rated_unrated(listRatingNormalized, list_product, user_id, list_pd_user_rated, list_pd_user_unrated);
            System.out.println("------------------------giai doan 1---------------------------");
            int size_unrated = list_pd_user_unrated.size();
//            int size_rated = list_pd_user_rated.size();
            StringBuilder listProductRS = new StringBuilder();
//            for (int j = 0; j < size_unrated; j++) {
////                    for(int k = 0; k<size_rated; k++){
////                        int pd_id = list_pd_user_rated.get(k).getProductId();
//                int pd_unrated_id = list_pd_user_unrated.get(j);
//                list_cos_of_user.clear();
//                list_cos_of_user = top_k_cosine_similarity_of_user(2, cosSimilarities, pd_unrated_id, list_pd_user_rated);
//
//                System.out.println("------------------------giai doan 2---------------------------");
//                list_normalize_of_user.clear();
//                list_normalize_of_user = top_k_normalized_corresponding_top_k_cosine_similarity(
//                        user_id, list_cos_of_user, listRatingNormalized, pd_unrated_id);
//                System.out.println("------------------------giai doan 3---------------------------");
//
//                double a = list_cos_of_user.get(0).getRow() == list_normalize_of_user.get(0).getProductId() || list_cos_of_user.get(0).getColumn() == list_normalize_of_user.get(0).getProductId() ?
//                        list_cos_of_user.get(0).getSimilarity() * list_normalize_of_user.get(0).getValue() +
//                                list_cos_of_user.get(1).getSimilarity() * list_normalize_of_user.get(1).getValue() :
//                        list_cos_of_user.get(0).getSimilarity() * list_normalize_of_user.get(1).getValue() +
//                                list_cos_of_user.get(1).getSimilarity() * list_normalize_of_user.get(0).getValue();
//                double b = Math.abs(list_cos_of_user.get(0).getSimilarity()) + Math.abs(list_cos_of_user.get(1).getSimilarity());
//
////                System.out.println(a / b);
//                if (a / b > 0) {
//                    listProductRS.append(pd_unrated_id).append(" (").append((double) Math.round((a / b) * 100) / 100).append("), ");
//                }
//        }

            list_pd_user_unrated.parallelStream().forEach(value -> {
//                int pd_unrated_id = list_pd_user_unrated.get(j);
                List<CosineSimilarity> list_cos_of_user = new ArrayList<>();
                List<RatingRSDTO> list_normalize_of_user = new ArrayList<>();
                list_cos_of_user = top_k_cosine_similarity_of_user(2, cosSimilarities, value, list_pd_user_rated);

                list_normalize_of_user = top_k_normalized_corresponding_top_k_cosine_similarity(
                        user_id, list_cos_of_user, listRatingNormalized, value);

                double a = list_cos_of_user.get(0).getRow() == list_normalize_of_user.get(0).getProductId() || list_cos_of_user.get(0).getColumn() == list_normalize_of_user.get(0).getProductId() ?
                        list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(0).getValue() +
                                list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(1).getValue() :
                        list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(1).getValue() +
                                list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(0).getValue();
                double b = Math.abs(list_cos_of_user.get(0).getCosSimilarity()) + Math.abs(list_cos_of_user.get(1).getCosSimilarity());

//                System.out.println(a / b);
                if (a / b > 0) {
                    listProductRS.append(value).append(" (").append((double) Math.round((a / b) * 100) / 100).append("), ");
                }
            });
            recommendForUserList.add(new RecommendForUser(user_id, listProductRS.toString()));

//        System.out.println("i: " + i + "/" + size_list_user);
        }
//
        recommendForUserList.forEach(System.out::println);


        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        System.out.println("done");

        return ResponseEntity.ok().body(recommendForUserList);
    }

    private List<RatingRSDTO> calcUserRatedProduct(List<RatingRSDTO> list, int pd) {
        List<RatingRSDTO> a = new ArrayList<>();
        int size_list = list.size();
        for (int i = 0; i < size_list; i++) {
            if (list.get(i).getProductId() == pd) {
                a.add(list.get(i));
            }
        }
//        list.removeAll(a);
        return a;
//        return list.stream().filter(v -> v.getProductId() == pd).collect(Collectors.toList());
    }

    private double p1_dot_p2(List<RatingRSDTO> list1, List<RatingRSDTO> list2) {
        int size_list1 = list1.size();
        int size_list2 = list2.size();
        double p1_dot_p2 = 0.0;
        for (int i = 0; i < size_list1; i++) {
            for (int j = 0; j < size_list2; j++) {
                RatingRSDTO rt1 = list1.get(i);
                RatingRSDTO rt2 = list2.get(j);
                if (rt1.getUserId() == rt2.getUserId()) {
                    p1_dot_p2 += (rt1.getValue() * rt2.getValue());
                    break;
                }
            }
        }
        return p1_dot_p2;
    }

    private double calc_norm(List<RatingRSDTO> list) {
        int size_list = list.size();
        double norm = 0.0;
        for (int i = 0; i < size_list; i++) {
            RatingRSDTO rt = list.get(i);
            if (rt.getValue() != 0.0) {
                norm += Math.pow(rt.getValue(), 2);
            }
        }
        return Math.sqrt(norm);
    }

    private void divide_rated_unrated(List<RatingRSDTO> list_normalized, List<Integer> list_product, int user_id, List<Integer> list_rated, List<Integer> list_unrated) {
        int size_list_product = list_product.size();
        list_rated.clear();
        list_unrated.clear();
        for (int i = 0; i < size_list_product; i++) {
            int pd = list_product.get(i);
//            if (list_normalized.contains(new RatingRSDTO(user_id, pd)))
            if (list_normalized.stream().filter(value -> value.getUserId() == user_id && value.getProductId() == pd).findAny().orElse(null) != null)
                list_rated.add(pd);
            else list_unrated.add(pd);
        }
    }

    private List<CosineSimilarity> top_k_cosine_similarity_of_user(int k, List<CosineSimilarity> list, int pd_unrated_id, List<Integer> list_pd_user_rated) {
//        List<CosineSimilarity> cos_list = new ArrayList<>();
//        int size_list = list.size();
        return list.stream().filter(cos -> (cos.getRow() == pd_unrated_id && list_pd_user_rated.contains(cos.getColumn())) ||
                (list_pd_user_rated.contains(cos.getRow()) && cos.getColumn() == pd_unrated_id)).limit(k).collect(Collectors.toList());
//        for (int i = 0; i < size_list; i++) {
//            CosineSimilarity cos = list.get(i);
//            if ((cos.getRow() == pd_unrated_id && list_pd_user_rated.contains(cos.getColumn())) ||
//                    (list_pd_user_rated.contains(cos.getRow()) && cos.getColumn() == pd_unrated_id)) {
//                cos_list.add(cos);
//            }
//        }
//
////        cosSimilarities.parallelStream().
////                filter(value -> (value.getRow() == pd_unrated_id && list_pd_user_rated.contains(value.getColumn())) || (list_pd_user_rated.contains(value.getRow()) && value.getColumn() == pd_unrated_id)).
////                sorted(Comparator.comparingDouble(CosineSimilarity::getSimilarity).reversed()).
////                limit(2).collect(Collectors.toList());
//
//        return cos_list.stream().sorted(Comparator.comparing(CosineSimilarity::getSimilarity)).limit(k).collect(Collectors.toList());
    }

    private List<RatingRSDTO> top_k_normalized_corresponding_top_k_cosine_similarity(int user_id, List<CosineSimilarity> list_cos_of_user, List<RatingRSDTO> listRatingNormalized, int pd_unrated_id) {
//        List<RatingRSDTO> list = new ArrayList<>();
//        int size_normalized = listRatingNormalized.size();
//        for (int i = 0; i < size_normalized; i++) {
//            RatingRSDTO nm = listRatingNormalized.get(i);
//            if (nm.getUserId() == user_id && (list_cos_of_user.contains(new CosineSimilarity(pd_unrated_id, nm.getProductId())) ||
//                    list_cos_of_user.contains(new CosineSimilarity(nm.getProductId(), pd_unrated_id)))) {
//                list.add(nm);
//                if (list.size() == 2) return list;
//            }
//        }
//        return list;
        return listRatingNormalized.stream().filter(nm -> nm.getUserId() == user_id && ((list_cos_of_user.stream().filter(value -> value.getRow() == pd_unrated_id && value.getColumn() == nm.getProductId()).findAny().orElse(null) != null) ||
                list_cos_of_user.stream().filter(value -> value.getRow() == nm.getProductId() && value.getColumn() == pd_unrated_id).findAny().orElse(null) != null)).limit(2).collect(Collectors.toList());
//        listRatingNormalized.parallelStream().
//                filter(value -> value.getUserId() == user_id && (list_cos_of_user.contains(new CosineSimilarity(pd_unrated_id, value.getProductId())) ||
//                        list_cos_of_user.contains(new CosineSimilarity(value.getProductId(), pd_unrated_id)))).
//                limit(2).collect(Collectors.toList());
    }

}
