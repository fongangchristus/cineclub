package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.ProjectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Projection.
 */
public interface ProjectionService {

    /**
     * Save a projection.
     *
     * @param projectionDTO the entity to save
     * @return the persisted entity
     */
    ProjectionDTO save(ProjectionDTO projectionDTO);

    /**
     * Get all the projections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProjectionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projection.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProjectionDTO findOne(Long id);

    /**
     * Delete the "id" projection.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
