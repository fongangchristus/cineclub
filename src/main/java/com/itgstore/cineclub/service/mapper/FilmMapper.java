package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.FilmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Film and its DTO FilmDTO.
 */
@Mapper(componentModel = "spring", uses = {CategorieFilmMapper.class})
public interface FilmMapper extends EntityMapper<FilmDTO, Film> {

    @Mapping(source = "categorie.id", target = "categorieId")
    FilmDTO toDto(Film film);

    @Mapping(source = "categorieId", target = "categorie")
    Film toEntity(FilmDTO filmDTO);

    default Film fromId(Long id) {
        if (id == null) {
            return null;
        }
        Film film = new Film();
        film.setId(id);
        return film;
    }
}
