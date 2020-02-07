package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.ProjectionService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.ProjectionDTO;
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
 * REST controller for managing Projection.
 */
@RestController
@RequestMapping("/api")
public class ProjectionResource {

    private final Logger log = LoggerFactory.getLogger(ProjectionResource.class);

    private static final String ENTITY_NAME = "projection";

    private final ProjectionService projectionService;

    public ProjectionResource(ProjectionService projectionService) {
        this.projectionService = projectionService;
    }

    /**
     * POST  /projections : Create a new projection.
     *
     * @param projectionDTO the projectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectionDTO, or with status 400 (Bad Request) if the projection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projections")
    @Timed
    public ResponseEntity<ProjectionDTO> createProjection(@RequestBody ProjectionDTO projectionDTO) throws URISyntaxException {
        log.debug("REST request to save Projection : {}", projectionDTO);
        if (projectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new projection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectionDTO result = projectionService.save(projectionDTO);
        return ResponseEntity.created(new URI("/api/projections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projections : Updates an existing projection.
     *
     * @param projectionDTO the projectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectionDTO,
     * or with status 400 (Bad Request) if the projectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projections")
    @Timed
    public ResponseEntity<ProjectionDTO> updateProjection(@RequestBody ProjectionDTO projectionDTO) throws URISyntaxException {
        log.debug("REST request to update Projection : {}", projectionDTO);
        if (projectionDTO.getId() == null) {
            return createProjection(projectionDTO);
        }
        ProjectionDTO result = projectionService.save(projectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projections : get all the projections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projections in body
     */
    @GetMapping("/projections")
    @Timed
    public ResponseEntity<List<ProjectionDTO>> getAllProjections(Pageable pageable) {
        log.debug("REST request to get a page of Projections");
        Page<ProjectionDTO> page = projectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projections/:id : get the "id" projection.
     *
     * @param id the id of the projectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/projections/{id}")
    @Timed
    public ResponseEntity<ProjectionDTO> getProjection(@PathVariable Long id) {
        log.debug("REST request to get Projection : {}", id);
        ProjectionDTO projectionDTO = projectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectionDTO));
    }

    /**
     * DELETE  /projections/:id : delete the "id" projection.
     *
     * @param id the id of the projectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projections/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjection(@PathVariable Long id) {
        log.debug("REST request to delete Projection : {}", id);
        projectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
