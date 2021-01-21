package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.dto.RecommendSystem.AVGRatedProductDTO;
import com.khangse616.serverecommerce.models.Product;
import com.khangse616.serverecommerce.models.Rating;
import com.khangse616.serverecommerce.models.RatingStar;
import com.khangse616.serverecommerce.models.User;
import com.khangse616.serverecommerce.repositories.ProductRepository;
import com.khangse616.serverecommerce.repositories.RatingRepository;
import com.khangse616.serverecommerce.repositories.RatingStarRepository;
import com.khangse616.serverecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Autowired
    private RatingStarRepository ratingStarRepository;

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

        int size_product = products.size();
        List<Rating> list_ratings = new ArrayList<>();
        List<RatingStar> ratingStarList = new ArrayList<>();
        int q = 100;
        for (int j = 0;j<size_product;j++) {
            System.out.println("-----------------product thu: "+j+" ------------------------");
            Product pd = products.get(j);
            int rating_rd = rd.nextInt(401) + q;
            q*=-1;
            int total_rating = rating_rd<0?0: Math.min(rating_rd, 500);
            System.out.println("tong rating: "+total_rating);
            int star1 =0; int star2=0; int star3=0; int star4=0; int star5=0;
            for (int i = 0; i < total_rating; i++) {
                int idRating;
                do {
                    idRating = 10000000 + rd.nextInt(6000001);
                } while (ratingRepository.existsById(idRating));

                int star = 1 + rd.nextInt(8);
                switch (star){
                    case 1:
                        star1++;
                        break;
                    case 2:
                        star2++;
                        break;
                    case 3:
                        star3++;
                        break;
                    case 4:
                        star4++;
                        break;
                    default:
                        star5++;
                        break;

                }
                int userIndex;
                User user;
                do {
                    userIndex = rd.nextInt(users.size());
                    user = users.get(userIndex);
                } while (ratingRepository.existsByUserAndProduct(user, pd));


                Rating rating = new Rating();
                rating.setId(idRating);
                rating.setStar(star);
                rating.setUser(user);
                rating.setProduct(pd);
                rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
                rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

                list_ratings.add(rating);

//                ratingRepository.save(rating);
            }

            RatingStar ratingStar = ratingStarRepository.findById(pd.getId()).get();
            ratingStar.setStar1(star1);
            ratingStar.setStar2(star2);
            ratingStar.setStar3(star3);
            ratingStar.setStar4(star4);
            ratingStar.setStar5(star5);

            ratingStarList.add(ratingStar);
        }


        ratingRepository.saveAll(list_ratings);
        ratingStarRepository.saveAll(ratingStarList);

//        for (User user : users) {
//            for (int i = 0; i < 20; i++) {
//                int idRating;
//                do {
//                    idRating = 16000000 + rd.nextInt(4000001);
//                } while (ratingRepository.existsById(idRating));
//
//                int star = 1 + rd.nextInt(5);
//                int productIndex;
//                do {
//                    productIndex = rd.nextInt(products.size());
//                } while (ratingRepository.existsByUserAndProduct(user, products.get(productIndex)));
//
//                Rating rating = new Rating();
//                rating.setId(idRating);
//                rating.setStar(star);
//                rating.setUser(user);
//                rating.setProduct(products.get(productIndex));
//                rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
//                rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
//
//                ratingRepository.save(rating);
//            }
//        }
    }
}
