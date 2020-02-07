package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ticket entity.
 */
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String codePaiement;

    @NotNull
    private String numeroPlace;

    @NotNull
    private Double prix;

    private Boolean statutTicket;

    private Long prixTicketId;

    private Long projectionId;

    private Long reservationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePaiement() {
        return codePaiement;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean isStatutTicket() {
        return statutTicket;
    }

    public void setStatutTicket(Boolean statutTicket) {
        this.statutTicket = statutTicket;
    }

    public Long getPrixTicketId() {
        return prixTicketId;
    }

    public void setPrixTicketId(Long prixTicketId) {
        this.prixTicketId = prixTicketId;
    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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
            ", codePaiement='" + getCodePaiement() + "'" +
            ", numeroPlace='" + getNumeroPlace() + "'" +
            ", prix=" + getPrix() +
            ", statutTicket='" + isStatutTicket() + "'" +
            "}";
    }
}
