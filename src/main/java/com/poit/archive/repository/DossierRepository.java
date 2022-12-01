package com.poit.archive.repository;

import com.poit.archive.entity.Dossier;
import java.util.List;

public interface DossierRepository {

    List<Dossier> findAll();

    Dossier findByCardNum(String cardNum);

    void save(Dossier dossier);

    void update(Dossier dossier);
}
