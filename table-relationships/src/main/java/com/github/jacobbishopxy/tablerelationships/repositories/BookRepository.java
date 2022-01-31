package com.github.jacobbishopxy.tablerelationships.repositories;

import com.github.jacobbishopxy.tablerelationships.models.Book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
