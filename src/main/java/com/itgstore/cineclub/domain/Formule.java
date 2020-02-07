package com.itgstore.cineclub.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Formule.
 */
@Entity
@Table(name = "formule")
public class Formule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "date_reservation")
    private LocalDate dateReservation;

    @Column(name = "code")
    private Integer code;

    @Column(name = "prix_balcon")
    private Double prixBalcon;

    @Column(name = "prix_classique")
    private Double prixClassique;

    @Column(name = "statut_formule")
    private Boolean statutFormule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public Formule libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public Formule dateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Integer getCode() {
        return code;
    }

    public Formule code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Double getPrixBalcon() {
        return prixBalcon;
    }

    public Formule prixBalcon(Double prixBalcon) {
        this.prixBalcon = prixBalcon;
        return this;
    }

    public void setPrixBalcon(Double prixBalcon) {
        this.prixBalcon = prixBalcon;
    }

    public Double getPrixClassique() {
        return prixClassique;
    }

    public Formule prixClassique(Double prixClassique) {
        this.prixClassique = prixClassique;
        return this;
    }

    public void setPrixClassique(Double prixClassique) {
        this.prixClassique = prixClassique;
    }

    public Boolean isStatutFormule() {
        return statutFormule;
    }

    public Formule statutFormule(Boolean statutFormule) {
        this.statutFormule = statutFormule;
        return this;
    }

    public void setStatutFormule(Boolean statutFormule) {
        this.statutFormule = statutFormule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formule formule = (Formule) o;
        if (formule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Formule{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", dateReservation='" + getDateReservation() + "'" +
            ", code=" + getCode() +
            ", prixBalcon=" + getPrixBalcon() +
            ", prixClassique=" + getPrixClassique() +
            ", statutFormule='" + isStatutFormule() + "'" +
            "}";
    }
}
