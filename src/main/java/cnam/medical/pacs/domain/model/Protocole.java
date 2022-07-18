package cnam.medical.pacs.domain.model;

import java.util.Date;

import javax.persistence.Entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Protocole is the model uses to store information about how is structured the
 * exams
 * This class extends PanacheEntity
 */

@Entity
public class Protocole extends PanacheEntity {

    public Technique technique;
    public Membre membre;
    public Type type;
    public Injection injection;
    public Date date= new Date();

    public enum Technique {
        SCANNER, RADIO, MEDECINE_NUCLEAIRE, ECHOGRAPHIE
    }

    public enum Membre {
        CRANE, TRONC, JAMBE, BRAS
    }

    public enum Type {
        MOU, OS
    }

    public enum Injection {
        OUI, NON
    }

}
