package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.PrixTicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PrixTicket and its DTO PrixTicketDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectionMapper.class})
public interface PrixTicketMapper extends EntityMapper<PrixTicketDTO, PrixTicket> {

    @Mapping(source = "projection.id", target = "projectionId")
    PrixTicketDTO toDto(PrixTicket prixTicket);

    @Mapping(source = "projectionId", target = "projection")
    PrixTicket toEntity(PrixTicketDTO prixTicketDTO);

    default PrixTicket fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrixTicket prixTicket = new PrixTicket();
        prixTicket.setId(id);
        return prixTicket;
    }
}
