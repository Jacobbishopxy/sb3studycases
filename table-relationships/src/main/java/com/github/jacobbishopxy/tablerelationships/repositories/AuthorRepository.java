package com.github.jacobbishopxy.tablerelationships.repositories;

import com.github.jacobbishopxy.tablerelationships.models.Author;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
