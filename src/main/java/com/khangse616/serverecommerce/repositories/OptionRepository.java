package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
    List<Option> findByIdIn(List<Integer> id);
}
