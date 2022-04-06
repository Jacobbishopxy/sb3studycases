package com.github.jacobbishopxy.democasrbac.entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class UserPrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany
  private Collection<UserRole> roles;

  public UserPrivilege() {
  }

  public UserPrivilege(String name) {
    this.name = name;
  }

  public UserPrivilege(String name, Collection<UserRole> roles) {
    this.name = name;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Collection<UserRole> roles) {
    this.roles = roles;
  }

}
