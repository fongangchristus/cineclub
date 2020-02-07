package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.FormuleService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.FormuleDTO;
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
 * REST controller for managing Formule.
 */
@RestController
@RequestMapping("/api")
public class FormuleResource {

    private final Logger log = LoggerFactory.getLogger(FormuleResource.class);

    private static final String ENTITY_NAME = "formule";

    private final FormuleService formuleService;

    public FormuleResource(FormuleService formuleService) {
        this.formuleService = formuleService;
    }

    /**
     * POST  /formules : Create a new formule.
     *
     * @param formuleDTO the formuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formuleDTO, or with status 400 (Bad Request) if the formule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formules")
    @Timed
    public ResponseEntity<FormuleDTO> createFormule(@RequestBody FormuleDTO formuleDTO) throws URISyntaxException {
        log.debug("REST request to save Formule : {}", formuleDTO);
        if (formuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new formule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormuleDTO result = formuleService.save(formuleDTO);
        return ResponseEntity.created(new URI("/api/formules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formules : Updates an existing formule.
     *
     * @param formuleDTO the formuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formuleDTO,
     * or with status 400 (Bad Request) if the formuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the formuleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formules")
    @Timed
    public ResponseEntity<FormuleDTO> updateFormule(@RequestBody FormuleDTO formuleDTO) throws URISyntaxException {
        log.debug("REST request to update Formule : {}", formuleDTO);
        if (formuleDTO.getId() == null) {
            return createFormule(formuleDTO);
        }
        FormuleDTO result = formuleService.save(formuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formules : get all the formules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formules in body
     */
    @GetMapping("/formules")
    @Timed
    public ResponseEntity<List<FormuleDTO>> getAllFormules(Pageable pageable) {
        log.debug("REST request to get a page of Formules");
        Page<FormuleDTO> page = formuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/formules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /formules/:id : get the "id" formule.
     *
     * @param id the id of the formuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/formules/{id}")
    @Timed
    public ResponseEntity<FormuleDTO> getFormule(@PathVariable Long id) {
        log.debug("REST request to get Formule : {}", id);
        FormuleDTO formuleDTO = formuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formuleDTO));
    }

    /**
     * DELETE  /formules/:id : delete the "id" formule.
     *
     * @param id the id of the formuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formules/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormule(@PathVariable Long id) {
        log.debug("REST request to delete Formule : {}", id);
        formuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
