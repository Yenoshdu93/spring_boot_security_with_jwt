package com.sprintsecurity.springjwtdemo.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface JwtServices {


    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    public boolean isValidToken(String token,UserDetails userDetails);

    String generateRefreshToken(Map<String,Object> extractClaims,UserDetails userDetails) throws UsernameNotFoundException;
    
}
