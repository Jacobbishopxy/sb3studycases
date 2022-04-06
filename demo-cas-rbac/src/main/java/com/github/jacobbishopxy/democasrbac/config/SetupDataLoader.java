package com.github.jacobbishopxy.democasrbac.config;

import java.util.List;

import com.github.jacobbishopxy.democasrbac.entity.UserAccount;
import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;
import com.github.jacobbishopxy.democasrbac.entity.UserRole;
import com.github.jacobbishopxy.democasrbac.repository.UserAccountRepo;
import com.github.jacobbishopxy.democasrbac.repository.UserPrivilegeRepo;
import com.github.jacobbishopxy.democasrbac.repository.UserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired
  private UserAccountRepo userAccountRepo;

  @Autowired
  private UserRoleRepo userRoleRepo;

  @Autowired
  private UserPrivilegeRepo userPrivilegeRepo;

  @Override
  @Transactional
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    if (alreadySetup) {
      return;
    }

    // create initial privileges
    final UserPrivilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
    final UserPrivilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
    final UserPrivilege adminPrivilege = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");

    // create initial roles
    final List<UserPrivilege> visitorPrivileges = List.of(readPrivilege);
    final List<UserPrivilege> editorPrivileges = List.of(readPrivilege, writePrivilege);
    final List<UserPrivilege> supervisorPrivileges = List.of(readPrivilege, writePrivilege, adminPrivilege);
    final UserRole visitor = createRoleIfNotFound("VISITOR", visitorPrivileges);
    final UserRole editor = createRoleIfNotFound("EDITOR", editorPrivileges);
    final UserRole supervisor = createRoleIfNotFound("SUPERVISOR", supervisorPrivileges);

    // create initial user accounts
    createUserIfNotFound("visitor@example.com", "visitor", true, List.of(visitor));
    createUserIfNotFound("editor@example.com", "editor", true, List.of(editor));
    createUserIfNotFound("supervisor@example.com", "supervisor", true, List.of(supervisor));

    alreadySetup = true;
  }

  @Transactional
  UserPrivilege createPrivilegeIfNotFound(String name) {
    return userPrivilegeRepo
        .findByName(name)
        .map(privilege -> {
          return privilege;
        })
        .orElseGet(() -> {
          UserPrivilege privilege = new UserPrivilege(name);
          userPrivilegeRepo.save(privilege);
          return privilege;
        });
  }

  @Transactional
  UserRole createRoleIfNotFound(String name, List<UserPrivilege> privileges) {
    return userRoleRepo
        .findByName(name)
        .map(role -> {
          return role;
        })
        .orElseGet(() -> {
          UserRole role = new UserRole(name, privileges);
          role.setPrivileges(privileges);
          userRoleRepo.save(role);
          return role;
        });
  }

  @Transactional
  UserAccount createUserIfNotFound(String nickname, String email, boolean enabled, List<UserRole> roles) {
    return userAccountRepo
        .findByEmail(email)
        .map(user -> {
          return user;
        })
        .orElseGet(() -> {
          UserAccount user = new UserAccount(nickname, email, enabled, roles);
          user.setRoles(roles);
          userAccountRepo.save(user);
          return user;
        });
  }

}
