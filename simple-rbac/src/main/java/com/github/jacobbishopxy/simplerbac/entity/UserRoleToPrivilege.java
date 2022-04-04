package com.github.jacobbishopxy.simplerbac.entity;

import javax.persistence.*;

@Entity
@Table(name = "rbac_user_role_to_privilege")
public class UserRoleToPrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private UserRole role;

  @ManyToOne
  private UserPrivilege privilege;

  public UserRoleToPrivilege() {
  }

  public UserRoleToPrivilege(UserRole role, UserPrivilege privilege) {
    this.role = role;
    this.privilege = privilege;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public UserPrivilege getPrivilege() {
    return privilege;
  }

  public void setPrivilege(UserPrivilege privilege) {
    this.privilege = privilege;
  }

}
