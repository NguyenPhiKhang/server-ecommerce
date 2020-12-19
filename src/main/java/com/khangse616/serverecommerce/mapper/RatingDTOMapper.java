package com.khangse616.serverecommerce.mapper;

import com.khangse616.serverecommerce.dto.RatingDTO;
import com.khangse616.serverecommerce.models.Rating;
import com.khangse616.serverecommerce.models.User;

public class RatingDTOMapper implements RowMapper<RatingDTO, Rating> {
    @Override
    public RatingDTO mapRow(Rating rating) {
        try{
            RatingDTO ratingDTO = new RatingDTO();
            ratingDTO.setId(rating.getId());
            ratingDTO.setComment(rating.getComment());
            User user = rating.getUser();
            ratingDTO.setCustomerId(user.getId());
            ratingDTO.setImage(user.getImageAvatar().getLink());
            ratingDTO.setStar(rating.getStar());
            ratingDTO.setTimeUpdated(rating.getTimeUpdated());
            ratingDTO.setUserName(user.getName());

            return ratingDTO;
        }catch (Exception ex){
            return null;
        }
    }
}
