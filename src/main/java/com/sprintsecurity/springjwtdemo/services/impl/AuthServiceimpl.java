package com.sprintsecurity.springjwtdemo.services.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sprintsecurity.springjwtdemo.dao.AuthRequest;
import com.sprintsecurity.springjwtdemo.dao.JwtAuthenticationResoponse;
import com.sprintsecurity.springjwtdemo.dao.RefreshToken;
import com.sprintsecurity.springjwtdemo.dao.Signin;
import com.sprintsecurity.springjwtdemo.modle.Role;
import com.sprintsecurity.springjwtdemo.modle.User;
import com.sprintsecurity.springjwtdemo.repository.UserRepository;
import com.sprintsecurity.springjwtdemo.services.AuthService;
import com.sprintsecurity.springjwtdemo.services.JwtServices;


@Service
public class AuthServiceimpl implements AuthService{
    

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServices jwtServices;

    @Override
    public User signUp(AuthRequest authRequest) {
      User user = User.builder()
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResoponse signin(Signin signin) throws UsernameNotFoundException{
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getEmail(),signin.getPassword()));

        var user = userRepository.findByEmail(signin.getEmail()).orElseThrow(() -> new UsernameNotFoundException("invalid user"));
        var token=jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResoponse jwt = new JwtAuthenticationResoponse();

        jwt.setToken(token);
        jwt.setRefreshToken(refreshToken);

        return jwt;
    }

    public JwtAuthenticationResoponse refreshToken(RefreshToken refreshToken) throws UsernameNotFoundException{

        String userEmail = jwtServices.extractUsername(refreshToken.getRefreshToken());

        var user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("invalid user"));

        if(jwtServices.isValidToken(refreshToken.getRefreshToken(), user)){

        String token = jwtServices.generateToken(user);
            
        JwtAuthenticationResoponse jwt = new JwtAuthenticationResoponse();

        jwt.setToken(token);
        jwt.setRefreshToken(refreshToken.getRefreshToken());

        return jwt;
        }
        return null;
    }

}
