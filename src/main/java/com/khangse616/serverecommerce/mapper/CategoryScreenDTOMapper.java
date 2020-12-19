package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.CategoryScreenDTO;
import com.khangse616.serverecommerce.models.Category;
import com.khangse616.serverecommerce.utils.ImageUtil;

public class CategoryScreenDTOMapper implements RowMapper<CategoryScreenDTO, Category> {
    @Override
    public CategoryScreenDTO mapRow(Category category) {
        try {
            CategoryScreenDTO categoryScreenDTO = new CategoryScreenDTO();
            categoryScreenDTO.setId(category.getId());
            categoryScreenDTO.setName(category.getName());
            categoryScreenDTO.setLevel(category.getLevel());
            categoryScreenDTO.setCategoryPath(category.getCategoryPath());
            categoryScreenDTO.setIcon(ImageUtil.addressImage(category.getIcon()));

            return categoryScreenDTO;
        }catch (Exception ex){
            return null;
        }
    }
}
