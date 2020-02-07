package com.itgstore.cineclub.service.dto;


import javax.validation.constraints.*;

import com.itgstore.cineclub.domain.Authority;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.List;

/**
 * A DTO for the PgwRoleApp entity.
 */
public class RoleAppDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String description;

    private Set<Authority> authorities;

    private Set<String> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleAppDTO pgwRoleAppDTO = (RoleAppDTO) o;
        if(pgwRoleAppDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgwRoleAppDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgwRoleAppDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
