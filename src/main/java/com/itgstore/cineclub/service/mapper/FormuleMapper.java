package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.FormuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Formule and its DTO FormuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormuleMapper extends EntityMapper<FormuleDTO, Formule> {



    default Formule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Formule formule = new Formule();
        formule.setId(id);
        return formule;
    }
}
