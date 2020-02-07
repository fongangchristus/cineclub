package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.AdresseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Adresse and its DTO AdresseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdresseMapper extends EntityMapper<AdresseDTO, Adresse> {



    default Adresse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setId(id);
        return adresse;
    }
}
