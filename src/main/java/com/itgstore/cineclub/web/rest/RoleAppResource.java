package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.RoleAppService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.RoleAppDTO;
import com.itgstore.cineclub.service.dto.RoleAppCriteria;
import com.itgstore.cineclub.service.RoleAppQueryService;
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
 * REST controller for managing PgwRoleApp.
 */
@RestController
@RequestMapping("/api")
public class RoleAppResource {

    private final Logger log = LoggerFactory.getLogger(RoleAppResource.class);

    private static final String ENTITY_NAME = "pgwRoleApp";

    private final RoleAppService pgwRoleAppService;

    private final RoleAppQueryService pgwRoleAppQueryService;

    public RoleAppResource(RoleAppService pgwRoleAppService, RoleAppQueryService pgwRoleAppQueryService) {
        this.pgwRoleAppService = pgwRoleAppService;
        this.pgwRoleAppQueryService = pgwRoleAppQueryService;
    }

    /**
     * POST  /pgw-role-apps : Create a new pgwRoleApp.
     *
     * @param pgwRoleAppDTO the pgwRoleAppDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pgwRoleAppDTO, or with status 400 (Bad Request) if the pgwRoleApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pgw-role-apps")
    @Timed
    public ResponseEntity<RoleAppDTO> createPgwRoleApp(@Valid @RequestBody RoleAppDTO pgwRoleAppDTO) throws URISyntaxException {
        log.debug("REST request to save PgwRoleApp : {}", pgwRoleAppDTO);
        if (pgwRoleAppDTO.getId() != null) {
            throw new BadRequestAlertException("A new pgwRoleApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleAppDTO result = pgwRoleAppService.save(pgwRoleAppDTO);
        return ResponseEntity.created(new URI("/api/pgw-role-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgw-role-apps : Updates an existing pgwRoleApp.
     *
     * @param pgwRoleAppDTO the pgwRoleAppDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pgwRoleAppDTO,
     * or with status 400 (Bad Request) if the pgwRoleAppDTO is not valid,
     * or with status 500 (Internal Server Error) if the pgwRoleAppDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pgw-role-apps")
    @Timed
    public ResponseEntity<RoleAppDTO> updatePgwRoleApp(@Valid @RequestBody RoleAppDTO pgwRoleAppDTO) throws URISyntaxException {
        log.debug("REST request to update PgwRoleApp : {}", pgwRoleAppDTO);
        if (pgwRoleAppDTO.getId() == null) {
            return createPgwRoleApp(pgwRoleAppDTO);
        }
        RoleAppDTO result = pgwRoleAppService.save(pgwRoleAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pgwRoleAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgw-role-apps : get all the pgwRoleApps.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of pgwRoleApps in body
     */
    @GetMapping("/pgw-role-apps")
    @Timed
    public ResponseEntity<List<RoleAppDTO>> getAllPgwRoleApps(RoleAppCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PgwRoleApps by criteria: {}", criteria);
        Page<RoleAppDTO> page = pgwRoleAppQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgw-role-apps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgw-role-apps/:id : get the "id" pgwRoleApp.
     *
     * @param id the id of the pgwRoleAppDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pgwRoleAppDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pgw-role-apps/{id}")
    @Timed
    public ResponseEntity<RoleAppDTO> getPgwRoleApp(@PathVariable Long id) {
        log.debug("REST request to get PgwRoleApp : {}", id);
        RoleAppDTO pgwRoleAppDTO = pgwRoleAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pgwRoleAppDTO));
    }

    /**
     * DELETE  /pgw-role-apps/:id : delete the "id" pgwRoleApp.
     *
     * @param id the id of the pgwRoleAppDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pgw-role-apps/{id}")
    @Timed
    public ResponseEntity<Void> deletePgwRoleApp(@PathVariable Long id) {
        log.debug("REST request to delete PgwRoleApp : {}", id);
        pgwRoleAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
