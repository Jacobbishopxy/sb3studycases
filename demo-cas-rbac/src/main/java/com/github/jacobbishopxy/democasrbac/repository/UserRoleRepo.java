package com.github.jacobbishopxy.democasrbac.repository;

import java.util.Optional;

import com.github.jacobbishopxy.democasrbac.entity.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

  Optional<UserRole> findByName(String name);

  void deleteByName(String name);

}
