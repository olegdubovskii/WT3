package com.poit.archive.dao.wrapper;

import com.poit.archive.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserWrapper {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public UserWrapper() {
        this.users = new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public UserWrapper(List<User> users) {
        this.users = users;
    }

    public void add(User u) {
        users.add(u);
    }
}
