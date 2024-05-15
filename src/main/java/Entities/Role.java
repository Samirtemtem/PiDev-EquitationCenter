package Entities;

public enum Role {
    ROLE_ADMIN(new String[]{"ROLE_ADMIN"}),
    ROLE_CLIENT(new String[]{"ROLE_CLIENT"}),
    ROLE_INSTRUCTOR(new String[]{"ROLE_INSTRUCTOR"}),
    ROLE_VET(new String[]{"ROLE_VET"});

    private final String[] value;

    Role(String[] value) {
        this.value = value;
    }

    public String[] getValue() {
        return value;
    }
}