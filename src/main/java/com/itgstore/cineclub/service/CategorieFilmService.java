package com.itgstore.cineclub.service;

import com.itgstore.cineclub.service.dto.CategorieFilmDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CategorieFilm.
 */
public interface CategorieFilmService {

    /**
     * Save a categorieFilm.
     *
     * @param categorieFilmDTO the entity to save
     * @return the persisted entity
     */
    CategorieFilmDTO save(CategorieFilmDTO categorieFilmDTO);

    /**
     * Get all the categorieFilms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CategorieFilmDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieFilm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategorieFilmDTO findOne(Long id);

    /**
     * Delete the "id" categorieFilm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
