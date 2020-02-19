package com.itgstore.cineclub.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Seance entity.
 */
public class SeanceDTO implements Serializable {

    private Long id;

    private String heureDebut;

    private String heureFin;

    private String description;

    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeanceDTO seanceDTO = (SeanceDTO) o;
        if(seanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeanceDTO{" +
            "id=" + getId() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
