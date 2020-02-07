package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.FilmService;
import com.itgstore.cineclub.domain.Film;
import com.itgstore.cineclub.repository.FilmRepository;
import com.itgstore.cineclub.service.dto.FilmDTO;
import com.itgstore.cineclub.service.mapper.FilmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Film.
 */
@Service
@Transactional
public class FilmServiceImpl implements FilmService {

    private final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);

    private final FilmRepository filmRepository;

    private final FilmMapper filmMapper;

    public FilmServiceImpl(FilmRepository filmRepository, FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
    }

    /**
     * Save a film.
     *
     * @param filmDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FilmDTO save(FilmDTO filmDTO) {
        log.debug("Request to save Film : {}", filmDTO);
        Film film = filmMapper.toEntity(filmDTO);
        film = filmRepository.save(film);
        return filmMapper.toDto(film);
    }

    /**
     * Get all the films.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Films");
        return filmRepository.findAll(pageable)
            .map(filmMapper::toDto);
    }

    /**
     * Get one film by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FilmDTO findOne(Long id) {
        log.debug("Request to get Film : {}", id);
        Film film = filmRepository.findOne(id);
        return filmMapper.toDto(film);
    }

    /**
     * Delete the film by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Film : {}", id);
        filmRepository.delete(id);
    }
}
