package com.itgstore.cineclub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.itgstore.cineclub.domain.enumeration.Jour;

import com.itgstore.cineclub.domain.enumeration.TypeProjection;

/**
 * A Projection.
 */
@Entity
@Table(name = "projection")
public class Projection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "duree")
    private String duree;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "jour")
    private Jour jour;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_projection")
    private TypeProjection typeProjection;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "projection")
    @JsonIgnore
    private Set<PrixTicket> listPrixTickets = new HashSet<>();

    @ManyToOne
    private Film film;

    @ManyToOne
    private Seance plageHoraire;

    @ManyToOne
    private Salle salle;

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

    public Projection libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getDuree() {
        return duree;
    }

    public Projection duree(String duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getCode() {
        return code;
    }

    public Projection code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Jour getJour() {
        return jour;
    }

    public Projection jour(Jour jour) {
        this.jour = jour;
        return this;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public TypeProjection getTypeProjection() {
        return typeProjection;
    }

    public Projection typeProjection(TypeProjection typeProjection) {
        this.typeProjection = typeProjection;
        return this;
    }

    public void setTypeProjection(TypeProjection typeProjection) {
        this.typeProjection = typeProjection;
    }

    public String getDescription() {
        return description;
    }

    public Projection description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PrixTicket> getListPrixTickets() {
        return listPrixTickets;
    }

    public Projection listPrixTickets(Set<PrixTicket> prixTickets) {
        this.listPrixTickets = prixTickets;
        return this;
    }

    public Projection addListPrixTicket(PrixTicket prixTicket) {
        this.listPrixTickets.add(prixTicket);
        prixTicket.setProjection(this);
        return this;
    }

    public Projection removeListPrixTicket(PrixTicket prixTicket) {
        this.listPrixTickets.remove(prixTicket);
        prixTicket.setProjection(null);
        return this;
    }

    public void setListPrixTickets(Set<PrixTicket> prixTickets) {
        this.listPrixTickets = prixTickets;
    }

    public Film getFilm() {
        return film;
    }

    public Projection film(Film film) {
        this.film = film;
        return this;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Seance getPlageHoraire() {
        return plageHoraire;
    }

    public Projection plageHoraire(Seance seance) {
        this.plageHoraire = seance;
        return this;
    }

    public void setPlageHoraire(Seance seance) {
        this.plageHoraire = seance;
    }

    public Salle getSalle() {
        return salle;
    }

    public Projection salle(Salle salle) {
        this.salle = salle;
        return this;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
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
        Projection projection = (Projection) o;
        if (projection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projection{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", duree='" + getDuree() + "'" +
            ", code='" + getCode() + "'" +
            ", jour='" + getJour() + "'" +
            ", typeProjection='" + getTypeProjection() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
