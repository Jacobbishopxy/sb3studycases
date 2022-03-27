package com.github.jacobbishopxy.simplesecurity.repository;

import java.util.Optional;

import com.github.jacobbishopxy.simplesecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

  public Optional<User> findByEmail(String email);
}
