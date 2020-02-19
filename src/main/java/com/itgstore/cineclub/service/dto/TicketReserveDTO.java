package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.itgstore.cineclub.domain.enumeration.TypePlace;

/**
 * A DTO for the TicketReserve entity.
 */
public class TicketReserveDTO implements Serializable {

    private Long id;

    @NotNull
    private String codePaiement;

    @NotNull
    private String numeroPlace;

    @NotNull
    private Double prix;

    private Boolean statutTicket;

    private TypePlace typePlace;

    private String codeSup;

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

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public String getCodeSup() {
        return codeSup;
    }

    public void setCodeSup(String codeSup) {
        this.codeSup = codeSup;
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

        TicketReserveDTO ticketReserveDTO = (TicketReserveDTO) o;
        if(ticketReserveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketReserveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketReserveDTO{" +
            "id=" + getId() +
            ", codePaiement='" + getCodePaiement() + "'" +
            ", numeroPlace='" + getNumeroPlace() + "'" +
            ", prix=" + getPrix() +
            ", statutTicket='" + isStatutTicket() + "'" +
            ", typePlace='" + getTypePlace() + "'" +
            ", codeSup='" + getCodeSup() + "'" +
            "}";
    }
}
