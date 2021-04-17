package com.khangse616.serverecommerce.services;

import com.khangse616.serverecommerce.models.User;
import com.khangse616.serverecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(int user_id){
        return userRepository.findById(user_id).get();
    }
}
