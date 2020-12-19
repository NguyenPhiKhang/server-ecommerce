package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.dto.ProductItemDTO;
import com.khangse616.serverecommerce.mapper.ProductDetailMapper;
import com.khangse616.serverecommerce.mapper.ProductItemDTOMapper;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

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
}
