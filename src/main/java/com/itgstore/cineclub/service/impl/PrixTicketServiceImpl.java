package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.PrixTicketService;
import com.itgstore.cineclub.domain.PrixTicket;
import com.itgstore.cineclub.repository.PrixTicketRepository;
import com.itgstore.cineclub.service.dto.PrixTicketDTO;
import com.itgstore.cineclub.service.mapper.PrixTicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PrixTicket.
 */
@Service
@Transactional
public class PrixTicketServiceImpl implements PrixTicketService {

    private final Logger log = LoggerFactory.getLogger(PrixTicketServiceImpl.class);

    private final PrixTicketRepository prixTicketRepository;

    private final PrixTicketMapper prixTicketMapper;

    public PrixTicketServiceImpl(PrixTicketRepository prixTicketRepository, PrixTicketMapper prixTicketMapper) {
        this.prixTicketRepository = prixTicketRepository;
        this.prixTicketMapper = prixTicketMapper;
    }

    /**
     * Save a prixTicket.
     *
     * @param prixTicketDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PrixTicketDTO save(PrixTicketDTO prixTicketDTO) {
        log.debug("Request to save PrixTicket : {}", prixTicketDTO);
        PrixTicket prixTicket = prixTicketMapper.toEntity(prixTicketDTO);
        prixTicket = prixTicketRepository.save(prixTicket);
        return prixTicketMapper.toDto(prixTicket);
    }

    /**
     * Get all the prixTickets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrixTicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrixTickets");
        return prixTicketRepository.findAll(pageable)
            .map(prixTicketMapper::toDto);
    }

    /**
     * Get one prixTicket by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PrixTicketDTO findOne(Long id) {
        log.debug("Request to get PrixTicket : {}", id);
        PrixTicket prixTicket = prixTicketRepository.findOne(id);
        return prixTicketMapper.toDto(prixTicket);
    }

    /**
     * Delete the prixTicket by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrixTicket : {}", id);
        prixTicketRepository.delete(id);
    }
}
