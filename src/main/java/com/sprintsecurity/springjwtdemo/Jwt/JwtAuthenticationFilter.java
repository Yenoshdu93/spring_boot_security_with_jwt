package com.sprintsecurity.springjwtdemo.Jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sprintsecurity.springjwtdemo.services.JwtServices;
import com.sprintsecurity.springjwtdemo.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            String requestHeader = response.getHeader("Authorization");
            String token=null;
            String userEmail=null;

            if(requestHeader!= null && requestHeader.startsWith("Bearer ")){
                token = requestHeader.substring(7);
                userEmail = jwtServices.extractUsername(token);
            
            }else{
                logger.info("invalid user");
            }
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() ==null){
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);          
                boolean isValidToken = jwtServices.isValidToken(token,userDetails);
                if(isValidToken){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication(authToken);     
                }else{
                    logger.info("validation failed");
                }
            }
            filterChain.doFilter(request, response);       
    }
    
}
