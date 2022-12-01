package com.poit.archive.service;

import com.poit.archive.entity.Dossier;
import java.util.List;

public interface DossierService {

    List<Dossier> findAll();

    void add(Dossier dossier);

    void update(Dossier dossier);
}
