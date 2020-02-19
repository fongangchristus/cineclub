package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.itgstore.cineclub.domain.enumeration.TypePlace;

/**
 * A DTO for the Ticket entity.
 */
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    private Double prix;

    private Boolean statutDisponibilite;

    private TypePlace typePlace;

    private Double nombreTicketTotal;

    private Double nombreTicketRestant;

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

    public Boolean isStatutDisponibilite() {
        return statutDisponibilite;
    }

    public void setStatutDisponibilite(Boolean statutDisponibilite) {
        this.statutDisponibilite = statutDisponibilite;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public Double getNombreTicketTotal() {
        return nombreTicketTotal;
    }

    public void setNombreTicketTotal(Double nombreTicketTotal) {
        this.nombreTicketTotal = nombreTicketTotal;
    }

    public Double getNombreTicketRestant() {
        return nombreTicketRestant;
    }

    public void setNombreTicketRestant(Double nombreTicketRestant) {
        this.nombreTicketRestant = nombreTicketRestant;
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

        TicketDTO ticketDTO = (TicketDTO) o;
        if(ticketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", statutDisponibilite='" + isStatutDisponibilite() + "'" +
            ", typePlace='" + getTypePlace() + "'" +
            ", nombreTicketTotal=" + getNombreTicketTotal() +
            ", nombreTicketRestant=" + getNombreTicketRestant() +
            "}";
    }
}
