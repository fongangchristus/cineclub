package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CategorieFilm.
 */
@Entity
@Table(name = "categorie_film")
public class CategorieFilm implements Serializable {

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

    @Lob
    @Column(name = "couverture")
    private byte[] couverture;

    @Column(name = "couverture_content_type")
    private String couvertureContentType;

    @Column(name = "description")
    private String description;

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

    public CategorieFilm libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getPathCouverture() {
        return pathCouverture;
    }

    public CategorieFilm pathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
        return this;
    }

    public void setPathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
    }

    public byte[] getCouverture() {
        return couverture;
    }

    public CategorieFilm couverture(byte[] couverture) {
        this.couverture = couverture;
        return this;
    }

    public void setCouverture(byte[] couverture) {
        this.couverture = couverture;
    }

    public String getCouvertureContentType() {
        return couvertureContentType;
    }

    public CategorieFilm couvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
        return this;
    }

    public void setCouvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
    }

    public String getDescription() {
        return description;
    }

    public CategorieFilm description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        CategorieFilm categorieFilm = (CategorieFilm) o;
        if (categorieFilm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorieFilm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategorieFilm{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", couverture='" + getCouverture() + "'" +
            ", couvertureContentType='" + getCouvertureContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
