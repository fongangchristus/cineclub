package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Film.
 */
@Entity
@Table(name = "film")
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titre", nullable = false)
    private String titre;

    @NotNull
    @Column(name = "path_couverture", nullable = false)
    private String pathCouverture;

    @Column(name = "bande_annonce")
    private String bandeAnnonce;

    @Lob
    @Column(name = "couverture")
    private byte[] couverture;

    @Column(name = "couverture_content_type")
    private String couvertureContentType;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "resume")
    private String resume;

    @NotNull
    @Column(name = "duree", nullable = false)
    private String duree;

    @NotNull
    @Column(name = "realisateur", nullable = false)
    private String realisateur;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @ManyToOne
    private CategorieFilm categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Film titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPathCouverture() {
        return pathCouverture;
    }

    public Film pathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
        return this;
    }

    public void setPathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
    }

    public String getBandeAnnonce() {
        return bandeAnnonce;
    }

    public Film bandeAnnonce(String bandeAnnonce) {
        this.bandeAnnonce = bandeAnnonce;
        return this;
    }

    public void setBandeAnnonce(String bandeAnnonce) {
        this.bandeAnnonce = bandeAnnonce;
    }

    public byte[] getCouverture() {
        return couverture;
    }

    public Film couverture(byte[] couverture) {
        this.couverture = couverture;
        return this;
    }

    public void setCouverture(byte[] couverture) {
        this.couverture = couverture;
    }

    public String getCouvertureContentType() {
        return couvertureContentType;
    }

    public Film couvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
        return this;
    }

    public void setCouvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
    }

    public String getDescription() {
        return description;
    }

    public Film description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResume() {
        return resume;
    }

    public Film resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getDuree() {
        return duree;
    }

    public Film duree(String duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public Film realisateur(String realisateur) {
        this.realisateur = realisateur;
        return this;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public Film dateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
        return this;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public CategorieFilm getCategorie() {
        return categorie;
    }

    public Film categorie(CategorieFilm categorieFilm) {
        this.categorie = categorieFilm;
        return this;
    }

    public void setCategorie(CategorieFilm categorieFilm) {
        this.categorie = categorieFilm;
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
        Film film = (Film) o;
        if (film.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), film.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Film{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", bandeAnnonce='" + getBandeAnnonce() + "'" +
            ", couverture='" + getCouverture() + "'" +
            ", couvertureContentType='" + getCouvertureContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", resume='" + getResume() + "'" +
            ", duree='" + getDuree() + "'" +
            ", realisateur='" + getRealisateur() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            "}";
    }
}
