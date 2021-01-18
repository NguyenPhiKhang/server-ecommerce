package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.dto.RatingRSDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.mapper.ProductDetailMapper;
import com.khangse616.serverecommerce.mapper.ProductItemDTOMapper;
import com.khangse616.serverecommerce.mapper.RatingDTOMapper;
import com.khangse616.serverecommerce.mapper.RatingRSDTOMapper;
import com.khangse616.serverecommerce.models.Product;
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
//            List<Integer> list_product = ratingService.getProductsRated();
            List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> new RatingRSDTOMapper().mapRow(value)).collect(Collectors.toList());

            List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

            HashMap<Integer, Float> list_avgProductRated = new HashMap<>();

            List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();

            // normalized utility matrix
            for (AVGRatedProductDTO product_avg : listAVG) {
                for (int user_id : list_users) {
                    int index = listRatingNormalized.indexOf(new RatingRSDTO(user_id, product_avg.getProductId()));
                    if (index > -1) {
                        RatingRSDTO rt = listRatingNormalized.get(index);
                        rt.setValue(rt.getValue() - product_avg.getAvgRated());
                    }
                }
            }

            for(RatingRSDTO a: listRatingNormalized){
                System.out.println(a);
            }

//            // calc cosine similarity
//            for () {
//
//            }

        }
        return ResponseEntity.ok().body(list);
    }
}
