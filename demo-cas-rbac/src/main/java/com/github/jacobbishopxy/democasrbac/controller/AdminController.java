package com.github.jacobbishopxy.democasrbac.controller;

import java.util.List;

import com.github.jacobbishopxy.democasrbac.dto.UserAccountDto;
import com.github.jacobbishopxy.democasrbac.dto.UserPrivilegeDto;
import com.github.jacobbishopxy.democasrbac.dto.UserRoleDto;
import com.github.jacobbishopxy.democasrbac.entity.UserAccount;
import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;
import com.github.jacobbishopxy.democasrbac.entity.UserRole;
import com.github.jacobbishopxy.democasrbac.service.UserRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private UserRegistrationService urService;

  // =======================================================================
  // Query methods
  // =======================================================================

  @GetMapping("/users")
  public List<UserAccount> showAllUsers() {
    return urService.getAllUsers();
  }

  @GetMapping("/user/{id}")
  public UserAccount showUserById(@PathVariable Long id) {
    return urService
        .getUserById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User with id %d not found", id)));
  }

  // =======================================================================
  // Mutation methods
  // =======================================================================

  @PostMapping("/privilege")
  UserPrivilege registerPrivilege(@RequestBody UserPrivilegeDto dto) {
    return urService.registerPrivilege(dto);
  }

  @PutMapping("/privilege/{id}")
  UserPrivilege modifyPrivilege(@PathVariable Long id, @RequestBody UserPrivilegeDto dto) {
    return urService
        .modifyPrivilege(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User privilege with id %d not found", id)));
  }

  @DeleteMapping("/privilege/{id}")
  void deletePrivilege(@PathVariable Long id) {
    urService.deletePrivilege(id);
  }

  @PostMapping("/role")
  UserRole registerRole(@RequestBody UserRoleDto dto) {
    return urService.registerRole(dto);
  }

  @PutMapping("/role/{id}")
  UserRole modifyRole(@PathVariable Long id, @RequestBody UserRoleDto dto) {
    return urService
        .modifyRole(id, dto)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User role with id %d not found", id)));
  }

  @DeleteMapping("/role/{id}")
  void deleteRole(@PathVariable Long id) {
    urService.deleteRole(id);
  }

  @PostMapping("/user")
  UserAccount registerUser(@RequestBody UserAccountDto user) {
    return urService.registerAccount(user);
  }

  @PutMapping("/user/{id}")
  UserAccount modifyUser(@PathVariable Long id, @RequestBody UserAccountDto user) {
    return urService
        .modifyAccount(id, user)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("User with id %d not found", id)));
  }

  @DeleteMapping("/user/{id}")
  void deleteUser(@PathVariable Long id) {
    urService.deleteAccount(id);
  }
}
