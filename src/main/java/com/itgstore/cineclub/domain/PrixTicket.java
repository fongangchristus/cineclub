package com.itgstore.cineclub.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.itgstore.cineclub.domain.enumeration.TypePlace;

/**
 * A PrixTicket.
 */
@Entity
@Table(name = "prix_ticket")
public class PrixTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_place")
    private TypePlace typePlace;

    @Column(name = "description")
    private String description;

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

    public PrixTicket prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getCode() {
        return code;
    }

    public PrixTicket code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypePlace getTypePlace() {
        return typePlace;
    }

    public PrixTicket typePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
        return this;
    }

    public void setTypePlace(TypePlace typePlace) {
        this.typePlace = typePlace;
    }

    public String getDescription() {
        return description;
    }

    public PrixTicket description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Projection getProjection() {
        return projection;
    }

    public PrixTicket projection(Projection projection) {
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
        PrixTicket prixTicket = (PrixTicket) o;
        if (prixTicket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prixTicket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrixTicket{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", code='" + getCode() + "'" +
            ", typePlace='" + getTypePlace() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
