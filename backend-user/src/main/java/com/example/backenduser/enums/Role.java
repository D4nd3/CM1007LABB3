package com.example.backenduser.enums;

public enum Role{
    PATIENT(1),
    PRACTITIONER(2),
    OTHER(3),
    ADMIN(4);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role fromId(int id) {
        for (Role role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role ID: " + id);
    }
}