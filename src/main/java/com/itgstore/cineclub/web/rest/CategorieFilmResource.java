package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.CategorieFilmService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.CategorieFilmDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CategorieFilm.
 */
@RestController
@RequestMapping("/api")
public class CategorieFilmResource {

    private final Logger log = LoggerFactory.getLogger(CategorieFilmResource.class);

    private static final String ENTITY_NAME = "categorieFilm";

    private final CategorieFilmService categorieFilmService;

    public CategorieFilmResource(CategorieFilmService categorieFilmService) {
        this.categorieFilmService = categorieFilmService;
    }

    /**
     * POST  /categorie-films : Create a new categorieFilm.
     *
     * @param categorieFilmDTO the categorieFilmDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorieFilmDTO, or with status 400 (Bad Request) if the categorieFilm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorie-films")
    @Timed
    public ResponseEntity<CategorieFilmDTO> createCategorieFilm(@Valid @RequestBody CategorieFilmDTO categorieFilmDTO) throws URISyntaxException {
        log.debug("REST request to save CategorieFilm : {}", categorieFilmDTO);
        if (categorieFilmDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieFilm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieFilmDTO result = categorieFilmService.save(categorieFilmDTO);
        return ResponseEntity.created(new URI("/api/categorie-films/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorie-films : Updates an existing categorieFilm.
     *
     * @param categorieFilmDTO the categorieFilmDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorieFilmDTO,
     * or with status 400 (Bad Request) if the categorieFilmDTO is not valid,
     * or with status 500 (Internal Server Error) if the categorieFilmDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorie-films")
    @Timed
    public ResponseEntity<CategorieFilmDTO> updateCategorieFilm(@Valid @RequestBody CategorieFilmDTO categorieFilmDTO) throws URISyntaxException {
        log.debug("REST request to update CategorieFilm : {}", categorieFilmDTO);
        if (categorieFilmDTO.getId() == null) {
            return createCategorieFilm(categorieFilmDTO);
        }
        CategorieFilmDTO result = categorieFilmService.save(categorieFilmDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorieFilmDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorie-films : get all the categorieFilms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categorieFilms in body
     */
    @GetMapping("/categorie-films")
    @Timed
    public ResponseEntity<List<CategorieFilmDTO>> getAllCategorieFilms(Pageable pageable) {
        log.debug("REST request to get a page of CategorieFilms");
        Page<CategorieFilmDTO> page = categorieFilmService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorie-films");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categorie-films/:id : get the "id" categorieFilm.
     *
     * @param id the id of the categorieFilmDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorieFilmDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorie-films/{id}")
    @Timed
    public ResponseEntity<CategorieFilmDTO> getCategorieFilm(@PathVariable Long id) {
        log.debug("REST request to get CategorieFilm : {}", id);
        CategorieFilmDTO categorieFilmDTO = categorieFilmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categorieFilmDTO));
    }

    /**
     * DELETE  /categorie-films/:id : delete the "id" categorieFilm.
     *
     * @param id the id of the categorieFilmDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorie-films/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorieFilm(@PathVariable Long id) {
        log.debug("REST request to delete CategorieFilm : {}", id);
        categorieFilmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
