package com.github.jacobbishopxy.simplesecurity.security;

import java.util.Collections;

import com.github.jacobbishopxy.simplesecurity.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepo
        .findByEmail(email)
        .map(user -> {
          return new User(
              email,
              user.getPassword(),
              Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        })
        .orElseThrow(() -> {
          throw new UsernameNotFoundException(String.format("User with email %s not found", email));
        });
  }

}
