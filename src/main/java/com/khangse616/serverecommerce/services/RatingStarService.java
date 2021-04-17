package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.Rating;
import com.khangse616.serverecommerce.models.RatingStar;
import com.khangse616.serverecommerce.repositories.RatingStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingStarService {
    @Autowired
    private RatingStarRepository ratingStarRepository;

    public RatingStar getRatingStarById(int id){
        return ratingStarRepository.findById(id).get();
    }

    public RatingStar save(RatingStar ratingStar){
        return ratingStarRepository.save(ratingStar);
    }
}
