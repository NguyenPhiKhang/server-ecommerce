package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.CategoryScreenDTO;
import com.khangse616.serverecommerce.mapper.CategoryScreenDTOMapper;
import com.khangse616.serverecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    private ResponseEntity<List<CategoryScreenDTO>> getCategoriesLevel1(@RequestParam(value = "level", required = false, defaultValue = "0") int level){
        List<CategoryScreenDTO> categoryScreenDTO = level!=0?categoryService.findCategoryByLevel(level).stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList())
                : categoryService.findAllCategories().stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryScreenDTO);
    }

    @GetMapping("/categories/{parentId}/sub-categories")
    private ResponseEntity<List<CategoryScreenDTO>> getCategoriesByParentCategory(@PathVariable("parentId") int parentId){
        List<CategoryScreenDTO> categoryScreenDTO = categoryService.findCategoryByParentCategory(parentId).stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryScreenDTO);
    }
}
