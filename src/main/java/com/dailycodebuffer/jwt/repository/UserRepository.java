package com.dailycodebuffer.jwt.repository;

import com.dailycodebuffer.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    @Query(value = "select u from User u where u.username = ?1  and u.password = ?2 and u.phonenumber=?3 and u.email=?4")
    User findUser(String username, String password,String phonenumber,String email);

    @Query(value = "select u from User u where u.id = ?1")
    User findUserById(Long id);

//    @Query(value = "select User from User where User.username = ?1")
//    Long findIdByUsername(String username);




}
