package com.sprintsecurity.springjwtdemo.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sprintsecurity.springjwtdemo.services.JwtServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServicesImpl implements JwtServices{

    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    
    public String generateToken(UserDetails userDetails){
            return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() *1000*60*24))
                .signWith(getSignKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
         return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 604800000 ))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    // public String generateRefreshToken(Map<String,Object> extractClaims,UserDetails userDetails){
    //        
    // }

    private Key getSignKey() {
        byte[] signatureKey = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(signatureKey);
    }

    private <T> T extractClaims(String token,Function<Claims,T> resolver){
        final Claims claims = getAllClaims(token);
        return resolver.apply(claims);
    }

    public Claims getAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }
    public Date extractExpires(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        final Date expiration = extractExpires(token);
        return expiration.before(new Date());
    }
    
    public boolean isValidToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    
     
}
