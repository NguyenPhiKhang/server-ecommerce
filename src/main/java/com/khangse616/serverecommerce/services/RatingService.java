package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public int checkUserIsRated(int uId){
        return ratingRepository.existsByUserId(uId);
    }

    public boolean checkExistId(int id){
        return ratingRepository.existsById(id);
    }
}
