package com.itgstore.cineclub.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.itgstore.cineclub.domain.enumeration.TypePlace;

/**
 * A DTO for the PrixTicket entity.
 */
public class PrixTicketDTO implements Serializable {

    private Long id;

    private Double prix;

    private String code;

    private TypePlace typePlace;

    private String description;

    private Long projectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrixTicketDTO prixTicketDTO = (PrixTicketDTO) o;
        if(prixTicketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prixTicketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrixTicketDTO{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", code='" + getCode() + "'" +
            ", typePlace='" + getTypePlace() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
