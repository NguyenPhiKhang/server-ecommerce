package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.RatingStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingStarRepository extends JpaRepository<RatingStar, Integer> {
}
