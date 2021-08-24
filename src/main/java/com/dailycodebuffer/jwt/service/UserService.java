package com.dailycodebuffer.jwt.service;

import com.dailycodebuffer.jwt.dto.Request.UserRequest;
import com.dailycodebuffer.jwt.dto.Respone.UserRespone;
import com.dailycodebuffer.jwt.model.CustomUserDetails;
import com.dailycodebuffer.jwt.model.User;
import com.dailycodebuffer.jwt.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(userName);

        return new CustomUserDetails(user);
    }

    public UserRespone addUser(UserRequest userRequest) {
        UserRespone userRespone = new UserRespone();

        try {
            if(userRequest.getUsername() != null && userRequest.getPassword() != null) {
                User userr = userRepository.findUser(userRequest.getUsername(), userRequest.getPassword() ,userRequest.getPhonenumber(),userRequest.getEmail());
                if(ObjectUtils.isEmpty(userr)) {
                    User user = new User();
                    BeanUtils.copyProperties(userRequest, user);
                    userRepository.save(user);
                    BeanUtils.copyProperties(user, userRespone);
                }
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return  userRespone;
    }
}
