package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
   @Query(value = "select count(*) as col_0_0_ from ratings where user_id=:uId", nativeQuery = true)
    int existsByUserId(@Param("uId") int userId);

   boolean existsById(int id);
}
