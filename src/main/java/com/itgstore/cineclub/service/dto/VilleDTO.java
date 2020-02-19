package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ville entity.
 */
public class VilleDTO implements Serializable {

    private Long id;

    @NotNull
    private String libele;

    private String description;

    private Long paysId;

    private String paysLibele;


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

    public Long getPaysId() {
        return paysId;
    }

    public void setPaysId(Long paysId) {
        this.paysId = paysId;
    }

    public String getPaysLibele() {
        return paysLibele;
    }

    public void setPaysLibele(String paysLibele) {
        this.paysLibele = paysLibele;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VilleDTO villeDTO = (VilleDTO) o;
        if(villeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), villeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VilleDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
