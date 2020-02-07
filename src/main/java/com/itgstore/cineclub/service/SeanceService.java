package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.SeanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Seance.
 */
public interface SeanceService {

    /**
     * Save a seance.
     *
     * @param seanceDTO the entity to save
     * @return the persisted entity
     */
    SeanceDTO save(SeanceDTO seanceDTO);

    /**
     * Get all the seances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SeanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" seance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SeanceDTO findOne(Long id);

    /**
     * Delete the "id" seance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
