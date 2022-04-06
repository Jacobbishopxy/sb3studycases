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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<UserPrivilege> privileges;

  public UserRole() {
  }

  public UserRole(String name, Collection<UserPrivilege> privileges) {
    this.name = name;
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

  public Collection<UserPrivilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Collection<UserPrivilege> privileges) {
    this.privileges = privileges;
  }

}
