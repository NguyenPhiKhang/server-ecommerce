package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.models.Rating;
import com.khangse616.serverecommerce.models.User;
import com.khangse616.serverecommerce.repositories.ProductRepository;
import com.khangse616.serverecommerce.repositories.RatingRepository;
import com.khangse616.serverecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public int checkUserIsRated(int uId) {
        return ratingRepository.existsByUserId(uId);
    }

    public boolean checkExistId(int id) {
        return ratingRepository.existsById(id);
    }

    public int numberUserInRatings() {
        return ratingRepository.countDistinctAllUser();
    }

    public int numberProductInRatings() {
        return ratingRepository.countDistinctAllProduct();
    }

    public List<Rating> getAll() {
        return ratingRepository.findAllByOrderByProductAsc();
    }

    public List<Integer> getUsersRated() {
        return ratingRepository.findUsersRated();
    }

    public List<Integer> getProductsRated() {
        return ratingRepository.findProductsRated();
    }

    public List<AVGRatedProductDTO> calcAVGRatedProduct() {
        return ratingRepository.avgRatedProduct();
    }

    public void autoRating() {
        Random rd = new Random();
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User user : users) {
            for (int i = 0; i < 20; i++) {
                int idRating;
                do {
                    idRating = 16000000 + rd.nextInt(4000001);
                } while (ratingRepository.existsById(idRating));

                int star = 1 + rd.nextInt(5);
                int productIndex;
                do {
                    productIndex = rd.nextInt(products.size());
                } while (ratingRepository.existsByUserAndProduct(user, products.get(productIndex)));

                Rating rating = new Rating();
                rating.setId(idRating);
                rating.setStar(star);
                rating.setUser(user);
                rating.setProduct(products.get(productIndex));
                rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
                rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

                ratingRepository.save(rating);
            }
        }
    }
}
