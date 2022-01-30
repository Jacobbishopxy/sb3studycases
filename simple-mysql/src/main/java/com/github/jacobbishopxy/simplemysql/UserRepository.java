package com.github.jacobbishopxy.simplemysql;

import org.springframework.data.repository.CrudRepository;

// 该接口会由 Spring **自动实现** 成为一个 Bean 包含了 CRUD 方法
public interface UserRepository extends CrudRepository<User, Integer> {

}
