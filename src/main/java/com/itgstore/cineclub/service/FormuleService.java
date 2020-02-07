package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.FormuleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Formule.
 */
public interface FormuleService {

    /**
     * Save a formule.
     *
     * @param formuleDTO the entity to save
     * @return the persisted entity
     */
    FormuleDTO save(FormuleDTO formuleDTO);

    /**
     * Get all the formules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FormuleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" formule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FormuleDTO findOne(Long id);

    /**
     * Delete the "id" formule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
