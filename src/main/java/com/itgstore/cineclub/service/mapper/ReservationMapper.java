package com.itgstore.cineclub.service.mapper;

import com.itgstore.cineclub.domain.*;
import com.itgstore.cineclub.service.dto.ReservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reservation and its DTO ReservationDTO.
 */
@Mapper(componentModel = "spring", uses = {TicketMapper.class, ClientMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "ticket.id", target = "ticketId")
    @Mapping(source = "client.id", target = "clientId")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(source = "ticketId", target = "ticket")
    @Mapping(target = "listTicketReserves", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Reservation toEntity(ReservationDTO reservationDTO);

    default Reservation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        return reservation;
    }
}
