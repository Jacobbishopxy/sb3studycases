package com.github.jacobbishopxy.simplerbac.repository;

import java.util.Optional;

import com.github.jacobbishopxy.simplerbac.entity.UserAccount;
import com.github.jacobbishopxy.simplerbac.entity.UserPrivilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Integer> {

  Optional<UserAccount> findByPrivilegeName(String privilegeName);

}
