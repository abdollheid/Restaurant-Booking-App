package com.Assignment.Restaurant.config;

import com.Assignment.Restaurant.RestaurantApplication;
import com.Assignment.Restaurant.dao.UserRepository;
import com.Assignment.Restaurant.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(RestaurantApplication.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("@App-UserDetailsServiceImpl-loadUserByUsername: Loading User " + username);

        User user = userRepository.findByEmail(username);

        if (user == null) {
            logger.debug("@App-UserDetailsServiceImpl-loadUserByUsername: Couldnt find user");
            throw new UsernameNotFoundException("Could not find user");

        }

        return new CustomUserDetails(user);
    }
}
