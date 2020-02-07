package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Salle.
 */
@Entity
@Table(name = "salle")
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libele", nullable = false)
    private String libele;

    @Column(name = "path_couverture")
    private String pathCouverture;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "nombre_place_totale")
    private Integer nombrePlaceTotale;

    @Column(name = "nombre_place_balcon")
    private Integer nombrePlaceBalcon;

    @Column(name = "nombre_place_clasique")
    private Integer nombrePlaceClasique;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresse;

    @ManyToOne
    private Ville ville;

    @ManyToOne
    private Cinema cinema;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public Salle libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getPathCouverture() {
        return pathCouverture;
    }

    public Salle pathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
        return this;
    }

    public void setPathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
    }

    public String getCode() {
        return code;
    }

    public Salle code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Salle description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNombrePlaceTotale() {
        return nombrePlaceTotale;
    }

    public Salle nombrePlaceTotale(Integer nombrePlaceTotale) {
        this.nombrePlaceTotale = nombrePlaceTotale;
        return this;
    }

    public void setNombrePlaceTotale(Integer nombrePlaceTotale) {
        this.nombrePlaceTotale = nombrePlaceTotale;
    }

    public Integer getNombrePlaceBalcon() {
        return nombrePlaceBalcon;
    }

    public Salle nombrePlaceBalcon(Integer nombrePlaceBalcon) {
        this.nombrePlaceBalcon = nombrePlaceBalcon;
        return this;
    }

    public void setNombrePlaceBalcon(Integer nombrePlaceBalcon) {
        this.nombrePlaceBalcon = nombrePlaceBalcon;
    }

    public Integer getNombrePlaceClasique() {
        return nombrePlaceClasique;
    }

    public Salle nombrePlaceClasique(Integer nombrePlaceClasique) {
        this.nombrePlaceClasique = nombrePlaceClasique;
        return this;
    }

    public void setNombrePlaceClasique(Integer nombrePlaceClasique) {
        this.nombrePlaceClasique = nombrePlaceClasique;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Salle adresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Ville getVille() {
        return ville;
    }

    public Salle ville(Ville ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Salle cinema(Cinema cinema) {
        this.cinema = cinema;
        return this;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
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
        Salle salle = (Salle) o;
        if (salle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Salle{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", nombrePlaceTotale=" + getNombrePlaceTotale() +
            ", nombrePlaceBalcon=" + getNombrePlaceBalcon() +
            ", nombrePlaceClasique=" + getNombrePlaceClasique() +
            "}";
    }
}
