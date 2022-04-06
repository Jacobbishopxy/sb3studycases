package com.github.jacobbishopxy.democasrbac.repository;

import java.util.Optional;

import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepo extends JpaRepository<UserPrivilege, Long> {

  Optional<UserPrivilege> findByName(String name);

  void deleteByName(String name);

}
