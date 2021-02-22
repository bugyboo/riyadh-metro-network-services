package com.nervelife.soma.riyadhmetronetworkserver.services;

import com.nervelife.soma.riyadhmetronetworkserver.domain.presist.User;
import com.nervelife.soma.riyadhmetronetworkserver.domain.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService{
    
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepo.findByUsername(username);
        if ( user != null ) {
            return org.springframework.security.core.userdetails.User
                                        .withUsername(user.getUsername())
                                        .password(user.getPassword())
                                        .roles(user.getRole())
                                        .build();
        }

        throw new UsernameNotFoundException(String.format("User =%s was not found", username));
    }
}
