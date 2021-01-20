package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.exceptions.ResourceNotFoundException;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.models.RecommendRating;
import com.khangse616.serverecommerce.repositories.CategoryRepository;
import com.khangse616.serverecommerce.repositories.ProductRepository;
import com.khangse616.serverecommerce.repositories.RecommendRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecommendRatingRepository recommendRatingRepository;

    public Product findProductById(int id) {
        return productRepository.findById(id).orElseThrow(null);
    }

    public List<Product> findProductByCategory(String categoryPath, int page){
        return productRepository.findProductsByCategoryPathTopN(categoryPath, page);
    }

    public List<Product> productTopRating(int page){
        float C = productRepository.meanOfVoteAverage();
        float m = productRepository.calculateQuantile();

        return productRepository.topRatingProducts(m, C, page);
    }

    public List<Product> productRecommendForUser(int userId){
        RecommendRating recommendRating = recommendRatingRepository.findByUserId(userId);
        List<Integer> listProducts = Arrays.stream(recommendRating.getProducts().split("-")).map(Integer::parseInt).collect(Collectors.toList());
        return productRepository.findProductByListIdProduct(listProducts);
    }
}
