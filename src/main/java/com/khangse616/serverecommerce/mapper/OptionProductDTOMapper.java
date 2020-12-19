package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.OptionProductDTO;
import com.khangse616.serverecommerce.models.Option;

public class OptionProductDTOMapper implements RowMapper<OptionProductDTO, Option> {
    @Override
    public OptionProductDTO mapRow(Option option) {
        try {
            OptionProductDTO optionProductDTO = new OptionProductDTO();
            optionProductDTO.setId(option.getId());
            optionProductDTO.setValue(option.getValue());
            return optionProductDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
