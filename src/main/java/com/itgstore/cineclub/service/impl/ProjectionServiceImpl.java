package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.ProjectionService;
import com.itgstore.cineclub.domain.Projection;
import com.itgstore.cineclub.repository.ProjectionRepository;
import com.itgstore.cineclub.service.dto.ProjectionDTO;
import com.itgstore.cineclub.service.mapper.ProjectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Projection.
 */
@Service
@Transactional
public class ProjectionServiceImpl implements ProjectionService {

    private final Logger log = LoggerFactory.getLogger(ProjectionServiceImpl.class);

    private final ProjectionRepository projectionRepository;

    private final ProjectionMapper projectionMapper;

    public ProjectionServiceImpl(ProjectionRepository projectionRepository, ProjectionMapper projectionMapper) {
        this.projectionRepository = projectionRepository;
        this.projectionMapper = projectionMapper;
    }

    /**
     * Save a projection.
     *
     * @param projectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectionDTO save(ProjectionDTO projectionDTO) {
        log.debug("Request to save Projection : {}", projectionDTO);
        Projection projection = projectionMapper.toEntity(projectionDTO);
        projection = projectionRepository.save(projection);
        return projectionMapper.toDto(projection);
    }

    /**
     * Get all the projections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projections");
        return projectionRepository.findAll(pageable)
            .map(projectionMapper::toDto);
    }

    /**
     * Get one projection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectionDTO findOne(Long id) {
        log.debug("Request to get Projection : {}", id);
        Projection projection = projectionRepository.findOneWithEagerRelationships(id);
        return projectionMapper.toDto(projection);
    }

    /**
     * Delete the projection by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Projection : {}", id);
        projectionRepository.delete(id);
    }
}
