package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.service.TicketReserveService;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;
import com.itgstore.cineclub.service.dto.TicketReserveDTO;
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
 * REST controller for managing TicketReserve.
 */
@RestController
@RequestMapping("/api")
public class TicketReserveResource {

    private final Logger log = LoggerFactory.getLogger(TicketReserveResource.class);

    private static final String ENTITY_NAME = "ticketReserve";

    private final TicketReserveService ticketReserveService;

    public TicketReserveResource(TicketReserveService ticketReserveService) {
        this.ticketReserveService = ticketReserveService;
    }

    /**
     * POST  /ticket-reserves : Create a new ticketReserve.
     *
     * @param ticketReserveDTO the ticketReserveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketReserveDTO, or with status 400 (Bad Request) if the ticketReserve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ticket-reserves")
    @Timed
    public ResponseEntity<TicketReserveDTO> createTicketReserve(@Valid @RequestBody TicketReserveDTO ticketReserveDTO) throws URISyntaxException {
        log.debug("REST request to save TicketReserve : {}", ticketReserveDTO);
        if (ticketReserveDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketReserve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketReserveDTO result = ticketReserveService.save(ticketReserveDTO);
        return ResponseEntity.created(new URI("/api/ticket-reserves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ticket-reserves : Updates an existing ticketReserve.
     *
     * @param ticketReserveDTO the ticketReserveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketReserveDTO,
     * or with status 400 (Bad Request) if the ticketReserveDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketReserveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ticket-reserves")
    @Timed
    public ResponseEntity<TicketReserveDTO> updateTicketReserve(@Valid @RequestBody TicketReserveDTO ticketReserveDTO) throws URISyntaxException {
        log.debug("REST request to update TicketReserve : {}", ticketReserveDTO);
        if (ticketReserveDTO.getId() == null) {
            return createTicketReserve(ticketReserveDTO);
        }
        TicketReserveDTO result = ticketReserveService.save(ticketReserveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticketReserveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ticket-reserves : get all the ticketReserves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ticketReserves in body
     */
    @GetMapping("/ticket-reserves")
    @Timed
    public ResponseEntity<List<TicketReserveDTO>> getAllTicketReserves(Pageable pageable) {
        log.debug("REST request to get a page of TicketReserves");
        Page<TicketReserveDTO> page = ticketReserveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ticket-reserves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ticket-reserves/:id : get the "id" ticketReserve.
     *
     * @param id the id of the ticketReserveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketReserveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ticket-reserves/{id}")
    @Timed
    public ResponseEntity<TicketReserveDTO> getTicketReserve(@PathVariable Long id) {
        log.debug("REST request to get TicketReserve : {}", id);
        TicketReserveDTO ticketReserveDTO = ticketReserveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketReserveDTO));
    }

    /**
     * DELETE  /ticket-reserves/:id : delete the "id" ticketReserve.
     *
     * @param id the id of the ticketReserveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ticket-reserves/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicketReserve(@PathVariable Long id) {
        log.debug("REST request to delete TicketReserve : {}", id);
        ticketReserveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
