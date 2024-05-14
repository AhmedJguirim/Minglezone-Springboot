package com.patients.patients.services;

import com.patients.patients.DTO.CustomUserDetails;
import com.patients.patients.entities.User;
import com.patients.patients.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        logger.info("User authenticated successfully: " + username);
        return new CustomUserDetails(user);
    }
}
