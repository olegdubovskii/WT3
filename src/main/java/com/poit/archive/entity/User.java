package com.poit.archive.entity;

import java.util.Objects;

public class User {
    String name;
    String password;
    Role role;

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }

        if (!Objects.equals(name, user.name)) {
            return false;
        }
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
