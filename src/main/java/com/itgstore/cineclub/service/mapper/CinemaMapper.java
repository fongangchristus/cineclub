package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.CinemaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cinema and its DTO CinemaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CinemaMapper extends EntityMapper<CinemaDTO, Cinema> {



    default Cinema fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cinema cinema = new Cinema();
        cinema.setId(id);
        return cinema;
    }
}
