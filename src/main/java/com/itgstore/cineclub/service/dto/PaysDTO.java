package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pays entity.
 */
public class PaysDTO implements Serializable {

    private Long id;

    @NotNull
    private String libele;

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

        PaysDTO paysDTO = (PaysDTO) o;
        if(paysDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paysDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaysDTO{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
