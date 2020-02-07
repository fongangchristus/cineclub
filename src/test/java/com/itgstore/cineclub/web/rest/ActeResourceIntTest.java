package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Acte;
import com.itgstore.cineclub.repository.ActeRepository;
import com.itgstore.cineclub.service.ActeService;
import com.itgstore.cineclub.service.dto.ActeDTO;
import com.itgstore.cineclub.service.mapper.ActeMapper;
import com.itgstore.cineclub.web.rest.errors.ExceptionTranslator;
import com.itgstore.cineclub.service.dto.ActeCriteria;
import com.itgstore.cineclub.service.ActeQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itgstore.cineclub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActeResource REST controller.
 *
 * @see ActeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class ActeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ActeRepository acteRepository;

    @Autowired
    private ActeMapper acteMapper;

    @Autowired
    private ActeService acteService;

    @Autowired
    private ActeQueryService acteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActeMockMvc;

    private Acte acte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeResource acteResource = new ActeResource(acteService, acteQueryService);
        this.restActeMockMvc = MockMvcBuilders.standaloneSetup(acteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acte createEntity(EntityManager em) {
        Acte acte = new Acte()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return acte;
    }

    @Before
    public void initTest() {
        acte = createEntity(em);
    }

    @Test
    @Transactional
    public void createActe() throws Exception {
        int databaseSizeBeforeCreate = acteRepository.findAll().size();

        // Create the Acte
        ActeDTO acteDTO = acteMapper.toDto(acte);
        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isCreated());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeCreate + 1);
        Acte testActe = acteList.get(acteList.size() - 1);
        assertThat(testActe.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActe.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createActeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteRepository.findAll().size();

        // Create the Acte with an existing ID
        acte.setId(1L);
        ActeDTO acteDTO = acteMapper.toDto(acte);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acteRepository.findAll().size();
        // set the field null
        acte.setCode(null);

        // Create the Acte, which fails.
        ActeDTO acteDTO = acteMapper.toDto(acte);

        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isBadRequest());

        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = acteRepository.findAll().size();
        // set the field null
        acte.setLibelle(null);

        // Create the Acte, which fails.
        ActeDTO acteDTO = acteMapper.toDto(acte);

        restActeMockMvc.perform(post("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isBadRequest());

        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActes() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList
        restActeMockMvc.perform(get("/api/actes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acte.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get the acte
        restActeMockMvc.perform(get("/api/actes/{id}", acte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acte.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getAllActesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where code equals to DEFAULT_CODE
        defaultActeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the acteList where code equals to UPDATED_CODE
        defaultActeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllActesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where code in DEFAULT_CODE or UPDATED_CODE
        defaultActeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the acteList where code equals to UPDATED_CODE
        defaultActeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllActesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where code is not null
        defaultActeShouldBeFound("code.specified=true");

        // Get all the acteList where code is null
        defaultActeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllActesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where libelle equals to DEFAULT_LIBELLE
        defaultActeShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the acteList where libelle equals to UPDATED_LIBELLE
        defaultActeShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllActesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultActeShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the acteList where libelle equals to UPDATED_LIBELLE
        defaultActeShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllActesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);

        // Get all the acteList where libelle is not null
        defaultActeShouldBeFound("libelle.specified=true");

        // Get all the acteList where libelle is null
        defaultActeShouldNotBeFound("libelle.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultActeShouldBeFound(String filter) throws Exception {
        restActeMockMvc.perform(get("/api/actes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acte.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultActeShouldNotBeFound(String filter) throws Exception {
        restActeMockMvc.perform(get("/api/actes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingActe() throws Exception {
        // Get the acte
        restActeMockMvc.perform(get("/api/actes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);
        int databaseSizeBeforeUpdate = acteRepository.findAll().size();

        // Update the acte
        Acte updatedActe = acteRepository.findOne(acte.getId());
        // Disconnect from session so that the updates on updatedActe are not directly saved in db
        em.detach(updatedActe);
        updatedActe
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        ActeDTO acteDTO = acteMapper.toDto(updatedActe);

        restActeMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isOk());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeUpdate);
        Acte testActe = acteList.get(acteList.size() - 1);
        assertThat(testActe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActe.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingActe() throws Exception {
        int databaseSizeBeforeUpdate = acteRepository.findAll().size();

        // Create the Acte
        ActeDTO acteDTO = acteMapper.toDto(acte);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActeMockMvc.perform(put("/api/actes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteDTO)))
            .andExpect(status().isCreated());

        // Validate the Acte in the database
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActe() throws Exception {
        // Initialize the database
        acteRepository.saveAndFlush(acte);
        int databaseSizeBeforeDelete = acteRepository.findAll().size();

        // Get the acte
        restActeMockMvc.perform(delete("/api/actes/{id}", acte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acte> acteList = acteRepository.findAll();
        assertThat(acteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acte.class);
        Acte acte1 = new Acte();
        acte1.setId(1L);
        Acte acte2 = new Acte();
        acte2.setId(acte1.getId());
        assertThat(acte1).isEqualTo(acte2);
        acte2.setId(2L);
        assertThat(acte1).isNotEqualTo(acte2);
        acte1.setId(null);
        assertThat(acte1).isNotEqualTo(acte2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeDTO.class);
        ActeDTO acteDTO1 = new ActeDTO();
        acteDTO1.setId(1L);
        ActeDTO acteDTO2 = new ActeDTO();
        assertThat(acteDTO1).isNotEqualTo(acteDTO2);
        acteDTO2.setId(acteDTO1.getId());
        assertThat(acteDTO1).isEqualTo(acteDTO2);
        acteDTO2.setId(2L);
        assertThat(acteDTO1).isNotEqualTo(acteDTO2);
        acteDTO1.setId(null);
        assertThat(acteDTO1).isNotEqualTo(acteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(acteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(acteMapper.fromId(null)).isNull();
    }
}
