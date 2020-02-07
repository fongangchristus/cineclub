package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.CategorieFilmService;
import com.itgstore.cineclub.domain.CategorieFilm;
import com.itgstore.cineclub.repository.CategorieFilmRepository;
import com.itgstore.cineclub.service.dto.CategorieFilmDTO;
import com.itgstore.cineclub.service.mapper.CategorieFilmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CategorieFilm.
 */
@Service
@Transactional
public class CategorieFilmServiceImpl implements CategorieFilmService {

    private final Logger log = LoggerFactory.getLogger(CategorieFilmServiceImpl.class);

    private final CategorieFilmRepository categorieFilmRepository;

    private final CategorieFilmMapper categorieFilmMapper;

    public CategorieFilmServiceImpl(CategorieFilmRepository categorieFilmRepository, CategorieFilmMapper categorieFilmMapper) {
        this.categorieFilmRepository = categorieFilmRepository;
        this.categorieFilmMapper = categorieFilmMapper;
    }

    /**
     * Save a categorieFilm.
     *
     * @param categorieFilmDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategorieFilmDTO save(CategorieFilmDTO categorieFilmDTO) {
        log.debug("Request to save CategorieFilm : {}", categorieFilmDTO);
        CategorieFilm categorieFilm = categorieFilmMapper.toEntity(categorieFilmDTO);
        categorieFilm = categorieFilmRepository.save(categorieFilm);
        return categorieFilmMapper.toDto(categorieFilm);
    }

    /**
     * Get all the categorieFilms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategorieFilmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorieFilms");
        return categorieFilmRepository.findAll(pageable)
            .map(categorieFilmMapper::toDto);
    }

    /**
     * Get one categorieFilm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategorieFilmDTO findOne(Long id) {
        log.debug("Request to get CategorieFilm : {}", id);
        CategorieFilm categorieFilm = categorieFilmRepository.findOne(id);
        return categorieFilmMapper.toDto(categorieFilm);
    }

    /**
     * Delete the categorieFilm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieFilm : {}", id);
        categorieFilmRepository.delete(id);
    }
}
