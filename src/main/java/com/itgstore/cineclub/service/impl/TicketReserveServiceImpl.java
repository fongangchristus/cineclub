package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.TicketReserveService;
import com.itgstore.cineclub.domain.TicketReserve;
import com.itgstore.cineclub.repository.TicketReserveRepository;
import com.itgstore.cineclub.service.dto.TicketReserveDTO;
import com.itgstore.cineclub.service.mapper.TicketReserveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TicketReserve.
 */
@Service
@Transactional
public class TicketReserveServiceImpl implements TicketReserveService {

    private final Logger log = LoggerFactory.getLogger(TicketReserveServiceImpl.class);

    private final TicketReserveRepository ticketReserveRepository;

    private final TicketReserveMapper ticketReserveMapper;

    public TicketReserveServiceImpl(TicketReserveRepository ticketReserveRepository, TicketReserveMapper ticketReserveMapper) {
        this.ticketReserveRepository = ticketReserveRepository;
        this.ticketReserveMapper = ticketReserveMapper;
    }

    /**
     * Save a ticketReserve.
     *
     * @param ticketReserveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TicketReserveDTO save(TicketReserveDTO ticketReserveDTO) {
        log.debug("Request to save TicketReserve : {}", ticketReserveDTO);
        TicketReserve ticketReserve = ticketReserveMapper.toEntity(ticketReserveDTO);
        ticketReserve = ticketReserveRepository.save(ticketReserve);
        return ticketReserveMapper.toDto(ticketReserve);
    }

    /**
     * Get all the ticketReserves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketReserveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketReserves");
        return ticketReserveRepository.findAll(pageable)
            .map(ticketReserveMapper::toDto);
    }

    /**
     * Get one ticketReserve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TicketReserveDTO findOne(Long id) {
        log.debug("Request to get TicketReserve : {}", id);
        TicketReserve ticketReserve = ticketReserveRepository.findOne(id);
        return ticketReserveMapper.toDto(ticketReserve);
    }

    /**
     * Delete the ticketReserve by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketReserve : {}", id);
        ticketReserveRepository.delete(id);
    }
}
