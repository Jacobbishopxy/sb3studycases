package com.github.jacobbishopxy.democasrbac.entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "cas_rbac_user_privilege")
public class UserPrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = true)
  private String description;

  @ManyToMany(mappedBy = "privileges")
  private Collection<UserRole> roles;

  public UserPrivilege() {
  }

  public UserPrivilege(Long id) {
    this.id = id;
  }

  public UserPrivilege(String name) {
    this.name = name;
  }

  public UserPrivilege(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public UserPrivilege(String name, String description, Collection<UserRole> roles) {
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Collection<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Collection<UserRole> roles) {
    this.roles = roles;
  }

}
