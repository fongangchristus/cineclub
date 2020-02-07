package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Cinema.
 */
@Entity
@Table(name = "cinema")
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libele", nullable = false)
    private String libele;

    @Column(name = "description")
    private String description;

    @Column(name = "proprietaire")
    private String proprietaire;

    @Column(name = "lien_facebook")
    private String lienFacebook;

    @Column(name = "liens_wathsapp")
    private String liensWathsapp;

    @Column(name = "lien_youtube")
    private String lienYoutube;

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

    public Cinema libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getDescription() {
        return description;
    }

    public Cinema description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public Cinema proprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
        return this;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getLienFacebook() {
        return lienFacebook;
    }

    public Cinema lienFacebook(String lienFacebook) {
        this.lienFacebook = lienFacebook;
        return this;
    }

    public void setLienFacebook(String lienFacebook) {
        this.lienFacebook = lienFacebook;
    }

    public String getLiensWathsapp() {
        return liensWathsapp;
    }

    public Cinema liensWathsapp(String liensWathsapp) {
        this.liensWathsapp = liensWathsapp;
        return this;
    }

    public void setLiensWathsapp(String liensWathsapp) {
        this.liensWathsapp = liensWathsapp;
    }

    public String getLienYoutube() {
        return lienYoutube;
    }

    public Cinema lienYoutube(String lienYoutube) {
        this.lienYoutube = lienYoutube;
        return this;
    }

    public void setLienYoutube(String lienYoutube) {
        this.lienYoutube = lienYoutube;
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
        Cinema cinema = (Cinema) o;
        if (cinema.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cinema.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cinema{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", description='" + getDescription() + "'" +
            ", proprietaire='" + getProprietaire() + "'" +
            ", lienFacebook='" + getLienFacebook() + "'" +
            ", liensWathsapp='" + getLiensWathsapp() + "'" +
            ", lienYoutube='" + getLienYoutube() + "'" +
            "}";
    }
}
