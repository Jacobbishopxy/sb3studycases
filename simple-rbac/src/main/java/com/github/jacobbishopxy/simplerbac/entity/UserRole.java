package com.github.jacobbishopxy.simplerbac.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "rbac_user_role")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String roleName;

  @OneToMany(mappedBy = "role")
  private List<UserRoleToPrivilege> userRoleToPrivileges;

  public UserRole() {
  }

  public UserRole(String roleName) {
    this.roleName = roleName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<UserRoleToPrivilege> getUserRoleToPrivileges() {
    return userRoleToPrivileges;
  }

  public void setUserRoleToPrivileges(List<UserRoleToPrivilege> userRoleToPrivileges) {
    this.userRoleToPrivileges = userRoleToPrivileges;
  }

}
