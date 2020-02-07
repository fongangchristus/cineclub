package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.CategorieFilmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CategorieFilm and its DTO CategorieFilmDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieFilmMapper extends EntityMapper<CategorieFilmDTO, CategorieFilm> {



    default CategorieFilm fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategorieFilm categorieFilm = new CategorieFilm();
        categorieFilm.setId(id);
        return categorieFilm;
    }
}
