package cnam.medical.pacs.domain.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Patient extends PanacheEntity{

    public String lastName;
    public String firstName;
    public LocalDate birthday;
    public Sex sex;


    private enum Sex {
        MALE, FEMALE
      }
    
}
