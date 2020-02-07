package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.CinemaService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.CinemaDTO;
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
 * REST controller for managing Cinema.
 */
@RestController
@RequestMapping("/api")
public class CinemaResource {

    private final Logger log = LoggerFactory.getLogger(CinemaResource.class);

    private static final String ENTITY_NAME = "cinema";

    private final CinemaService cinemaService;

    public CinemaResource(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    /**
     * POST  /cinemas : Create a new cinema.
     *
     * @param cinemaDTO the cinemaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cinemaDTO, or with status 400 (Bad Request) if the cinema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cinemas")
    @Timed
    public ResponseEntity<CinemaDTO> createCinema(@Valid @RequestBody CinemaDTO cinemaDTO) throws URISyntaxException {
        log.debug("REST request to save Cinema : {}", cinemaDTO);
        if (cinemaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cinema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CinemaDTO result = cinemaService.save(cinemaDTO);
        return ResponseEntity.created(new URI("/api/cinemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cinemas : Updates an existing cinema.
     *
     * @param cinemaDTO the cinemaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cinemaDTO,
     * or with status 400 (Bad Request) if the cinemaDTO is not valid,
     * or with status 500 (Internal Server Error) if the cinemaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cinemas")
    @Timed
    public ResponseEntity<CinemaDTO> updateCinema(@Valid @RequestBody CinemaDTO cinemaDTO) throws URISyntaxException {
        log.debug("REST request to update Cinema : {}", cinemaDTO);
        if (cinemaDTO.getId() == null) {
            return createCinema(cinemaDTO);
        }
        CinemaDTO result = cinemaService.save(cinemaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cinemaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cinemas : get all the cinemas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cinemas in body
     */
    @GetMapping("/cinemas")
    @Timed
    public ResponseEntity<List<CinemaDTO>> getAllCinemas(Pageable pageable) {
        log.debug("REST request to get a page of Cinemas");
        Page<CinemaDTO> page = cinemaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cinemas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cinemas/:id : get the "id" cinema.
     *
     * @param id the id of the cinemaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cinemaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cinemas/{id}")
    @Timed
    public ResponseEntity<CinemaDTO> getCinema(@PathVariable Long id) {
        log.debug("REST request to get Cinema : {}", id);
        CinemaDTO cinemaDTO = cinemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cinemaDTO));
    }

    /**
     * DELETE  /cinemas/:id : delete the "id" cinema.
     *
     * @param id the id of the cinemaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cinemas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        log.debug("REST request to delete Cinema : {}", id);
        cinemaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
