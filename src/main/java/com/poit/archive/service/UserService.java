package com.poit.archive.service;

import com.poit.archive.entity.User;

public interface UserService {

    User signIn(String login, String password);
}
