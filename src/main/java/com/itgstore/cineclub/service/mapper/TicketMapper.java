package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.TicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ticket and its DTO TicketDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectionMapper.class})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {

    @Mapping(source = "projection.id", target = "projectionId")
    TicketDTO toDto(Ticket ticket);

    @Mapping(source = "projectionId", target = "projection")
    Ticket toEntity(TicketDTO ticketDTO);

    default Ticket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
