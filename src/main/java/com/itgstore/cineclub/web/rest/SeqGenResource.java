package com.itgstore.cineclub.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itgstore.cineclub.domain.SeqGen;
import com.itgstore.cineclub.repository.SeqGenRepository;
import com.itgstore.cineclub.web.rest.errors.BadRequestAlertException;
import com.itgstore.cineclub.web.rest.util.HeaderUtil;
import com.itgstore.cineclub.web.rest.util.PaginationUtil;

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
 * REST controller for managing SeqGen.
 */
@RestController
@RequestMapping("/api")
public class SeqGenResource {

    private final Logger log = LoggerFactory.getLogger(SeqGenResource.class);

    private static final String ENTITY_NAME = "seqGen";

    private final SeqGenRepository seqGenRepository;

    public SeqGenResource(SeqGenRepository seqGenRepository) {
        this.seqGenRepository = seqGenRepository;
    }

    /**
     * POST  /seq-gens : Create a new seqGen.
     *
     * @param seqGen the seqGen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seqGen, or with status 400 (Bad Request) if the seqGen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seq-gens")
    @Timed
    public ResponseEntity<SeqGen> createSeqGen(@Valid @RequestBody SeqGen seqGen) throws URISyntaxException {
        log.debug("REST request to save SeqGen : {}", seqGen);
        if (seqGen.getId() != null) {
            throw new BadRequestAlertException("A new seqGen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeqGen result = seqGenRepository.save(seqGen);
        return ResponseEntity.created(new URI("/api/seq-gens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seq-gens : Updates an existing seqGen.
     *
     * @param seqGen the seqGen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seqGen,
     * or with status 400 (Bad Request) if the seqGen is not valid,
     * or with status 500 (Internal Server Error) if the seqGen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seq-gens")
    @Timed
    public ResponseEntity<SeqGen> updateSeqGen(@Valid @RequestBody SeqGen seqGen) throws URISyntaxException {
        log.debug("REST request to update SeqGen : {}", seqGen);
        if (seqGen.getId() == null) {
            return createSeqGen(seqGen);
        }
        SeqGen result = seqGenRepository.save(seqGen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seqGen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seq-gens : get all the seqGens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of seqGens in body
     */
    @GetMapping("/seq-gens")
    @Timed
    public ResponseEntity<List<SeqGen>> getAllSeqGens(Pageable pageable) {
        log.debug("REST request to get a page of SeqGens");
        Page<SeqGen> page = seqGenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/seq-gens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /seq-gens/:id : get the "id" seqGen.
     *
     * @param id the id of the seqGen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seqGen, or with status 404 (Not Found)
     */
    @GetMapping("/seq-gens/{id}")
    @Timed
    public ResponseEntity<SeqGen> getSeqGen(@PathVariable Long id) {
        log.debug("REST request to get SeqGen : {}", id);
        SeqGen seqGen = seqGenRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(seqGen));
    }

    /**
     * DELETE  /seq-gens/:id : delete the "id" seqGen.
     *
     * @param id the id of the seqGen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seq-gens/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeqGen(@PathVariable Long id) {
        log.debug("REST request to delete SeqGen : {}", id);
        seqGenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
