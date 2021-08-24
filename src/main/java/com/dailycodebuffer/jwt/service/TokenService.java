package com.dailycodebuffer.jwt.service;

import java.sql.Timestamp;

public interface TokenService {

    void updateToken(String token, Timestamp timestamp, Long id);
}
