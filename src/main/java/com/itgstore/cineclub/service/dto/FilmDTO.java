package com.itgstore.cineclub.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Film entity.
 */
public class FilmDTO implements Serializable {

    private Long id;

    @NotNull
    private String titre;

    @NotNull
    private String pathCouverture;

    private String bandeAnnonce;

    @Lob
    private byte[] couverture;
    private String couvertureContentType;

    @NotNull
    private String description;

    private String resume;

    @NotNull
    private String duree;

    @NotNull
    private String realisateur;

    private LocalDate dateSortie;

    private Long categorieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPathCouverture() {
        return pathCouverture;
    }

    public void setPathCouverture(String pathCouverture) {
        this.pathCouverture = pathCouverture;
    }

    public String getBandeAnnonce() {
        return bandeAnnonce;
    }

    public void setBandeAnnonce(String bandeAnnonce) {
        this.bandeAnnonce = bandeAnnonce;
    }

    public byte[] getCouverture() {
        return couverture;
    }

    public void setCouverture(byte[] couverture) {
        this.couverture = couverture;
    }

    public String getCouvertureContentType() {
        return couvertureContentType;
    }

    public void setCouvertureContentType(String couvertureContentType) {
        this.couvertureContentType = couvertureContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieFilmId) {
        this.categorieId = categorieFilmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilmDTO filmDTO = (FilmDTO) o;
        if(filmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", pathCouverture='" + getPathCouverture() + "'" +
            ", bandeAnnonce='" + getBandeAnnonce() + "'" +
            ", couverture='" + getCouverture() + "'" +
            ", description='" + getDescription() + "'" +
            ", resume='" + getResume() + "'" +
            ", duree='" + getDuree() + "'" +
            ", realisateur='" + getRealisateur() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            "}";
    }
}
