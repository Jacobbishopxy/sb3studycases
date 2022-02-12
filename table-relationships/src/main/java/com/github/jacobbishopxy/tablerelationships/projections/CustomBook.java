package com.github.jacobbishopxy.tablerelationships.projections;

import java.util.List;

import com.github.jacobbishopxy.tablerelationships.models.Author;
import com.github.jacobbishopxy.tablerelationships.models.Book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customBook", types = { Book.class })
public interface CustomBook {

  @Value("#{target.id}")
  long getId();

  String getTitle();

  List<Author> getAuthors();

  @Value("#{target.getAuthors().size()}")
  int getAuthorCount();
}
