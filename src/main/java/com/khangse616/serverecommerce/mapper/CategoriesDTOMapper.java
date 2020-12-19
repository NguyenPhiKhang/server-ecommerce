package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.CategoriesDTO;
import com.khangse616.serverecommerce.models.Category;

import java.util.List;

public class CategoriesDTOMapper implements RowMapper<CategoriesDTO, List<Category>> {
    @Override
    public CategoriesDTO mapRow(List<Category> categories) {
       try{
           CategoriesDTO categoriesDTO = new CategoriesDTO();
           categories.forEach(value->{
               switch (value.getLevel()){
                   case 1:
                       categoriesDTO.setCategoryLevel1Id(value.getId());
                       categoriesDTO.setCategoryLevel1Name(value.getName());
                       categoriesDTO.setCategoryLevel1Path(value.getCategoryPath());
                       break;
                   case 2:
                       categoriesDTO.setCategoryLevel2Id(value.getId());
                       categoriesDTO.setCategoryLevel2Name(value.getName());
                       categoriesDTO.setCategoryLevel2Path(value.getCategoryPath());
                       break;
                   case 3:
                       categoriesDTO.setCategoryLevel3Id(value.getId());
                       categoriesDTO.setCategoryLevel3Name(value.getName());
                       categoriesDTO.setCategoryLevel3Path(value.getCategoryPath());
                       break;
               }
           });

           return categoriesDTO;

       }catch (Exception ex){
           return null;
       }
    }
}
