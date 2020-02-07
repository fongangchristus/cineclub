package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.PrixTicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PrixTicket.
 */
public interface PrixTicketService {

    /**
     * Save a prixTicket.
     *
     * @param prixTicketDTO the entity to save
     * @return the persisted entity
     */
    PrixTicketDTO save(PrixTicketDTO prixTicketDTO);

    /**
     * Get all the prixTickets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PrixTicketDTO> findAll(Pageable pageable);

    /**
     * Get the "id" prixTicket.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PrixTicketDTO findOne(Long id);

    /**
     * Delete the "id" prixTicket.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
