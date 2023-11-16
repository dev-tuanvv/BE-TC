package com.tutorcenter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),

    PARENT_READ("parent:read"),
    PARENT_UPDATE("parent:update"),
    PARENT_CREATE("parent:create"),
    PARENT_DELETE("parent:delete"),

    TUTOR_READ("tutor:read"),
    TUTOR_UPDATE("tutor:update"),
    TUTOR_CREATE("tutor:create"),
    TUTOR_DELETE("tutor:delete");

    @Getter
    final String permission;
}
