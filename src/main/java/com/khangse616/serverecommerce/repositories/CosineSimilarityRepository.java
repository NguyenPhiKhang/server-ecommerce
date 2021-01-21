package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.CosineSimilarity;
import com.khangse616.serverecommerce.models.CosineSimilarityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosineSimilarityRepository extends JpaRepository<CosineSimilarity, CosineSimilarityId> {
    CosineSimilarity findByColumnAndRow(int column, int row);
}
