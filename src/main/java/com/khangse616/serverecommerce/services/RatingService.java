package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.Rating;
import com.khangse616.serverecommerce.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public int checkUserIsRated(int uId) {
        return ratingRepository.existsByUserId(uId);
    }

    public boolean checkExistId(int id) {
        return ratingRepository.existsById(id);
    }

    public int numberUserInRatings() {
        return ratingRepository.countDistinctAllUser();
    }

    public int numberProductInRatings() {
        return ratingRepository.countDistinctAllProduct();
    }

    public List<Rating> getAll(){
        return ratingRepository.findAll();
    }

    public List<Integer> getUsersRated(){return ratingRepository.findUsersRated();}

    public List<Integer> getProductsRated(){return ratingRepository.findProductsRated();}
}
