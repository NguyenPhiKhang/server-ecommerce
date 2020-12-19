package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.ProductDetailDTO;
import com.khangse616.serverecommerce.models.Product;

public class ProductDetailMapper implements RowMapper<ProductDetailDTO, Product> {
    @Override
    public ProductDetailDTO mapRow(Product product) {
        try{
            ProductDetailDTO productDetailDTO = new ProductDetailDTO();
//            productDetailDTO
            return productDetailDTO;
        }catch(Exception ex){
            return null;
        }
    }
}
