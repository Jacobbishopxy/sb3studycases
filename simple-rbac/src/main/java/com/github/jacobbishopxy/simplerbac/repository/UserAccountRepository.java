package com.github.jacobbishopxy.simplerbac.repository;

import java.util.Optional;

import com.github.jacobbishopxy.simplerbac.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

  Optional<UserAccount> findByUsername(String username);
}
