package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.models.Account;
import com.khangse616.serverecommerce.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/rating/auto-rating")
    public ResponseEntity<String> autoRating(){
        ratingService.autoRating();
        return ResponseEntity.ok().body("done");
    }
}
