package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.AdresseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Adresse.
 */
public interface AdresseService {

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save
     * @return the persisted entity
     */
    AdresseDTO save(AdresseDTO adresseDTO);

    /**
     * Get all the adresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdresseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adresse.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdresseDTO findOne(Long id);

    /**
     * Delete the "id" adresse.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
