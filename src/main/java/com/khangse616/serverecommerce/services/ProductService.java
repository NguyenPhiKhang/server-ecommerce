package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.exceptions.ResourceNotFoundException;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.repositories.CategoryRepository;
import com.khangse616.serverecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product findProductById(int id) {
        return productRepository.findById(id).orElseThrow(null);
    }

    public List<Product> findProductByCategory(String categoryPath, int page){
        return productRepository.findProductsByCategoryPathTopN(categoryPath, page);
//        return categoryRepository.findNProduct(categoryPath, (Pageable) PageRequest.of(page-1, 20));

//        Page<Product> products = (Page<Product>) categoryRepository.findFirstByCategoryPath(categoryPath).getProducts(PageRequest.of(page-1,20)));
    }

    public List<Product> productTopRating(int page){
        float C = productRepository.meanOfVoteAverage();
//        System.out.println("C: "+ C);
        float m = productRepository.calculateQuantile();
//        System.out.println("m: "+ m);

        return productRepository.topRatingProducts(m, C, page);
    }
}
