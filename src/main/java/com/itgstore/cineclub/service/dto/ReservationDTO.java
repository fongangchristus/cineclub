package com.itgstore.cineclub.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    private String nomClient;

    @NotNull
    private LocalDate dateReservation;

    private Integer quantite;

    @NotNull
    private Double prixToTale;

    private Boolean statutReservation;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixToTale() {
        return prixToTale;
    }

    public void setPrixToTale(Double prixToTale) {
        this.prixToTale = prixToTale;
    }

    public Boolean isStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(Boolean statutReservation) {
        this.statutReservation = statutReservation;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if(reservationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", nomClient='" + getNomClient() + "'" +
            ", dateReservation='" + getDateReservation() + "'" +
            ", quantite=" + getQuantite() +
            ", prixToTale=" + getPrixToTale() +
            ", statutReservation='" + isStatutReservation() + "'" +
            "}";
    }
}
