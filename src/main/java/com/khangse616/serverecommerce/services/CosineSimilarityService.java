package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.CosineSimilarity;
import com.khangse616.serverecommerce.repositories.CosineSimilarityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosineSimilarityService {
    @Autowired
    private CosineSimilarityRepository cosineSimilarityRepository;

    public void saveAll(List<CosineSimilarity> list){
        cosineSimilarityRepository.saveAll(list);
    }

    public CosineSimilarity save(CosineSimilarity cosineSimilarityDTO) {return cosineSimilarityRepository.save(cosineSimilarityDTO);}

    public CosineSimilarity getByColumnAndRow(int row, int column){
        return cosineSimilarityRepository.findByColumnAndRow(column, row);
    }

    public List<CosineSimilarity> getAll(){
        return cosineSimilarityRepository.findAll();
    }

    public void removeAll(){
        cosineSimilarityRepository.deleteAll();;
    }
}
