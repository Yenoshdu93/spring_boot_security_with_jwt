package com.sprintsecurity.springjwtdemo.dao;

import lombok.Data;

@Data
public class JwtAuthenticationResoponse {
    
    private String token;
    private String refreshToken;
}
