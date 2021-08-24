package com.dailycodebuffer.jwt.controller;

import com.dailycodebuffer.jwt.dto.Request.UserRequest;
import com.dailycodebuffer.jwt.dto.Respone.UserRespone;
import com.dailycodebuffer.jwt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControll {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/addUser")
    public UserRespone addUser(@RequestBody UserRequest userRequest) {
        UserRespone userRespone =userService.addUser(userRequest);
        return userRespone;
    }

    @PostMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/updateUser/{id}")
    public void updateUser(@RequestBody UserRequest userRequest,@PathVariable Long id) {
        userService.updateUser(userRequest, id);
    }
}
