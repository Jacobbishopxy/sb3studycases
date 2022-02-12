package com.github.jacobbishopxy.tablerelationships.repositories;

import com.github.jacobbishopxy.tablerelationships.models.Book;
import com.github.jacobbishopxy.tablerelationships.projections.CustomBook;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CustomBook.class)
public interface BookRepository extends CrudRepository<Book, Long> {

}
