package entities;

public enum Role {
    USER("role.user"),
    ADMINISTRATOR("role.admin");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return Long.valueOf(ordinal());
    }

    public String getName() {
        return name;
    }
}
