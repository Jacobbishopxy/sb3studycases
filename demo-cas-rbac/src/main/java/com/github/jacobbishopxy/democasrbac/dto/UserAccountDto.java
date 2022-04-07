package com.github.jacobbishopxy.democasrbac.dto;

import java.util.Collection;

public record UserAccountDto(
    String nickname,
    String email,
    Boolean active,
    Collection<Long> userRoleIds) {

}
