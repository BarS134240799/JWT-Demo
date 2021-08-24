package com.dailycodebuffer.jwt.controller;

import com.dailycodebuffer.jwt.dto.Request.JwtRequest;
import com.dailycodebuffer.jwt.dto.Respone.JwtResponse;
import com.dailycodebuffer.jwt.model.User;
import com.dailycodebuffer.jwt.repository.UserRepository;
import com.dailycodebuffer.jwt.service.impl.TokenServiceImpl;
import com.dailycodebuffer.jwt.service.impl.UserServiceImpl;
import com.dailycodebuffer.jwt.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class HomeController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenServiceImpl tokenService;

    @GetMapping("/")
    public String home() {
        return "Welcome ";
    }
    @GetMapping("/uot")
    public String logout() {
        return "logout ";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = userRepository.findUserByUsername(jwtRequest.getUsername());
        Long id = user.getId();
        tokenService.updateToken(token, timestamp, id);

        return  new JwtResponse(token);
    }
}
