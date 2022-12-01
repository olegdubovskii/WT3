package com.poit.archive.repository;

import com.poit.archive.entity.User;

public interface UserRepository {
    User findByLogin(String login);
}
