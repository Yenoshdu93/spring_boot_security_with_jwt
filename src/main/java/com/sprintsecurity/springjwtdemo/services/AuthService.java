package com.sprintsecurity.springjwtdemo.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sprintsecurity.springjwtdemo.dao.AuthRequest;
import com.sprintsecurity.springjwtdemo.dao.JwtAuthenticationResoponse;
import com.sprintsecurity.springjwtdemo.dao.RefreshToken;
import com.sprintsecurity.springjwtdemo.dao.Signin;
import com.sprintsecurity.springjwtdemo.modle.User;

public interface AuthService {

    User signUp(AuthRequest authRequest);

    JwtAuthenticationResoponse signin(Signin signin);

    JwtAuthenticationResoponse refreshToken(RefreshToken refreshToken) throws UsernameNotFoundException;

    
}
