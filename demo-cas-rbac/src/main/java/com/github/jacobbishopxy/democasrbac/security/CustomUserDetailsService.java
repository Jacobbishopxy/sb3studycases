package com.github.jacobbishopxy.democasrbac.security;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.github.jacobbishopxy.democasrbac.entity.UserAccount;
import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;
import com.github.jacobbishopxy.democasrbac.entity.UserRole;
import com.github.jacobbishopxy.democasrbac.repository.UserAccountRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserAccountRepo userAccountRepo;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<UserAccount> userAccountOpt = userAccountRepo.findByNickname(username);
    if (userAccountOpt.isEmpty()) {
      return new CustomUserDetails("visitor", true, new HashSet<GrantedAuthority>());
    }

    UserAccount userAccount = userAccountOpt.get();

    Set<GrantedAuthority> authorities = new HashSet<>();

    for (UserRole userRole : userAccount.getRoles()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
      for (UserPrivilege userPrivilege : userRole.getPrivileges()) {
        authorities.add(new SimpleGrantedAuthority(userPrivilege.getName()));
      }
    }

    return new CustomUserDetails(username, userAccount.getActive(), authorities);
  }

}
