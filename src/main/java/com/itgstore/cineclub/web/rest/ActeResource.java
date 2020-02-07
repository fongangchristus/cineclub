package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.ActeService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.ActeDTO;
import com.itgstore.cineclub.service.dto.ActeCriteria;
import com.itgstore.cineclub.service.ActeQueryService;
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
 * REST controller for managing Acte.
 */
@RestController
@RequestMapping("/api")
public class ActeResource {

    private final Logger log = LoggerFactory.getLogger(ActeResource.class);

    private static final String ENTITY_NAME = "acte";

    private final ActeService acteService;

    private final ActeQueryService acteQueryService;

    public ActeResource(ActeService acteService, ActeQueryService acteQueryService) {
        this.acteService = acteService;
        this.acteQueryService = acteQueryService;
    }

    /**
     * POST  /actes : Create a new acte.
     *
     * @param acteDTO the acteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acteDTO, or with status 400 (Bad Request) if the acte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actes")
    @Timed
    public ResponseEntity<ActeDTO> createActe(@Valid @RequestBody ActeDTO acteDTO) throws URISyntaxException {
        log.debug("REST request to save Acte : {}", acteDTO);
        if (acteDTO.getId() != null) {
            throw new BadRequestAlertException("A new acte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActeDTO result = acteService.save(acteDTO);
        return ResponseEntity.created(new URI("/api/actes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actes : Updates an existing acte.
     *
     * @param acteDTO the acteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acteDTO,
     * or with status 400 (Bad Request) if the acteDTO is not valid,
     * or with status 500 (Internal Server Error) if the acteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actes")
    @Timed
    public ResponseEntity<ActeDTO> updateActe(@Valid @RequestBody ActeDTO acteDTO) throws URISyntaxException {
        log.debug("REST request to update Acte : {}", acteDTO);
        if (acteDTO.getId() == null) {
            return createActe(acteDTO);
        }
        ActeDTO result = acteService.save(acteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actes : get all the actes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of actes in body
     */
    @GetMapping("/actes")
    @Timed
    public ResponseEntity<List<ActeDTO>> getAllActes(ActeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Actes by criteria: {}", criteria);
        Page<ActeDTO> page = acteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actes/:id : get the "id" acte.
     *
     * @param id the id of the acteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/actes/{id}")
    @Timed
    public ResponseEntity<ActeDTO> getActe(@PathVariable Long id) {
        log.debug("REST request to get Acte : {}", id);
        ActeDTO acteDTO = acteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acteDTO));
    }

    /**
     * DELETE  /actes/:id : delete the "id" acte.
     *
     * @param id the id of the acteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actes/{id}")
    @Timed
    public ResponseEntity<Void> deleteActe(@PathVariable Long id) {
        log.debug("REST request to delete Acte : {}", id);
        acteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
