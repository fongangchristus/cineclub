package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.SeanceService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.SeanceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Seance.
 */
@RestController
@RequestMapping("/api")
public class SeanceResource {

    private final Logger log = LoggerFactory.getLogger(SeanceResource.class);

    private static final String ENTITY_NAME = "seance";

    private final SeanceService seanceService;

    public SeanceResource(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    /**
     * POST  /seances : Create a new seance.
     *
     * @param seanceDTO the seanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seanceDTO, or with status 400 (Bad Request) if the seance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seances")
    @Timed
    public ResponseEntity<SeanceDTO> createSeance(@RequestBody SeanceDTO seanceDTO) throws URISyntaxException {
        log.debug("REST request to save Seance : {}", seanceDTO);
        if (seanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new seance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity.created(new URI("/api/seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seances : Updates an existing seance.
     *
     * @param seanceDTO the seanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seanceDTO,
     * or with status 400 (Bad Request) if the seanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the seanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seances")
    @Timed
    public ResponseEntity<SeanceDTO> updateSeance(@RequestBody SeanceDTO seanceDTO) throws URISyntaxException {
        log.debug("REST request to update Seance : {}", seanceDTO);
        if (seanceDTO.getId() == null) {
            return createSeance(seanceDTO);
        }
        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seances : get all the seances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seances in body
     */
    @GetMapping("/seances")
    @Timed
    public ResponseEntity<List<SeanceDTO>> getAllSeances(Pageable pageable) {
        log.debug("REST request to get a page of Seances");
        Page<SeanceDTO> page = seanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seances/:id : get the "id" seance.
     *
     * @param id the id of the seanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seances/{id}")
    @Timed
    public ResponseEntity<SeanceDTO> getSeance(@PathVariable Long id) {
        log.debug("REST request to get Seance : {}", id);
        SeanceDTO seanceDTO = seanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seanceDTO));
    }

    /**
     * DELETE  /seances/:id : delete the "id" seance.
     *
     * @param id the id of the seanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seances/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        log.debug("REST request to delete Seance : {}", id);
        seanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
