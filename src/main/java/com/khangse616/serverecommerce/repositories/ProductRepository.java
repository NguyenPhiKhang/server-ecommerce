package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("select u.id, u.name, a.")
//    List<Object> joinProductWithImges();

    @Query(value = "select b.* from category_product as a join products as b on a.product_id=b.id join categories as c on a.category_id = c.id where c.category_path=:path order by b.time_created asc limit :page,20", nativeQuery = true)
    List<Product> findProductsByCategoryPathTopN(@Param("path") String path, @Param("page") int page);
}
