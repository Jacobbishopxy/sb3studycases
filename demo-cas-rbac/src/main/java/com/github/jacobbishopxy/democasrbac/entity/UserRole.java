package com.github.jacobbishopxy.democasrbac.entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = true)
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<UserPrivilege> privileges;

  public UserRole() {
  }

  public UserRole(Long id) {
    this.id = id;
  }

  public UserRole(String name) {
    this.name = name;
  }

  public UserRole(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UserRole(String name, String description, Collection<UserPrivilege> privileges) {
    this.name = name;
    this.description = description;
    this.privileges = privileges;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Collection<UserPrivilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Collection<UserPrivilege> privileges) {
    this.privileges = privileges;
  }

}
