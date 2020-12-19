package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsById(int id);
    boolean existsByIdAndShopTrue(int id);
    boolean existsByIdAndName(int id, String name);
}
