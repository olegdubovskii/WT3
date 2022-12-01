package com.poit.archive.dao;

import com.poit.archive.criteria.Criteria;
import com.poit.archive.dao.exception.DAOException;
import com.poit.archive.entity.Dossier;
import java.util.List;

public interface DossierDAO {
    List<Dossier> find(Criteria criteria) throws DAOException;

    void save(Dossier dossier) throws DAOException;

    void update(Dossier dossier) throws DAOException;
}
