package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.PaysDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pays and its DTO PaysDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaysMapper extends EntityMapper<PaysDTO, Pays> {



    default Pays fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pays pays = new Pays();
        pays.setId(id);
        return pays;
    }
}
