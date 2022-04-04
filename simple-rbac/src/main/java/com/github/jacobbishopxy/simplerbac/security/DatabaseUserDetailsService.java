package com.github.jacobbishopxy.simplerbac.security;

import java.util.HashSet;
import java.util.Set;

import com.github.jacobbishopxy.simplerbac.entity.UserAccount;
import com.github.jacobbishopxy.simplerbac.entity.UserRoleToPrivilege;
import com.github.jacobbishopxy.simplerbac.entity.UserToRole;
import com.github.jacobbishopxy.simplerbac.repository.UserAccountRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseUserDetailsService implements UserDetailsService {

  private final UserAccountRepository userAccountRepository;

  public DatabaseUserDetailsService(UserAccountRepository userAccountRepository) {
    this.userAccountRepository = userAccountRepository;
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) {
    UserAccount userAccount = userAccountRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    Set<GrantedAuthority> authorities = new HashSet<>();

    for (UserToRole userToRole : userAccount.getUserToRoles()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + userToRole.getRole().getRoleName()));
      for (UserRoleToPrivilege userRoleToPrivilege : userToRole.getRole().getUserRoleToPrivileges()) {
        authorities.add(new SimpleGrantedAuthority(userRoleToPrivilege.getPrivilege().getPrivilegeName()));
      }
    }

    return new CustomUserDetails(
        userAccount.getUsername(),
        userAccount.getPassword(),
        userAccount.isActive(),
        authorities);
  }

}
