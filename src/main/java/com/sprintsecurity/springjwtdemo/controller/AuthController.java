package com.sprintsecurity.springjwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintsecurity.springjwtdemo.dao.AuthRequest;
import com.sprintsecurity.springjwtdemo.dao.JwtAuthenticationResoponse;
import com.sprintsecurity.springjwtdemo.dao.RefreshToken;
import com.sprintsecurity.springjwtdemo.dao.Signin;
import com.sprintsecurity.springjwtdemo.modle.User;
import com.sprintsecurity.springjwtdemo.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody AuthRequest authRequest){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signUp(authRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResoponse> signin(@RequestBody Signin signin) throws UsernameNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(authService.signin(signin));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResoponse> refreshToken(@RequestBody RefreshToken refreshToken) throws UsernameNotFoundException{
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
    
}
