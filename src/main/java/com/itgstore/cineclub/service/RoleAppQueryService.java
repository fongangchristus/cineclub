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

import com.itgstore.cineclub.domain.RoleApp;
import com.itgstore.cineclub.domain.*; // for static metamodels
import com.itgstore.cineclub.repository.RoleAppRepository;
import com.itgstore.cineclub.service.dto.RoleAppCriteria;

import com.itgstore.cineclub.service.dto.RoleAppDTO;
import com.itgstore.cineclub.service.mapper.RoleAppMapper;

/**
 * Service for executing complex queries for PgwRoleApp entities in the database.
 * The main input is a {@link RoleAppCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleAppDTO} or a {@link Page} of {@link RoleAppDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleAppQueryService extends QueryService<RoleApp> {

    private final Logger log = LoggerFactory.getLogger(RoleAppQueryService.class);


    private final RoleAppRepository roleAppRepository;

    private final RoleAppMapper roleAppMapper;

    public RoleAppQueryService(RoleAppRepository roleAppRepository, RoleAppMapper roleAppMapper) {
        this.roleAppRepository = roleAppRepository;
        this.roleAppMapper = roleAppMapper;
    }

    /**
     * Return a {@link List} of {@link RoleAppDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleAppDTO> findByCriteria(RoleAppCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<RoleApp> specification = createSpecification(criteria);
        return roleAppMapper.toDto(roleAppRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoleAppDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleAppDTO> findByCriteria(RoleAppCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<RoleApp> specification = createSpecification(criteria);
        final Page<RoleApp> result = roleAppRepository.findAll(specification, page);
        return result.map(roleAppMapper::toDto);
    }

    /**
     * Function to convert PgwRoleAppCriteria to a {@link Specifications}
     */
    private Specifications<RoleApp> createSpecification(RoleAppCriteria criteria) {
        Specifications<RoleApp> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RoleApp_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), RoleApp_.libelle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RoleApp_.description));
            }
        }
        return specification;
    }

}
