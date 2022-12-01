package com.poit.archive.dao;

import com.poit.archive.criteria.Criteria;
import com.poit.archive.dao.exception.DAOException;
import com.poit.archive.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> find(Criteria criteria) throws DAOException;
}
