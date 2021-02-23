package com.Assignment.Restaurant.config;

import com.Assignment.Restaurant.RestaurantApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;


public class CustomAuthenticationProviderImpl extends DaoAuthenticationProvider {
    private static final Logger logger = LogManager.getLogger(RestaurantApplication.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = new AnonymousAuthenticationToken("GUEST", "USERNAME", AuthorityUtils
                .createAuthorityList("GUEST"));

        try {
            auth = super.authenticate(authentication);
            logger.debug("@App-CustomAuthenticationProviderImpl-authenticate: Authorized user");
        } catch (AuthenticationException authenticationException) {
            // auth is AnonymousAuthenticationToken, Access Denied
            logger.debug("@App-CustomAuthenticationProviderImpl-authenticate: Unauthorized user");

        }

        return auth;

    }

}
