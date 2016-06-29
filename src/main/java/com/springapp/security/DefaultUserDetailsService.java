package com.springapp.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DefaultUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static final String ROLE_USER = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String openId) {
        return new User(openId, "", Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) {
        return loadUserByUsername((String) token.getPrincipal());
    }

}