package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.TicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ticket and its DTO TicketDTO.
 */
@Mapper(componentModel = "spring", uses = {PrixTicketMapper.class, ProjectionMapper.class, ReservationMapper.class})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {

    @Mapping(source = "prixTicket.id", target = "prixTicketId")
    @Mapping(source = "projection.id", target = "projectionId")
    @Mapping(source = "reservation.id", target = "reservationId")
    TicketDTO toDto(Ticket ticket);

    @Mapping(source = "prixTicketId", target = "prixTicket")
    @Mapping(source = "projectionId", target = "projection")
    @Mapping(source = "reservationId", target = "reservation")
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
