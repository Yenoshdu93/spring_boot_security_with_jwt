package com.sprintsecurity.springjwtdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sprintsecurity.springjwtdemo.Jwt.JwtAuthenticationFilter;
import com.sprintsecurity.springjwtdemo.modle.Role;
import com.sprintsecurity.springjwtdemo.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{

        return security.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admin").hasAnyRole(Role.ADMIN.name())
                    .requestMatchers("/api/v1/user").hasAnyRole(Role.USER.name())
                    .anyRequest().authenticated()
                    )
                    .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class)
                    .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        auth.setUserDetailsService(userService.userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());

        return auth;



    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();  
      }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
