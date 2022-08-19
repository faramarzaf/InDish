package com.faraf;

public enum RoleType {

    USER(1, "USER"),
    ADMIN(2, "ADMIN");

    private final Integer key;
    private final String value;


    RoleType(Integer key, String value) {
        this.key = key;
        this.value = value;

    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static RoleType of(Integer key) {
        for (RoleType roleType : RoleType.values()) {
            if (key.equals(roleType.getKey()))
                return roleType;
        }
        return null;
    }

    public static RoleType of(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (value.equals(roleType.getValue()))
                return roleType;
        }
        return null;
    }

}
