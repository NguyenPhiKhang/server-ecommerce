package com.khangse616.serverecommerce.repositories;


import com.khangse616.serverecommerce.models.RecommendRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRatingRepository extends JpaRepository<RecommendRating, Integer> {
    RecommendRating findByUserId(int userId);
}
