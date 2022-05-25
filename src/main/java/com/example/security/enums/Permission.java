package com.example.security.enums;

public enum Permission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
