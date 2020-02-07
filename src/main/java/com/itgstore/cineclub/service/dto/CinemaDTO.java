package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Cinema entity.
 */
public class CinemaDTO implements Serializable {

    private Long id;

    @NotNull
    private String libele;

    private String description;

    private String proprietaire;

    private String lienFacebook;

    private String liensWathsapp;

    private String lienYoutube;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getLienFacebook() {
        return lienFacebook;
    }

    public void setLienFacebook(String lienFacebook) {
        this.lienFacebook = lienFacebook;
    }

    public String getLiensWathsapp() {
        return liensWathsapp;
    }

    public void setLiensWathsapp(String liensWathsapp) {
        this.liensWathsapp = liensWathsapp;
    }

    public String getLienYoutube() {
        return lienYoutube;
    }

    public void setLienYoutube(String lienYoutube) {
        this.lienYoutube = lienYoutube;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CinemaDTO cinemaDTO = (CinemaDTO) o;
        if(cinemaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cinemaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CinemaDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            ", proprietaire='" + getProprietaire() + "'" +
            ", lienFacebook='" + getLienFacebook() + "'" +
            ", liensWathsapp='" + getLiensWathsapp() + "'" +
            ", lienYoutube='" + getLienYoutube() + "'" +
            "}";
    }
}
