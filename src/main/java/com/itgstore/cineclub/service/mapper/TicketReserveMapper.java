package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.TicketReserveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TicketReserve and its DTO TicketReserveDTO.
 */
@Mapper(componentModel = "spring", uses = {ReservationMapper.class})
public interface TicketReserveMapper extends EntityMapper<TicketReserveDTO, TicketReserve> {

    @Mapping(source = "reservation.id", target = "reservationId")
    TicketReserveDTO toDto(TicketReserve ticketReserve);

    @Mapping(source = "reservationId", target = "reservation")
    TicketReserve toEntity(TicketReserveDTO ticketReserveDTO);

    default TicketReserve fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketReserve ticketReserve = new TicketReserve();
        ticketReserve.setId(id);
        return ticketReserve;
    }
}
