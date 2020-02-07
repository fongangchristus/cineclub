package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.CinemaService;
import com.itgstore.cineclub.domain.Cinema;
import com.itgstore.cineclub.repository.CinemaRepository;
import com.itgstore.cineclub.service.dto.CinemaDTO;
import com.itgstore.cineclub.service.mapper.CinemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cinema.
 */
@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    private final Logger log = LoggerFactory.getLogger(CinemaServiceImpl.class);

    private final CinemaRepository cinemaRepository;

    private final CinemaMapper cinemaMapper;

    public CinemaServiceImpl(CinemaRepository cinemaRepository, CinemaMapper cinemaMapper) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaMapper = cinemaMapper;
    }

    /**
     * Save a cinema.
     *
     * @param cinemaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CinemaDTO save(CinemaDTO cinemaDTO) {
        log.debug("Request to save Cinema : {}", cinemaDTO);
        Cinema cinema = cinemaMapper.toEntity(cinemaDTO);
        cinema = cinemaRepository.save(cinema);
        return cinemaMapper.toDto(cinema);
    }

    /**
     * Get all the cinemas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CinemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cinemas");
        return cinemaRepository.findAll(pageable)
            .map(cinemaMapper::toDto);
    }

    /**
     * Get one cinema by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CinemaDTO findOne(Long id) {
        log.debug("Request to get Cinema : {}", id);
        Cinema cinema = cinemaRepository.findOne(id);
        return cinemaMapper.toDto(cinema);
    }

    /**
     * Delete the cinema by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cinema : {}", id);
        cinemaRepository.delete(id);
    }
}
