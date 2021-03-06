package com.github.jacobbishopxy.democasrbac.config;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SingleSignOutFilter singleSignOutFilter;

  @Autowired
  private LogoutFilter logoutFilter;

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private CasAuthenticationFilter casAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/validate", "/login/cas").permitAll()
        .antMatchers("/admin").hasRole("SUPERVISOR")
        .anyRequest().authenticated()
        .and()
        .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilter(casAuthenticationFilter)
        .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
        .addFilterBefore(logoutFilter, LogoutFilter.class)
        .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
  }
}
