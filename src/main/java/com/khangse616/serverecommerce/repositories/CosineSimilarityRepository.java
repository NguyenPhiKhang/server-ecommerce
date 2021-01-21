package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.dto.RecommendSystem.CosineSimilarityDTO;
import com.khangse616.serverecommerce.dto.RecommendSystem.CosineSimilarityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosineSimilarityRepository extends JpaRepository<CosineSimilarityDTO, CosineSimilarityId> {
}
