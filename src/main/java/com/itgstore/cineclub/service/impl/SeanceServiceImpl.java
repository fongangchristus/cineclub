package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.SeanceService;
import com.itgstore.cineclub.domain.Seance;
import com.itgstore.cineclub.repository.SeanceRepository;
import com.itgstore.cineclub.service.dto.SeanceDTO;
import com.itgstore.cineclub.service.mapper.SeanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Seance.
 */
@Service
@Transactional
public class SeanceServiceImpl implements SeanceService {

    private final Logger log = LoggerFactory.getLogger(SeanceServiceImpl.class);

    private final SeanceRepository seanceRepository;

    private final SeanceMapper seanceMapper;

    public SeanceServiceImpl(SeanceRepository seanceRepository, SeanceMapper seanceMapper) {
        this.seanceRepository = seanceRepository;
        this.seanceMapper = seanceMapper;
    }

    /**
     * Save a seance.
     *
     * @param seanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeanceDTO save(SeanceDTO seanceDTO) {
        log.debug("Request to save Seance : {}", seanceDTO);
        Seance seance = seanceMapper.toEntity(seanceDTO);
        seance = seanceRepository.save(seance);
        return seanceMapper.toDto(seance);
    }

    /**
     * Get all the seances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SeanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seances");
        return seanceRepository.findAll(pageable)
            .map(seanceMapper::toDto);
    }

    /**
     * Get one seance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SeanceDTO findOne(Long id) {
        log.debug("Request to get Seance : {}", id);
        Seance seance = seanceRepository.findOne(id);
        return seanceMapper.toDto(seance);
    }

    /**
     * Delete the seance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seance : {}", id);
        seanceRepository.delete(id);
    }
}
