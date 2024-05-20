package ua.skillup.data;

public enum Users {
    STANDARD_USER("standard_user", "secret_sauce");
    // TODO: placeholder for others

    private final String username;
    private final String password;

    Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
