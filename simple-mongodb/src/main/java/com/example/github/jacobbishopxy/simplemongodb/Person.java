package com.example.github.jacobbishopxy.simplemongodb;

import org.springframework.data.annotation.Id;

public class Person {
  @Id
  private String id;

  private String firstName;
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
