package com.itgstore.cineclub.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Formule entity.
 */
public class FormuleDTO implements Serializable {

    private Long id;

    private String libele;

    private LocalDate dateReservation;

    private Integer code;

    private Double prixBalcon;

    private Double prixClassique;

    private Boolean statutFormule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Double getPrixBalcon() {
        return prixBalcon;
    }

    public void setPrixBalcon(Double prixBalcon) {
        this.prixBalcon = prixBalcon;
    }

    public Double getPrixClassique() {
        return prixClassique;
    }

    public void setPrixClassique(Double prixClassique) {
        this.prixClassique = prixClassique;
    }

    public Boolean isStatutFormule() {
        return statutFormule;
    }

    public void setStatutFormule(Boolean statutFormule) {
        this.statutFormule = statutFormule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormuleDTO formuleDTO = (FormuleDTO) o;
        if(formuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormuleDTO{" +
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
