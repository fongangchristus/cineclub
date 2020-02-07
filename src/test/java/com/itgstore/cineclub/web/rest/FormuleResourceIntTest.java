package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Formule;
import com.itgstore.cineclub.repository.FormuleRepository;
import com.itgstore.cineclub.service.FormuleService;
import com.itgstore.cineclub.service.dto.FormuleDTO;
import com.itgstore.cineclub.service.mapper.FormuleMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.itgstore.cineclub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormuleResource REST controller.
 *
 * @see FormuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class FormuleResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RESERVATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RESERVATION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final Double DEFAULT_PRIX_BALCON = 1D;
    private static final Double UPDATED_PRIX_BALCON = 2D;

    private static final Double DEFAULT_PRIX_CLASSIQUE = 1D;
    private static final Double UPDATED_PRIX_CLASSIQUE = 2D;

    private static final Boolean DEFAULT_STATUT_FORMULE = false;
    private static final Boolean UPDATED_STATUT_FORMULE = true;

    @Autowired
    private FormuleRepository formuleRepository;

    @Autowired
    private FormuleMapper formuleMapper;

    @Autowired
    private FormuleService formuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormuleMockMvc;

    private Formule formule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormuleResource formuleResource = new FormuleResource(formuleService);
        this.restFormuleMockMvc = MockMvcBuilders.standaloneSetup(formuleResource)
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
    public static Formule createEntity(EntityManager em) {
        Formule formule = new Formule()
            .libele(DEFAULT_LIBELE)
            .dateReservation(DEFAULT_DATE_RESERVATION)
            .code(DEFAULT_CODE)
            .prixBalcon(DEFAULT_PRIX_BALCON)
            .prixClassique(DEFAULT_PRIX_CLASSIQUE)
            .statutFormule(DEFAULT_STATUT_FORMULE);
        return formule;
    }

    @Before
    public void initTest() {
        formule = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormule() throws Exception {
        int databaseSizeBeforeCreate = formuleRepository.findAll().size();

        // Create the Formule
        FormuleDTO formuleDTO = formuleMapper.toDto(formule);
        restFormuleMockMvc.perform(post("/api/formules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formuleDTO)))
            .andExpect(status().isCreated());

        // Validate the Formule in the database
        List<Formule> formuleList = formuleRepository.findAll();
        assertThat(formuleList).hasSize(databaseSizeBeforeCreate + 1);
        Formule testFormule = formuleList.get(formuleList.size() - 1);
        assertThat(testFormule.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testFormule.getDateReservation()).isEqualTo(DEFAULT_DATE_RESERVATION);
        assertThat(testFormule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFormule.getPrixBalcon()).isEqualTo(DEFAULT_PRIX_BALCON);
        assertThat(testFormule.getPrixClassique()).isEqualTo(DEFAULT_PRIX_CLASSIQUE);
        assertThat(testFormule.isStatutFormule()).isEqualTo(DEFAULT_STATUT_FORMULE);
    }

    @Test
    @Transactional
    public void createFormuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formuleRepository.findAll().size();

        // Create the Formule with an existing ID
        formule.setId(1L);
        FormuleDTO formuleDTO = formuleMapper.toDto(formule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormuleMockMvc.perform(post("/api/formules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Formule in the database
        List<Formule> formuleList = formuleRepository.findAll();
        assertThat(formuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormules() throws Exception {
        // Initialize the database
        formuleRepository.saveAndFlush(formule);

        // Get all the formuleList
        restFormuleMockMvc.perform(get("/api/formules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formule.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].dateReservation").value(hasItem(DEFAULT_DATE_RESERVATION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].prixBalcon").value(hasItem(DEFAULT_PRIX_BALCON.doubleValue())))
            .andExpect(jsonPath("$.[*].prixClassique").value(hasItem(DEFAULT_PRIX_CLASSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].statutFormule").value(hasItem(DEFAULT_STATUT_FORMULE.booleanValue())));
    }

    @Test
    @Transactional
    public void getFormule() throws Exception {
        // Initialize the database
        formuleRepository.saveAndFlush(formule);

        // Get the formule
        restFormuleMockMvc.perform(get("/api/formules/{id}", formule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formule.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.dateReservation").value(DEFAULT_DATE_RESERVATION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.prixBalcon").value(DEFAULT_PRIX_BALCON.doubleValue()))
            .andExpect(jsonPath("$.prixClassique").value(DEFAULT_PRIX_CLASSIQUE.doubleValue()))
            .andExpect(jsonPath("$.statutFormule").value(DEFAULT_STATUT_FORMULE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormule() throws Exception {
        // Get the formule
        restFormuleMockMvc.perform(get("/api/formules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormule() throws Exception {
        // Initialize the database
        formuleRepository.saveAndFlush(formule);
        int databaseSizeBeforeUpdate = formuleRepository.findAll().size();

        // Update the formule
        Formule updatedFormule = formuleRepository.findOne(formule.getId());
        // Disconnect from session so that the updates on updatedFormule are not directly saved in db
        em.detach(updatedFormule);
        updatedFormule
            .libele(UPDATED_LIBELE)
            .dateReservation(UPDATED_DATE_RESERVATION)
            .code(UPDATED_CODE)
            .prixBalcon(UPDATED_PRIX_BALCON)
            .prixClassique(UPDATED_PRIX_CLASSIQUE)
            .statutFormule(UPDATED_STATUT_FORMULE);
        FormuleDTO formuleDTO = formuleMapper.toDto(updatedFormule);

        restFormuleMockMvc.perform(put("/api/formules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formuleDTO)))
            .andExpect(status().isOk());

        // Validate the Formule in the database
        List<Formule> formuleList = formuleRepository.findAll();
        assertThat(formuleList).hasSize(databaseSizeBeforeUpdate);
        Formule testFormule = formuleList.get(formuleList.size() - 1);
        assertThat(testFormule.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testFormule.getDateReservation()).isEqualTo(UPDATED_DATE_RESERVATION);
        assertThat(testFormule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFormule.getPrixBalcon()).isEqualTo(UPDATED_PRIX_BALCON);
        assertThat(testFormule.getPrixClassique()).isEqualTo(UPDATED_PRIX_CLASSIQUE);
        assertThat(testFormule.isStatutFormule()).isEqualTo(UPDATED_STATUT_FORMULE);
    }

    @Test
    @Transactional
    public void updateNonExistingFormule() throws Exception {
        int databaseSizeBeforeUpdate = formuleRepository.findAll().size();

        // Create the Formule
        FormuleDTO formuleDTO = formuleMapper.toDto(formule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormuleMockMvc.perform(put("/api/formules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formuleDTO)))
            .andExpect(status().isCreated());

        // Validate the Formule in the database
        List<Formule> formuleList = formuleRepository.findAll();
        assertThat(formuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormule() throws Exception {
        // Initialize the database
        formuleRepository.saveAndFlush(formule);
        int databaseSizeBeforeDelete = formuleRepository.findAll().size();

        // Get the formule
        restFormuleMockMvc.perform(delete("/api/formules/{id}", formule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Formule> formuleList = formuleRepository.findAll();
        assertThat(formuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formule.class);
        Formule formule1 = new Formule();
        formule1.setId(1L);
        Formule formule2 = new Formule();
        formule2.setId(formule1.getId());
        assertThat(formule1).isEqualTo(formule2);
        formule2.setId(2L);
        assertThat(formule1).isNotEqualTo(formule2);
        formule1.setId(null);
        assertThat(formule1).isNotEqualTo(formule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormuleDTO.class);
        FormuleDTO formuleDTO1 = new FormuleDTO();
        formuleDTO1.setId(1L);
        FormuleDTO formuleDTO2 = new FormuleDTO();
        assertThat(formuleDTO1).isNotEqualTo(formuleDTO2);
        formuleDTO2.setId(formuleDTO1.getId());
        assertThat(formuleDTO1).isEqualTo(formuleDTO2);
        formuleDTO2.setId(2L);
        assertThat(formuleDTO1).isNotEqualTo(formuleDTO2);
        formuleDTO1.setId(null);
        assertThat(formuleDTO1).isNotEqualTo(formuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(formuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(formuleMapper.fromId(null)).isNull();
    }
}
