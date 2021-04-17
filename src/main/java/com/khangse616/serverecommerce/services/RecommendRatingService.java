package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.RecommendRating;
import com.khangse616.serverecommerce.repositories.RecommendRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendRatingService {
    @Autowired
    private RecommendRatingRepository recommendRatingRepository;

    public boolean checkExistUser(int userId){
        return recommendRatingRepository.existsByUserId(userId);
    }

    public RecommendRating save(RecommendRating recommendRating){
        return recommendRatingRepository.save(recommendRating);
    }

    public RecommendRating getById(int user_id){
        return recommendRatingRepository.findByUserId(user_id);
    }

    public void removeAll(){
        recommendRatingRepository.deleteAll();
    }
}
