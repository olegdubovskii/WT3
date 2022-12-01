package com.poit.archive.dao.wrapper;

import com.poit.archive.entity.Dossier;
import java.util.ArrayList;
import java.util.List;

public class DossierWrapper {
    private List<Dossier> dossiers;

    public DossierWrapper() {
        this.dossiers = new ArrayList<>();
    }

    public List<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public DossierWrapper(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public void add(Dossier d) {
        dossiers.add(d);
    }
}
