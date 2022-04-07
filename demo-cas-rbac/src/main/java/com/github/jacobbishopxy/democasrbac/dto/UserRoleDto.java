package com.github.jacobbishopxy.democasrbac.dto;

import java.util.Collection;

public record UserRoleDto(
    String name,
    String description,
    Collection<Long> userPrivilegeIds) {

}
