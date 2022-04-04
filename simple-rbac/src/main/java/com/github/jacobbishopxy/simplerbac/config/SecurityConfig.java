package com.github.jacobbishopxy.simplerbac.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/user").access("hasAuthority('canReadUser')")
        .antMatchers("/admin").access("hasAuthority('canReadAdmin')")
        .anyRequest().authenticated()
        .and().httpBasic()
        .and().formLogin();
  }
}
