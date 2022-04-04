package com.github.jacobbishopxy.simplerbac.entity;

import javax.persistence.*;

@Entity
@Table(name = "rbac_user_privilege")
public class UserPrivilege {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String privilegeName;

  public UserPrivilege() {
  }

  public UserPrivilege(String privilegeName) {
    this.privilegeName = privilegeName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPrivilegeName() {
    return privilegeName;
  }

  public void setPrivilegeName(String privilegeName) {
    this.privilegeName = privilegeName;
  }

}
