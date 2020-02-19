package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Salle entity.
 */
public class SalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String libele;

    private String pathCouverture;

    private String code;

    private String description;

    private Integer nombrePlaceTotale;

    private Integer nombrePlaceBalcon;

    private Integer nombrePlaceClasique;

    private Long adresseId;

    private Long villeId;
    private String villeLibele;

    private Long cinemaId;
    private String cinemaLibele;


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

    public String getPathCouverture() {
        return pathCouverture;
    }

    public void setPathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNombrePlaceTotale() {
        return nombrePlaceTotale;
    }

    public void setNombrePlaceTotale(Integer nombrePlaceTotale) {
        this.nombrePlaceTotale = nombrePlaceTotale;
    }

    public Integer getNombrePlaceBalcon() {
        return nombrePlaceBalcon;
    }

    public void setNombrePlaceBalcon(Integer nombrePlaceBalcon) {
        this.nombrePlaceBalcon = nombrePlaceBalcon;
    }

    public Integer getNombrePlaceClasique() {
        return nombrePlaceClasique;
    }

    public void setNombrePlaceClasique(Integer nombrePlaceClasique) {
        this.nombrePlaceClasique = nombrePlaceClasique;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public Long getVilleId() {
        return villeId;
    }

    public void setVilleId(Long villeId) {
        this.villeId = villeId;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getVilleLibele() {
        return villeLibele;
    }

    public void setVilleLibele(String villeLibele) {
        this.villeLibele = villeLibele;
    }

    public String getCinemaLibele() {
        return cinemaLibele;
    }

    public void setCinemaLibele(String cinemaLibele) {
        this.cinemaLibele = cinemaLibele;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalleDTO salleDTO = (SalleDTO) o;
        if(salleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", nombrePlaceTotale=" + getNombrePlaceTotale() +
            ", nombrePlaceBalcon=" + getNombrePlaceBalcon() +
            ", nombrePlaceClasique=" + getNombrePlaceClasique() +
            "}";
    }
}
