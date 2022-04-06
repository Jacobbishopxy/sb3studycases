package com.github.jacobbishopxy.democasrbac.repository;

import java.util.Optional;

import com.github.jacobbishopxy.democasrbac.entity.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {

  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findByNickname(String nickname);

  void deleteByEmail(String email);

  void deleteByNickname(String nickname);

}
