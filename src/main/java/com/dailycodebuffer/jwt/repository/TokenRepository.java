package com.dailycodebuffer.jwt.repository;

import com.dailycodebuffer.jwt.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select t from Token t where t.id = ?1")
    Token findTokenById(Long id);

}
