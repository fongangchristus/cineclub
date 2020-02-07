package com.itgstore.cineclub.service.impl;

import com.itgstore.cineclub.service.AdresseService;
import com.itgstore.cineclub.domain.Adresse;
import com.itgstore.cineclub.repository.AdresseRepository;
import com.itgstore.cineclub.service.dto.AdresseDTO;
import com.itgstore.cineclub.service.mapper.AdresseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseServiceImpl implements AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseServiceImpl.class);

    private final AdresseRepository adresseRepository;

    private final AdresseMapper adresseMapper;

    public AdresseServiceImpl(AdresseRepository adresseRepository, AdresseMapper adresseMapper) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
    }

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdresseDTO save(AdresseDTO adresseDTO) {
        log.debug("Request to save Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Get all the adresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdresseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Adresses");
        return adresseRepository.findAll(pageable)
            .map(adresseMapper::toDto);
    }

    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdresseDTO findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        Adresse adresse = adresseRepository.findOne(id);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.delete(id);
    }
}
