package cnam.medical.pacs.domain.model;

import java.time.LocalDate;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


/** 
   * Patient is my Patient model uses to store information about patients identity to database
   * This class extends PanacheEntity
   */

@Entity
public class Patient extends PanacheEntity{

    public String lastName;
    public String firstName;
    public LocalDate birth;
    public Sex sex;


    public enum Sex {
        MALE, FEMALE
      }
    
}
