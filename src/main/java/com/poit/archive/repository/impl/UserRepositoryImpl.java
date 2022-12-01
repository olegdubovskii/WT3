package com.poit.archive.repository.impl;

import com.poit.archive.criteria.Criteria;
import com.poit.archive.dao.UserDAO;
import com.poit.archive.dao.exception.DAOException;
import com.poit.archive.entity.User;
import com.poit.archive.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findByLogin(String login) {
        var criteria = new Criteria();
        criteria.add("name", login);
        try {
            return userDAO.find(criteria).get(0);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
