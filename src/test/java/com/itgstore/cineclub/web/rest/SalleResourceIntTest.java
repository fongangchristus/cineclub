package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Salle;
import com.itgstore.cineclub.repository.SalleRepository;
import com.itgstore.cineclub.service.SalleService;
import com.itgstore.cineclub.service.dto.SalleDTO;
import com.itgstore.cineclub.service.mapper.SalleMapper;
import com.itgstore.cineclub.web.rest.errors.ExceptionTranslator;

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
 * Test class for the SalleResource REST controller.
 *
 * @see SalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class SalleResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_COUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_PATH_COUVERTURE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_PLACE_TOTALE = 1;
    private static final Integer UPDATED_NOMBRE_PLACE_TOTALE = 2;

    private static final Integer DEFAULT_NOMBRE_PLACE_BALCON = 1;
    private static final Integer UPDATED_NOMBRE_PLACE_BALCON = 2;

    private static final Integer DEFAULT_NOMBRE_PLACE_CLASIQUE = 1;
    private static final Integer UPDATED_NOMBRE_PLACE_CLASIQUE = 2;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleMapper salleMapper;

    @Autowired
    private SalleService salleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalleMockMvc;

    private Salle salle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalleResource salleResource = new SalleResource(salleService);
        this.restSalleMockMvc = MockMvcBuilders.standaloneSetup(salleResource)
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
    public static Salle createEntity(EntityManager em) {
        Salle salle = new Salle()
            .libele(DEFAULT_LIBELE)
            .pathCouverture(DEFAULT_PATH_COUVERTURE)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .nombrePlaceTotale(DEFAULT_NOMBRE_PLACE_TOTALE)
            .nombrePlaceBalcon(DEFAULT_NOMBRE_PLACE_BALCON)
            .nombrePlaceClasique(DEFAULT_NOMBRE_PLACE_CLASIQUE);
        return salle;
    }

    @Before
    public void initTest() {
        salle = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalle() throws Exception {
        int databaseSizeBeforeCreate = salleRepository.findAll().size();

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);
        restSalleMockMvc.perform(post("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isCreated());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate + 1);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testSalle.getPathCouverture()).isEqualTo(DEFAULT_PATH_COUVERTURE);
        assertThat(testSalle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSalle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSalle.getNombrePlaceTotale()).isEqualTo(DEFAULT_NOMBRE_PLACE_TOTALE);
        assertThat(testSalle.getNombrePlaceBalcon()).isEqualTo(DEFAULT_NOMBRE_PLACE_BALCON);
        assertThat(testSalle.getNombrePlaceClasique()).isEqualTo(DEFAULT_NOMBRE_PLACE_CLASIQUE);
    }

    @Test
    @Transactional
    public void createSalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salleRepository.findAll().size();

        // Create the Salle with an existing ID
        salle.setId(1L);
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalleMockMvc.perform(post("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibeleIsRequired() throws Exception {
        int databaseSizeBeforeTest = salleRepository.findAll().size();
        // set the field null
        salle.setLibele(null);

        // Create the Salle, which fails.
        SalleDTO salleDTO = salleMapper.toDto(salle);

        restSalleMockMvc.perform(post("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isBadRequest());

        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalles() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get all the salleList
        restSalleMockMvc.perform(get("/api/salles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salle.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].pathCouverture").value(hasItem(DEFAULT_PATH_COUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nombrePlaceTotale").value(hasItem(DEFAULT_NOMBRE_PLACE_TOTALE)))
            .andExpect(jsonPath("$.[*].nombrePlaceBalcon").value(hasItem(DEFAULT_NOMBRE_PLACE_BALCON)))
            .andExpect(jsonPath("$.[*].nombrePlaceClasique").value(hasItem(DEFAULT_NOMBRE_PLACE_CLASIQUE)));
    }

    @Test
    @Transactional
    public void getSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);

        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", salle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salle.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.pathCouverture").value(DEFAULT_PATH_COUVERTURE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.nombrePlaceTotale").value(DEFAULT_NOMBRE_PLACE_TOTALE))
            .andExpect(jsonPath("$.nombrePlaceBalcon").value(DEFAULT_NOMBRE_PLACE_BALCON))
            .andExpect(jsonPath("$.nombrePlaceClasique").value(DEFAULT_NOMBRE_PLACE_CLASIQUE));
    }

    @Test
    @Transactional
    public void getNonExistingSalle() throws Exception {
        // Get the salle
        restSalleMockMvc.perform(get("/api/salles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Update the salle
        Salle updatedSalle = salleRepository.findOne(salle.getId());
        // Disconnect from session so that the updates on updatedSalle are not directly saved in db
        em.detach(updatedSalle);
        updatedSalle
            .libele(UPDATED_LIBELE)
            .pathCouverture(UPDATED_PATH_COUVERTURE)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .nombrePlaceTotale(UPDATED_NOMBRE_PLACE_TOTALE)
            .nombrePlaceBalcon(UPDATED_NOMBRE_PLACE_BALCON)
            .nombrePlaceClasique(UPDATED_NOMBRE_PLACE_CLASIQUE);
        SalleDTO salleDTO = salleMapper.toDto(updatedSalle);

        restSalleMockMvc.perform(put("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isOk());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate);
        Salle testSalle = salleList.get(salleList.size() - 1);
        assertThat(testSalle.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testSalle.getPathCouverture()).isEqualTo(UPDATED_PATH_COUVERTURE);
        assertThat(testSalle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSalle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalle.getNombrePlaceTotale()).isEqualTo(UPDATED_NOMBRE_PLACE_TOTALE);
        assertThat(testSalle.getNombrePlaceBalcon()).isEqualTo(UPDATED_NOMBRE_PLACE_BALCON);
        assertThat(testSalle.getNombrePlaceClasique()).isEqualTo(UPDATED_NOMBRE_PLACE_CLASIQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalle() throws Exception {
        int databaseSizeBeforeUpdate = salleRepository.findAll().size();

        // Create the Salle
        SalleDTO salleDTO = salleMapper.toDto(salle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalleMockMvc.perform(put("/api/salles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleDTO)))
            .andExpect(status().isCreated());

        // Validate the Salle in the database
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalle() throws Exception {
        // Initialize the database
        salleRepository.saveAndFlush(salle);
        int databaseSizeBeforeDelete = salleRepository.findAll().size();

        // Get the salle
        restSalleMockMvc.perform(delete("/api/salles/{id}", salle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Salle> salleList = salleRepository.findAll();
        assertThat(salleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salle.class);
        Salle salle1 = new Salle();
        salle1.setId(1L);
        Salle salle2 = new Salle();
        salle2.setId(salle1.getId());
        assertThat(salle1).isEqualTo(salle2);
        salle2.setId(2L);
        assertThat(salle1).isNotEqualTo(salle2);
        salle1.setId(null);
        assertThat(salle1).isNotEqualTo(salle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalleDTO.class);
        SalleDTO salleDTO1 = new SalleDTO();
        salleDTO1.setId(1L);
        SalleDTO salleDTO2 = new SalleDTO();
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO2.setId(salleDTO1.getId());
        assertThat(salleDTO1).isEqualTo(salleDTO2);
        salleDTO2.setId(2L);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO1.setId(null);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salleMapper.fromId(null)).isNull();
    }
}
