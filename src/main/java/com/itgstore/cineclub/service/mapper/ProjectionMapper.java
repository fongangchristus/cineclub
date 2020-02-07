package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.ProjectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Projection and its DTO ProjectionDTO.
 */
@Mapper(componentModel = "spring", uses = {FilmMapper.class, SeanceMapper.class, SalleMapper.class})
public interface ProjectionMapper extends EntityMapper<ProjectionDTO, Projection> {

    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "plageHoraire.id", target = "plageHoraireId")
    @Mapping(source = "salle.id", target = "salleId")
    ProjectionDTO toDto(Projection projection);

    @Mapping(target = "listPrixTickets", ignore = true)
    @Mapping(source = "filmId", target = "film")
    @Mapping(source = "plageHoraireId", target = "plageHoraire")
    @Mapping(source = "salleId", target = "salle")
    Projection toEntity(ProjectionDTO projectionDTO);

    default Projection fromId(Long id) {
        if (id == null) {
            return null;
        }
        Projection projection = new Projection();
        projection.setId(id);
        return projection;
    }
}
