package com.itgstore.cineclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom_client")
    private String nomClient;

    @NotNull
    @Column(name = "date_reservation", nullable = false)
    private LocalDate dateReservation;

    @Column(name = "quantite")
    private Integer quantite;

    @NotNull
    @Column(name = "prix_to_tale", nullable = false)
    private Double prixToTale;

    @Column(name = "statut_reservation")
    private Boolean statutReservation;

    @OneToMany(mappedBy = "reservation")
    @JsonIgnore
    private Set<Ticket> listTickets = new HashSet<>();

    @ManyToOne
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClient() {
        return nomClient;
    }

    public Reservation nomClient(String nomClient) {
        this.nomClient = nomClient;
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public Reservation dateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Reservation quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixToTale() {
        return prixToTale;
    }

    public Reservation prixToTale(Double prixToTale) {
        this.prixToTale = prixToTale;
        return this;
    }

    public void setPrixToTale(Double prixToTale) {
        this.prixToTale = prixToTale;
    }

    public Boolean isStatutReservation() {
        return statutReservation;
    }

    public Reservation statutReservation(Boolean statutReservation) {
        this.statutReservation = statutReservation;
        return this;
    }

    public void setStatutReservation(Boolean statutReservation) {
        this.statutReservation = statutReservation;
    }

    public Set<Ticket> getListTickets() {
        return listTickets;
    }

    public Reservation listTickets(Set<Ticket> tickets) {
        this.listTickets = tickets;
        return this;
    }

    public Reservation addListTicket(Ticket ticket) {
        this.listTickets.add(ticket);
        ticket.setReservation(this);
        return this;
    }

    public Reservation removeListTicket(Ticket ticket) {
        this.listTickets.remove(ticket);
        ticket.setReservation(null);
        return this;
    }

    public void setListTickets(Set<Ticket> tickets) {
        this.listTickets = tickets;
    }

    public Client getClient() {
        return client;
    }

    public Reservation client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
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
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", nomClient='" + getNomClient() + "'" +
            ", dateReservation='" + getDateReservation() + "'" +
            ", quantite=" + getQuantite() +
            ", prixToTale=" + getPrixToTale() +
            ", statutReservation='" + isStatutReservation() + "'" +
            "}";
    }
}
