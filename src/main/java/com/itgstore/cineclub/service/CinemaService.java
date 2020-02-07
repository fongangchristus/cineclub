package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.CinemaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cinema.
 */
public interface CinemaService {

    /**
     * Save a cinema.
     *
     * @param cinemaDTO the entity to save
     * @return the persisted entity
     */
    CinemaDTO save(CinemaDTO cinemaDTO);

    /**
     * Get all the cinemas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CinemaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cinema.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CinemaDTO findOne(Long id);

    /**
     * Delete the "id" cinema.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
