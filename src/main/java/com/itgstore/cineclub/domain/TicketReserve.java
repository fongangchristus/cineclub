package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.itgstore.cineclub.domain.enumeration.TypePlace;

/**
 * A TicketReserve.
 */
@Entity
@Table(name = "ticket")
public class TicketReserve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_paiement", nullable = false)
    private String codePaiement;

    @NotNull
    @Column(name = "numero_place", nullable = false)
    private String numeroPlace;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "statut_ticket")
    private Boolean statutTicket;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_place")
    private TypePlace typePlace;

    @Column(name = "code_sup")
    private String codeSup;

    @ManyToOne
    private Reservation reservation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePaiement() {
        return codePaiement;
    }

    public TicketReserve codePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
        return this;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public TicketReserve numeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
        return this;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public Double getPrix() {
        return prix;
    }

    public TicketReserve prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean isStatutTicket() {
        return statutTicket;
    }

    public TicketReserve statutTicket(Boolean statutTicket) {
        this.statutTicket = statutTicket;
        return this;
    }

    public void setStatutTicket(Boolean statutTicket) {
        this.statutTicket = statutTicket;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public TicketReserve typePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
        return this;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public String getCodeSup() {
        return codeSup;
    }

    public TicketReserve codeSup(String codeSup) {
        this.codeSup = codeSup;
        return this;
    }

    public void setCodeSup(String codeSup) {
        this.codeSup = codeSup;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public TicketReserve reservation(Reservation reservation) {
        this.reservation = reservation;
        return this;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketReserve ticketReserve = (TicketReserve) o;
        if (ticketReserve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketReserve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketReserve{" +
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
