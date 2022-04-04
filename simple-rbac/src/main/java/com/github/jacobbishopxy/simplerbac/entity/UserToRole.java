package com.github.jacobbishopxy.simplerbac.entity;

import javax.persistence.*;

@Entity
@Table(name = "rbac_user_to_role")
public class UserToRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  private UserAccount user;

  @ManyToOne
  private UserRole role;

  public UserToRole() {
  }

  public UserToRole(UserAccount user, UserRole role) {
    this.user = user;
    this.role = role;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public UserAccount getUser() {
    return user;
  }

  public void setUser(UserAccount user) {
    this.user = user;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

}
