package com.github.jacobbishopxy.democasrbac.entity;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class UserAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false, unique = true)
  private String email;

  private boolean active;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<UserRole> roles;

  public UserAccount() {
  }

  public UserAccount(String nickname, String email, boolean active, Collection<UserRole> roles) {
    this.nickname = nickname;
    this.email = email;
    this.active = active;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Collection<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Collection<UserRole> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("User [")
        .append("id =").append(id)
        .append(", nickname =").append(nickname)
        .append(", email =").append(email)
        .append(", enabled =").append(active)
        .append(", roles =").append(roles)
        .append("]");

    return builder.toString();
  }
}
