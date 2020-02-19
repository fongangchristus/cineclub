package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.TicketReserveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TicketReserve.
 */
public interface TicketReserveService {

    /**
     * Save a ticketReserve.
     *
     * @param ticketReserveDTO the entity to save
     * @return the persisted entity
     */
    TicketReserveDTO save(TicketReserveDTO ticketReserveDTO);

    /**
     * Get all the ticketReserves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TicketReserveDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketReserve.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TicketReserveDTO findOne(Long id);

    /**
     * Delete the "id" ticketReserve.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
