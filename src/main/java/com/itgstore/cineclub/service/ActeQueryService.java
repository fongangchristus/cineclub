package com.itgstore.cineclub.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.itgstore.cineclub.domain.Acte;
import com.itgstore.cineclub.domain.*; // for static metamodels
import com.itgstore.cineclub.repository.ActeRepository;
import com.itgstore.cineclub.service.dto.ActeCriteria;

import com.itgstore.cineclub.service.dto.ActeDTO;
import com.itgstore.cineclub.service.mapper.ActeMapper;

/**
 * Service for executing complex queries for Acte entities in the database.
 * The main input is a {@link ActeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActeDTO} or a {@link Page} of {@link ActeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActeQueryService extends QueryService<Acte> {

    private final Logger log = LoggerFactory.getLogger(ActeQueryService.class);


    private final ActeRepository acteRepository;

    private final ActeMapper acteMapper;

    public ActeQueryService(ActeRepository acteRepository, ActeMapper acteMapper) {
        this.acteRepository = acteRepository;
        this.acteMapper = acteMapper;
    }

    /**
     * Return a {@link List} of {@link ActeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActeDTO> findByCriteria(ActeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Acte> specification = createSpecification(criteria);
        return acteMapper.toDto(acteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ActeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActeDTO> findByCriteria(ActeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Acte> specification = createSpecification(criteria);
        final Page<Acte> result = acteRepository.findAll(specification, page);
        return result.map(acteMapper::toDto);
    }

    /**
     * Function to convert ActeCriteria to a {@link Specifications}
     */
    private Specifications<Acte> createSpecification(ActeCriteria criteria) {
        Specifications<Acte> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Acte_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Acte_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Acte_.libelle));
            }
        }
        return specification;
    }

}
