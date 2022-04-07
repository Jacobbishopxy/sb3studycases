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
    createUserIfNotFound("visitor", "visitor@example.com", true, List.of(visitor));
    createUserIfNotFound("editor", "editor@example.com", true, List.of(editor));
    createUserIfNotFound("supervisor", "supervisor@example.com", true, List.of(supervisor));

    alreadySetup = true;
  }

  @Transactional
  UserPrivilege createPrivilegeIfNotFound(String name) {
    return userPrivilegeRepo
        .findByName(name)
        .orElse(userPrivilegeRepo.save(new UserPrivilege(name)));
  }

  @Transactional
  UserRole createRoleIfNotFound(String name, List<UserPrivilege> privileges) {
    return userRoleRepo
        .findByName(name)
        .orElse(userRoleRepo.save(new UserRole(name, "test case", privileges)));
  }

  @Transactional
  UserAccount createUserIfNotFound(String nickname, String email, boolean active, List<UserRole> roles) {
    return userAccountRepo
        .findByEmail(email)
        .orElse(userAccountRepo.save(new UserAccount(nickname, email, active, roles)));
  }

}
