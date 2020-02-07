package com.itgstore.cineclub.service;

import com.itgstore.cineclub.domain.Acte;
import com.itgstore.cineclub.repository.ActeRepository;
import com.itgstore.cineclub.service.dto.ActeDTO;
import com.itgstore.cineclub.service.mapper.ActeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Acte.
 */
@Service
@Transactional
public class ActeService {

    private final Logger log = LoggerFactory.getLogger(ActeService.class);

    private final ActeRepository acteRepository;

    private final ActeMapper acteMapper;

    public ActeService(ActeRepository acteRepository, ActeMapper acteMapper) {
        this.acteRepository = acteRepository;
        this.acteMapper = acteMapper;
    }

    /**
     * Save a acte.
     *
     * @param acteDTO the entity to save
     * @return the persisted entity
     */
    public ActeDTO save(ActeDTO acteDTO) {
        log.debug("Request to save Acte : {}", acteDTO);
        Acte acte = acteMapper.toEntity(acteDTO);
        acte = acteRepository.save(acte);
        return acteMapper.toDto(acte);
    }

    /**
     * Get all the actes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Actes");
        return acteRepository.findAll(pageable)
            .map(acteMapper::toDto);
    }

    /**
     * Get one acte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ActeDTO findOne(Long id) {
        log.debug("Request to get Acte : {}", id);
        Acte acte = acteRepository.findOne(id);
        return acteMapper.toDto(acte);
    }

    /**
     * Delete the acte by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Acte : {}", id);
        acteRepository.delete(id);
    }
}
