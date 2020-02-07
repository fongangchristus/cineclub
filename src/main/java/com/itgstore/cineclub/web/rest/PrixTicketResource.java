package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.PrixTicketService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.PrixTicketDTO;
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
 * REST controller for managing PrixTicket.
 */
@RestController
@RequestMapping("/api")
public class PrixTicketResource {

    private final Logger log = LoggerFactory.getLogger(PrixTicketResource.class);

    private static final String ENTITY_NAME = "prixTicket";

    private final PrixTicketService prixTicketService;

    public PrixTicketResource(PrixTicketService prixTicketService) {
        this.prixTicketService = prixTicketService;
    }

    /**
     * POST  /prix-tickets : Create a new prixTicket.
     *
     * @param prixTicketDTO the prixTicketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prixTicketDTO, or with status 400 (Bad Request) if the prixTicket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prix-tickets")
    @Timed
    public ResponseEntity<PrixTicketDTO> createPrixTicket(@RequestBody PrixTicketDTO prixTicketDTO) throws URISyntaxException {
        log.debug("REST request to save PrixTicket : {}", prixTicketDTO);
        if (prixTicketDTO.getId() != null) {
            throw new BadRequestAlertException("A new prixTicket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrixTicketDTO result = prixTicketService.save(prixTicketDTO);
        return ResponseEntity.created(new URI("/api/prix-tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prix-tickets : Updates an existing prixTicket.
     *
     * @param prixTicketDTO the prixTicketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prixTicketDTO,
     * or with status 400 (Bad Request) if the prixTicketDTO is not valid,
     * or with status 500 (Internal Server Error) if the prixTicketDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prix-tickets")
    @Timed
    public ResponseEntity<PrixTicketDTO> updatePrixTicket(@RequestBody PrixTicketDTO prixTicketDTO) throws URISyntaxException {
        log.debug("REST request to update PrixTicket : {}", prixTicketDTO);
        if (prixTicketDTO.getId() == null) {
            return createPrixTicket(prixTicketDTO);
        }
        PrixTicketDTO result = prixTicketService.save(prixTicketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prixTicketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prix-tickets : get all the prixTickets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prixTickets in body
     */
    @GetMapping("/prix-tickets")
    @Timed
    public ResponseEntity<List<PrixTicketDTO>> getAllPrixTickets(Pageable pageable) {
        log.debug("REST request to get a page of PrixTickets");
        Page<PrixTicketDTO> page = prixTicketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prix-tickets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prix-tickets/:id : get the "id" prixTicket.
     *
     * @param id the id of the prixTicketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prixTicketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prix-tickets/{id}")
    @Timed
    public ResponseEntity<PrixTicketDTO> getPrixTicket(@PathVariable Long id) {
        log.debug("REST request to get PrixTicket : {}", id);
        PrixTicketDTO prixTicketDTO = prixTicketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prixTicketDTO));
    }

    /**
     * DELETE  /prix-tickets/:id : delete the "id" prixTicket.
     *
     * @param id the id of the prixTicketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prix-tickets/{id}")
    @Timed
    public ResponseEntity<Void> deletePrixTicket(@PathVariable Long id) {
        log.debug("REST request to delete PrixTicket : {}", id);
        prixTicketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
