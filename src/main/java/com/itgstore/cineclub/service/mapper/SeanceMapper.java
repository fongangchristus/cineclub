package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.SeanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seance and its DTO SeanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeanceMapper extends EntityMapper<SeanceDTO, Seance> {


    @Mapping(target = "projections", ignore = true)
    Seance toEntity(SeanceDTO seanceDTO);

    default Seance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seance seance = new Seance();
        seance.setId(id);
        return seance;
    }
}
