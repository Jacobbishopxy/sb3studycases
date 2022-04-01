package com.github.jacobbishopxy.simplecas;

import java.util.Map;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailsService extends AbstractCasAssertionUserDetailsService {

  @Override
  protected UserDetails loadUserDetails(Assertion assertion) {
    // custom user info
    String username = assertion.getPrincipal().getName();

    Map<String, Object> attributes = assertion.getPrincipal().getAttributes();

    for (String key : attributes.keySet()) {
      System.out.println(">>>>>>>>>> " + key + ": " + attributes.get(key));
    }

    return new User(username, "", true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER"));
  }

}
