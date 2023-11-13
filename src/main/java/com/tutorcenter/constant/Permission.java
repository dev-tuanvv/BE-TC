package com.tutorcenter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),

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
