package com.dailycodebuffer.jwt.service.impl;

import com.dailycodebuffer.jwt.model.Token;
import com.dailycodebuffer.jwt.repository.TokenRepository;
import com.dailycodebuffer.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void updateToken(String token, Timestamp timestamp, Long id) {
        Token tokenn = tokenRepository.findTokenById(id);
        tokenn.setToken(token);
        tokenn.setUpdateTime(timestamp);
        tokenRepository.save(tokenn);
    }


}
