package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query(value = "select count(*) as col_0_0_ from ratings where user_id=:uId", nativeQuery = true)
    int existsByUserId(@Param("uId") int userId);

    boolean existsById(int id);

    @Query(value = "select count(distinct user_id) from ratings", nativeQuery = true)
    int countDistinctAllUser();

    @Query(value = "select count(distinct product_id) from ratings", nativeQuery = true)
    int countDistinctAllProduct();

    @Query(value = "select distinct user_id from ratings order by user_id asc", nativeQuery = true)
    List<Integer> findUsersRated();

    @Query(value = "select distinct product_id from ratings order by product_id asc", nativeQuery = true)
    List<Integer> findProductsRated();

//    @Query(value = "select product_id as ProductId, avg(star) as AvgRated from ratings group by productId order by productId asc;", nativeQuery = true)
    @Query("select new com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO(u.product.id, avg(u.star)) from Rating u group by u.product order by u.product.id asc ")
    List<AVGRatedProductDTO> avgRatedProduct();

    List<Rating> findAllByOrderByProductAsc();
}
