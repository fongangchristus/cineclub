package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Seance;
import com.itgstore.cineclub.repository.SeanceRepository;
import com.itgstore.cineclub.service.SeanceService;
import com.itgstore.cineclub.service.dto.SeanceDTO;
import com.itgstore.cineclub.service.mapper.SeanceMapper;
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
 * Test class for the SeanceResource REST controller.
 *
 * @see SeanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class SeanceResourceIntTest {

    private static final String DEFAULT_HEURE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SeanceMapper seanceMapper;

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeanceMockMvc;

    private Seance seance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeanceResource seanceResource = new SeanceResource(seanceService);
        this.restSeanceMockMvc = MockMvcBuilders.standaloneSetup(seanceResource)
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
    public static Seance createEntity(EntityManager em) {
        Seance seance = new Seance()
            .heureDebut(DEFAULT_HEURE_DEBUT)
            .heureFin(DEFAULT_HEURE_FIN)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE);
        return seance;
    }

    @Before
    public void initTest() {
        seance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeance() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate + 1);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getHeureDebut()).isEqualTo(DEFAULT_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(DEFAULT_HEURE_FIN);
        assertThat(testSeance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSeance.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createSeanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance with an existing ID
        seance.setId(1L);
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeances() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get all the seanceList
        restSeanceMockMvc.perform(get("/api/seances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seance.getId().intValue())))
            .andExpect(jsonPath("$.[*].heureDebut").value(hasItem(DEFAULT_HEURE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].heureFin").value(hasItem(DEFAULT_HEURE_FIN.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", seance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seance.getId().intValue()))
            .andExpect(jsonPath("$.heureDebut").value(DEFAULT_HEURE_DEBUT.toString()))
            .andExpect(jsonPath("$.heureFin").value(DEFAULT_HEURE_FIN.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeance() throws Exception {
        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance
        Seance updatedSeance = seanceRepository.findOne(seance.getId());
        // Disconnect from session so that the updates on updatedSeance are not directly saved in db
        em.detach(updatedSeance);
        updatedSeance
            .heureDebut(UPDATED_HEURE_DEBUT)
            .heureFin(UPDATED_HEURE_FIN)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE);
        SeanceDTO seanceDTO = seanceMapper.toDto(updatedSeance);

        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getHeureDebut()).isEqualTo(UPDATED_HEURE_DEBUT);
        assertThat(testSeance.getHeureFin()).isEqualTo(UPDATED_HEURE_FIN);
        assertThat(testSeance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSeance.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);
        int databaseSizeBeforeDelete = seanceRepository.findAll().size();

        // Get the seance
        restSeanceMockMvc.perform(delete("/api/seances/{id}", seance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seance.class);
        Seance seance1 = new Seance();
        seance1.setId(1L);
        Seance seance2 = new Seance();
        seance2.setId(seance1.getId());
        assertThat(seance1).isEqualTo(seance2);
        seance2.setId(2L);
        assertThat(seance1).isNotEqualTo(seance2);
        seance1.setId(null);
        assertThat(seance1).isNotEqualTo(seance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeanceDTO.class);
        SeanceDTO seanceDTO1 = new SeanceDTO();
        seanceDTO1.setId(1L);
        SeanceDTO seanceDTO2 = new SeanceDTO();
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO2.setId(seanceDTO1.getId());
        assertThat(seanceDTO1).isEqualTo(seanceDTO2);
        seanceDTO2.setId(2L);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO1.setId(null);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seanceMapper.fromId(null)).isNull();
    }
}
