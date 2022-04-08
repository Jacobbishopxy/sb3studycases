package com.github.jacobbishopxy.democasrbac.service;

import java.util.Map;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomCasService extends AbstractCasAssertionUserDetailsService {

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Override
  protected UserDetails loadUserDetails(Assertion assertion) {
    String username = assertion.getPrincipal().getName();

    // can be loaded into CustomUserDetails
    Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
    for (String key : attributes.keySet()) {
      System.out.println(">>>>>>>>>> " + key + ": " + attributes.get(key));
    }

    return customUserDetailsService.loadUserByUsername(username);
  }
}
