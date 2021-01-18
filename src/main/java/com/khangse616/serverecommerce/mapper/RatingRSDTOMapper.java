package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.RecommendSystem.RatingRSDTO;
import com.khangse616.serverecommerce.models.Rating;

public class RatingRSDTOMapper implements RowMapper<RatingRSDTO, Rating>{
    @Override
    public RatingRSDTO mapRow(Rating rating) {
        try{
            return new RatingRSDTO(rating.getUser().getId(), rating.getProduct().getId(), rating.getStar());
        }catch (Exception ex){
            return null;
        }
    }
}
