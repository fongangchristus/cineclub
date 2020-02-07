package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.SalleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Salle.
 */
public interface SalleService {

    /**
     * Save a salle.
     *
     * @param salleDTO the entity to save
     * @return the persisted entity
     */
    SalleDTO save(SalleDTO salleDTO);

    /**
     * Get all the salles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SalleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" salle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SalleDTO findOne(Long id);

    /**
     * Delete the "id" salle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
