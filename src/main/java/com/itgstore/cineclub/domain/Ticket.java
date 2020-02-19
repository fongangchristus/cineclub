package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.itgstore.cineclub.domain.enumeration.TypePlace;

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
    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "statut_disponibilite")
    private Boolean statutDisponibilite;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_place")
    private TypePlace typePlace;

    @Column(name = "nombre_ticket_total")
    private Double nombreTicketTotal;

    @Column(name = "nombre_ticket_restant")
    private Double nombreTicketRestant;

    @ManyToOne
    private Projection projection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isStatutDisponibilite() {
        return statutDisponibilite;
    }

    public Ticket statutDisponibilite(Boolean statutDisponibilite) {
        this.statutDisponibilite = statutDisponibilite;
        return this;
    }

    public void setStatutDisponibilite(Boolean statutDisponibilite) {
        this.statutDisponibilite = statutDisponibilite;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public Ticket typePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
        return this;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public Double getNombreTicketTotal() {
        return nombreTicketTotal;
    }

    public Ticket nombreTicketTotal(Double nombreTicketTotal) {
        this.nombreTicketTotal = nombreTicketTotal;
        return this;
    }

    public void setNombreTicketTotal(Double nombreTicketTotal) {
        this.nombreTicketTotal = nombreTicketTotal;
    }

    public Double getNombreTicketRestant() {
        return nombreTicketRestant;
    }

    public Ticket nombreTicketRestant(Double nombreTicketRestant) {
        this.nombreTicketRestant = nombreTicketRestant;
        return this;
    }

    public void setNombreTicketRestant(Double nombreTicketRestant) {
        this.nombreTicketRestant = nombreTicketRestant;
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
            ", prix=" + getPrix() +
            ", statutDisponibilite='" + isStatutDisponibilite() + "'" +
            ", typePlace='" + getTypePlace() + "'" +
            ", nombreTicketTotal=" + getNombreTicketTotal() +
            ", nombreTicketRestant=" + getNombreTicketRestant() +
            "}";
    }
}
