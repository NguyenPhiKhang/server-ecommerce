package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.models.Category;
import com.khangse616.serverecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    private ResponseEntity<List<Category>> getCategoriesLevel1(@RequestParam("level") int level){
        return ResponseEntity.ok().body(categoryService.findCategoryByLevel(level));
    }

    @GetMapping("/categories/{parentId}/sub-categories")
    private ResponseEntity<List<Category>> getCategoriesByParentCategory(@PathVariable("parentId") int parentId){
        return ResponseEntity.ok().body(categoryService.findCategoryByParentCategory(parentId));
    }
}
