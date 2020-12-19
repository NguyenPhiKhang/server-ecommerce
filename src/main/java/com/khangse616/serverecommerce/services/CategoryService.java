package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.Category;
import com.khangse616.serverecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findCategoryByLevel(int level){
        return categoryRepository.findByLevel(level);
    }

    public List<Category> findCategoryByParentCategory(int parentId){
        return categoryRepository.findByParentId(parentId);
    }
}
