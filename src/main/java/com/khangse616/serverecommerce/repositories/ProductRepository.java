package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("select u.id, u.name, a.")
//    List<Object> joinProductWithImges();

    @Query(value = "select b.* from category_product as a join products as b on a.product_id=b.id join categories as c on a.category_id = c.id where c.category_path=:path order by b.time_created asc limit :page,20", nativeQuery = true)
    List<Product> findProductsByCategoryPathTopN(@Param("path") String path, @Param("page") int page);

    // Calculate mean of vote average column
    @Query(value = "SELECT avg(case when (star1+star2+star3+star4+star5)>0 then (star1 + star2*2 + star3*3 + star4*4 + star5*5)/(star1+star2+star3+star4+star5) else 0 end) FROM rating_star;", nativeQuery = true)
    float meanOfVoteAverage();

    @Query(value = "call calculate_quantile();", nativeQuery = true)
    float calculateQuantile();
}
