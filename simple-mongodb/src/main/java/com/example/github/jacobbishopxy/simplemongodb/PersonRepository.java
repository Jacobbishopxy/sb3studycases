/**
 * Repo
 *
 * 本仓库作为一个接口允许你对 Person 对象进行多样化的操作。其继承了 `MongoRepository`，
 * 转而继承了由 Spring Data Common 定义的 `PagingAndSortingRepository` 接口。
 *
 * 在运行时，Spring Data REST 自动创建一个该接口的实现。接着它会使用 `@RepositoryRestResource`
 * 注解使 Spring MVC 创建 RESTful 的 `/people` 端点。
 */

package com.example.github.jacobbishopxy.simplemongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {
  List<Person> findByLastName(@Param("name") String name);
}
