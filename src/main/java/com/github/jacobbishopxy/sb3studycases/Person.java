/**
 * 领域对象
 */

package com.github.jacobbishopxy.sb3studycases;

import org.springframework.data.annotation.Id;

public class Person {

  @Id
  private String id;

  private String firstName;
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName(String lastName) {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
