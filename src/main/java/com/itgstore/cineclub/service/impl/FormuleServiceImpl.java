package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.FormuleService;
import com.itgstore.cineclub.domain.Formule;
import com.itgstore.cineclub.repository.FormuleRepository;
import com.itgstore.cineclub.service.dto.FormuleDTO;
import com.itgstore.cineclub.service.mapper.FormuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Formule.
 */
@Service
@Transactional
public class FormuleServiceImpl implements FormuleService {

    private final Logger log = LoggerFactory.getLogger(FormuleServiceImpl.class);

    private final FormuleRepository formuleRepository;

    private final FormuleMapper formuleMapper;

    public FormuleServiceImpl(FormuleRepository formuleRepository, FormuleMapper formuleMapper) {
        this.formuleRepository = formuleRepository;
        this.formuleMapper = formuleMapper;
    }

    /**
     * Save a formule.
     *
     * @param formuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FormuleDTO save(FormuleDTO formuleDTO) {
        log.debug("Request to save Formule : {}", formuleDTO);
        Formule formule = formuleMapper.toEntity(formuleDTO);
        formule = formuleRepository.save(formule);
        return formuleMapper.toDto(formule);
    }

    /**
     * Get all the formules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Formules");
        return formuleRepository.findAll(pageable)
            .map(formuleMapper::toDto);
    }

    /**
     * Get one formule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormuleDTO findOne(Long id) {
        log.debug("Request to get Formule : {}", id);
        Formule formule = formuleRepository.findOne(id);
        return formuleMapper.toDto(formule);
    }

    /**
     * Delete the formule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formule : {}", id);
        formuleRepository.delete(id);
    }
}
