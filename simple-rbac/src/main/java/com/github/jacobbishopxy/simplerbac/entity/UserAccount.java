package com.github.jacobbishopxy.simplerbac.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "rbac_user_account")
public class UserAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String username;

  private String password;

  private boolean active;

  @OneToMany(mappedBy = "user")
  private List<UserToRole> userToRoles;

  public UserAccount() {
  }

  public UserAccount(String username, String password, boolean active) {
    this.username = username;
    this.password = password;
    this.active = active;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<UserToRole> getUserToRoles() {
    return userToRoles;
  }

  public void setUserToRoles(List<UserToRole> userToRoles) {
    this.userToRoles = userToRoles;
  }

}
