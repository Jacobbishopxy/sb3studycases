package com.github.jacobbishopxy.democasrbac.service;

import java.util.List;
import java.util.Optional;

import com.github.jacobbishopxy.democasrbac.dto.UserAccountDto;
import com.github.jacobbishopxy.democasrbac.dto.UserPrivilegeDto;
import com.github.jacobbishopxy.democasrbac.dto.UserRoleDto;
import com.github.jacobbishopxy.democasrbac.entity.UserAccount;
import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;
import com.github.jacobbishopxy.democasrbac.entity.UserRole;
import com.github.jacobbishopxy.democasrbac.repository.UserAccountRepo;
import com.github.jacobbishopxy.democasrbac.repository.UserPrivilegeRepo;
import com.github.jacobbishopxy.democasrbac.repository.UserRoleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
  @Autowired
  UserAccountRepo userAccountRepo;

  @Autowired
  UserRoleRepo userRoleRepo;

  @Autowired
  UserPrivilegeRepo userPrivilegeRepo;

  public UserPrivilege registerPrivilege(UserPrivilegeDto dto) {
    return userPrivilegeRepo.save(dto.intoUserPrivilege());
  }

  public Optional<UserPrivilege> modifyPrivilege(Long id, UserPrivilegeDto dto) {
    return userPrivilegeRepo.findById(id).map(p -> {
      if (dto.name() != null) {
        p.setName(dto.name());
      }
      if (dto.description() != null) {
        p.setDescription(dto.description());
      }
      return userPrivilegeRepo.save(p);
    });
  }

  public void deletePrivilege(Long id) {
    userPrivilegeRepo.deleteById(id);
  }

  public UserRole registerRole(UserRoleDto dto) {
    // only valid privilege ids are allowed
    List<UserPrivilege> privileges = userPrivilegeRepo.findAllById(dto.userPrivilegeIds());
    return userRoleRepo.save(new UserRole(dto.name(), dto.description(), privileges));
  }

  public Optional<UserRole> modifyRole(Long id, UserRoleDto dto) {
    return userRoleRepo
        .findById(id)
        .map(r -> {
          if (dto.name() != null) {
            r.setName(dto.name());
          }
          if (dto.description() != null) {
            r.setDescription(dto.description());
          }
          if (dto.userPrivilegeIds() != null) {
            List<UserPrivilege> privileges = userPrivilegeRepo.findAllById(dto.userPrivilegeIds());
            r.setPrivileges(privileges);
          }
          return userRoleRepo.save(r);
        });
  }

  public void deleteRole(Long id) {
    userRoleRepo.deleteById(id);
  }

  public UserAccount registerAccount(UserAccountDto dto) {
    // only valid role ids are allowed
    List<UserRole> roles = userRoleRepo.findAllById(dto.userRoleIds());
    return userAccountRepo.save(new UserAccount(dto.nickname(), dto.email(), dto.active(), roles));
  }

  public Optional<UserAccount> modifyAccount(Long id, UserAccountDto dto) {
    return userAccountRepo
        .findById(id)
        .map(a -> {
          if (dto.nickname() != null) {
            a.setNickname(dto.nickname());
          }
          if (dto.email() != null) {
            a.setEmail(dto.email());
          }
          if (dto.active() != null) {
            a.setActive(dto.active());
          }
          if (dto.userRoleIds() != null) {
            List<UserRole> roles = userRoleRepo.findAllById(dto.userRoleIds());
            a.setRoles(roles);
          }
          return userAccountRepo.save(a);
        });
  }

  public void deleteAccount(Long id) {
    userAccountRepo.deleteById(id);
  }

}
