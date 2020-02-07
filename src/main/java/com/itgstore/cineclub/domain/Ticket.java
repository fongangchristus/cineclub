package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

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

    @ManyToOne
    private PrixTicket prixTicket;

    @ManyToOne
    private Projection projection;

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

    public Ticket codePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
        return this;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public Ticket numeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
        return this;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public Double getPrix() {
        return prix;
    }

    public Ticket prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean isStatutTicket() {
        return statutTicket;
    }

    public Ticket statutTicket(Boolean statutTicket) {
        this.statutTicket = statutTicket;
        return this;
    }

    public void setStatutTicket(Boolean statutTicket) {
        this.statutTicket = statutTicket;
    }

    public PrixTicket getPrixTicket() {
        return prixTicket;
    }

    public Ticket prixTicket(PrixTicket prixTicket) {
        this.prixTicket = prixTicket;
        return this;
    }

    public void setPrixTicket(PrixTicket prixTicket) {
        this.prixTicket = prixTicket;
    }

    public Projection getProjection() {
        return projection;
    }

    public Ticket projection(Projection projection) {
        this.projection = projection;
        return this;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Ticket reservation(Reservation reservation) {
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
        Ticket ticket = (Ticket) o;
        if (ticket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", codePaiement='" + getCodePaiement() + "'" +
            ", numeroPlace='" + getNumeroPlace() + "'" +
            ", prix=" + getPrix() +
            ", statutTicket='" + isStatutTicket() + "'" +
            "}";
    }
}
