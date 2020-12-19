package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.AttributeProductDTO;
import com.khangse616.serverecommerce.models.Attribute;
import com.khangse616.serverecommerce.models.Option;

import java.util.List;

public class AttributeProductDTOMapper implements RowMapper<AttributeProductDTO, Attribute> {
    @Override
    public AttributeProductDTO mapRow(Attribute attribute) {
        try{
            AttributeProductDTO attributeProductDTO = new AttributeProductDTO();
            attributeProductDTO.setId(attribute.getId());
            attributeProductDTO.setName(attribute.getName());
            return attributeProductDTO;
        }catch(Exception ex){
            return null;
        }
    }
}
