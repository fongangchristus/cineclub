package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.SalleService;
import com.itgstore.cineclub.domain.Salle;
import com.itgstore.cineclub.repository.SalleRepository;
import com.itgstore.cineclub.service.dto.SalleDTO;
import com.itgstore.cineclub.service.mapper.SalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Salle.
 */
@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private final Logger log = LoggerFactory.getLogger(SalleServiceImpl.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleServiceImpl(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    /**
     * Save a salle.
     *
     * @param salleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        log.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    /**
     * Get all the salles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salles");
        return salleRepository.findAll(pageable)
            .map(salleMapper::toDto);
    }

    /**
     * Get one salle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SalleDTO findOne(Long id) {
        log.debug("Request to get Salle : {}", id);
        Salle salle = salleRepository.findOne(id);
        return salleMapper.toDto(salle);
    }

    /**
     * Delete the salle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Salle : {}", id);
        salleRepository.delete(id);
    }
}
