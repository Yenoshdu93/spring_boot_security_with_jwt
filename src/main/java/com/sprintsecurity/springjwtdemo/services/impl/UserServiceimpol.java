package com.sprintsecurity.springjwtdemo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sprintsecurity.springjwtdemo.repository.UserRepository;
import com.sprintsecurity.springjwtdemo.services.UserService;

@Service
public class UserServiceimpol implements UserService{

    @Autowired
    private UserRepository userRepository;
    

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               return userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found with this id"));
            }
            
        };
    }
}
