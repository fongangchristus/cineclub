package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CategorieFilm entity.
 */
public class CategorieFilmDTO implements Serializable {

    private Long id;

    @NotNull
    private String libele;

    private String pathCouverture;

    @Lob
    private byte[] couverture;
    private String couvertureContentType;

    private String description;

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

    public byte[] getCouverture() {
        return couverture;
    }

    public void setCouverture(byte[] couverture) {
        this.couverture = couverture;
    }

    public String getCouvertureContentType() {
        return couvertureContentType;
    }

    public void setCouvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategorieFilmDTO categorieFilmDTO = (CategorieFilmDTO) o;
        if(categorieFilmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorieFilmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategorieFilmDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", couverture='" + getCouverture() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
