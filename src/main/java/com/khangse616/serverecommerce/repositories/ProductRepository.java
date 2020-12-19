package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    @Query("select u.id, u.name, a.")
//    List<Object> joinProductWithImges();
}
