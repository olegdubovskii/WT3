package com.poit.archive.service.impl;

import com.poit.archive.entity.Dossier;
import com.poit.archive.repository.DossierRepository;
import com.poit.archive.service.DossierService;
import java.util.List;

public class DossierServiceImpl implements DossierService {
    private final DossierRepository dossierRepository;

    public DossierServiceImpl(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @Override
    public List<Dossier> findAll() {
        return dossierRepository.findAll();
    }

    @Override
    public void add(Dossier dossier) {
        dossierRepository.save(dossier);
    }

    @Override
    public void update(Dossier dossier) {
        dossierRepository.update(dossier);
    }
}
