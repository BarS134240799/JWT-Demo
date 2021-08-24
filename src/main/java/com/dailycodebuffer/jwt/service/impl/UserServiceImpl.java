package com.dailycodebuffer.jwt.service.impl;

import com.dailycodebuffer.jwt.dto.Request.UserRequest;
import com.dailycodebuffer.jwt.dto.Respone.UserRespone;
import com.dailycodebuffer.jwt.model.CustomUserDetails;
import com.dailycodebuffer.jwt.model.Token;
import com.dailycodebuffer.jwt.model.User;
import com.dailycodebuffer.jwt.repository.TokenRepository;
import com.dailycodebuffer.jwt.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(userName);

        return new CustomUserDetails(user);
    }

    public UserRespone addUser(UserRequest userRequest) {
        UserRespone userRespone = new UserRespone();

        try {
            if(userRequest.getUsername() != null && userRequest.getPassword() != null) {
                User userr = userRepository.findUser(userRequest.getUsername(), userRequest.getPassword(),userRequest.getPhonenumber(),userRequest.getEmail());
                if(ObjectUtils.isEmpty(userr)) {
                    User user = new User();
                    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                    user.setUsername(userRequest.getUsername());
                    user.setPhonenumber(userRequest.getPhonenumber());
                    user.setEmail(userRequest.getEmail());
//                    BeanUtils.copyProperties(userRequest, user);
                    userRepository.save(user);
                    BeanUtils.copyProperties(user, userRespone);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    Token token = new Token();
                    token.setId(userRespone.getId());
                    token.setToken("");
                    token.setCreatTime(timestamp);
                    token.setUpdateTime(timestamp);
                    tokenRepository.save(token);

                }
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return  userRespone;
    }

    public void deleteUser(Long id) {
        try {
            if(id != null) {
                User user = userRepository.findUserById(id);
                if(!ObjectUtils.isEmpty(user)) {
                    userRepository.deleteById(id);
                    tokenRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }
    public void updateUser(UserRequest userRequest, Long id) {

        try {
            if(userRequest.getUsername() != null && userRequest.getPassword() != null) {
                User user = userRepository.findUserById(id);
                if(!ObjectUtils.isEmpty(user)) {
                    user.setUsername(userRequest.getUsername());
                    user.setPassword(userRequest.getPassword());
                    userRepository.save(user);
                }
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }
}
