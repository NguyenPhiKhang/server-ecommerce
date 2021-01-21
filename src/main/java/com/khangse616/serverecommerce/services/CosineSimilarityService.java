package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.dto.RecommendSystem.CosineSimilarityDTO;
import com.khangse616.serverecommerce.repositories.CosineSimilarityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosineSimilarityService {
    @Autowired
    private CosineSimilarityRepository cosineSimilarityRepository;

    public void saveAll(List<CosineSimilarityDTO> list){
        cosineSimilarityRepository.saveAll(list);
    }
}
