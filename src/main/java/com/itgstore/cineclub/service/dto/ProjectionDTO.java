package com.itgstore.cineclub.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.itgstore.cineclub.domain.enumeration.Jour;
import com.itgstore.cineclub.domain.enumeration.TypeProjection;

/**
 * A DTO for the Projection entity.
 */
public class ProjectionDTO implements Serializable {

    private Long id;

    private String libele;

    private String duree;

    private String code;

    private Jour jour;

    private TypeProjection typeProjection;

    private String description;

    private Long filmId;

    private Set<SeanceDTO> listeSeances = new HashSet<>();

    private Long salleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public TypeProjection getTypeProjection() {
        return typeProjection;
    }

    public void setTypeProjection(TypeProjection typeProjection) {
        this.typeProjection = typeProjection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Set<SeanceDTO> getListeSeances() {
        return listeSeances;
    }

    public void setListeSeances(Set<SeanceDTO> seances) {
        this.listeSeances = seances;
    }

    public Long getSalleId() {
        return salleId;
    }

    public void setSalleId(Long salleId) {
        this.salleId = salleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectionDTO projectionDTO = (ProjectionDTO) o;
        if(projectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectionDTO{" +
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
