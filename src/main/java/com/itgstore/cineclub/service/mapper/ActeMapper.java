package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.ActeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Acte and its DTO ActeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActeMapper extends EntityMapper<ActeDTO, Acte> {



    default Acte fromId(Long id) {
        if (id == null) {
            return null;
        }
        Acte acte = new Acte();
        acte.setId(id);
        return acte;
    }
}
