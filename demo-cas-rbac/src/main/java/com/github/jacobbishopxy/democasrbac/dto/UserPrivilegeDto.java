package com.github.jacobbishopxy.democasrbac.dto;

import com.github.jacobbishopxy.democasrbac.entity.UserPrivilege;

public record UserPrivilegeDto(String name, String description) {

  public UserPrivilege intoUserPrivilege() {
    return new UserPrivilege(name, description);
  }

}
