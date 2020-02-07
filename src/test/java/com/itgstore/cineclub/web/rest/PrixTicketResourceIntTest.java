package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.PrixTicket;
import com.itgstore.cineclub.repository.PrixTicketRepository;
import com.itgstore.cineclub.service.PrixTicketService;
import com.itgstore.cineclub.service.dto.PrixTicketDTO;
import com.itgstore.cineclub.service.mapper.PrixTicketMapper;
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

import com.itgstore.cineclub.domain.enumeration.TypePlace;
/**
 * Test class for the PrixTicketResource REST controller.
 *
 * @see PrixTicketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class PrixTicketResourceIntTest {

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final TypePlace DEFAULT_TYPE_PLACE = TypePlace.BALCON;
    private static final TypePlace UPDATED_TYPE_PLACE = TypePlace.CLASSIQUE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PrixTicketRepository prixTicketRepository;

    @Autowired
    private PrixTicketMapper prixTicketMapper;

    @Autowired
    private PrixTicketService prixTicketService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrixTicketMockMvc;

    private PrixTicket prixTicket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrixTicketResource prixTicketResource = new PrixTicketResource(prixTicketService);
        this.restPrixTicketMockMvc = MockMvcBuilders.standaloneSetup(prixTicketResource)
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
    public static PrixTicket createEntity(EntityManager em) {
        PrixTicket prixTicket = new PrixTicket()
            .prix(DEFAULT_PRIX)
            .code(DEFAULT_CODE)
            .typePlace(DEFAULT_TYPE_PLACE)
            .description(DEFAULT_DESCRIPTION);
        return prixTicket;
    }

    @Before
    public void initTest() {
        prixTicket = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrixTicket() throws Exception {
        int databaseSizeBeforeCreate = prixTicketRepository.findAll().size();

        // Create the PrixTicket
        PrixTicketDTO prixTicketDTO = prixTicketMapper.toDto(prixTicket);
        restPrixTicketMockMvc.perform(post("/api/prix-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prixTicketDTO)))
            .andExpect(status().isCreated());

        // Validate the PrixTicket in the database
        List<PrixTicket> prixTicketList = prixTicketRepository.findAll();
        assertThat(prixTicketList).hasSize(databaseSizeBeforeCreate + 1);
        PrixTicket testPrixTicket = prixTicketList.get(prixTicketList.size() - 1);
        assertThat(testPrixTicket.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testPrixTicket.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrixTicket.getTypePlace()).isEqualTo(DEFAULT_TYPE_PLACE);
        assertThat(testPrixTicket.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPrixTicketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prixTicketRepository.findAll().size();

        // Create the PrixTicket with an existing ID
        prixTicket.setId(1L);
        PrixTicketDTO prixTicketDTO = prixTicketMapper.toDto(prixTicket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrixTicketMockMvc.perform(post("/api/prix-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prixTicketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrixTicket in the database
        List<PrixTicket> prixTicketList = prixTicketRepository.findAll();
        assertThat(prixTicketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrixTickets() throws Exception {
        // Initialize the database
        prixTicketRepository.saveAndFlush(prixTicket);

        // Get all the prixTicketList
        restPrixTicketMockMvc.perform(get("/api/prix-tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prixTicket.getId().intValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].typePlace").value(hasItem(DEFAULT_TYPE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPrixTicket() throws Exception {
        // Initialize the database
        prixTicketRepository.saveAndFlush(prixTicket);

        // Get the prixTicket
        restPrixTicketMockMvc.perform(get("/api/prix-tickets/{id}", prixTicket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prixTicket.getId().intValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.typePlace").value(DEFAULT_TYPE_PLACE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrixTicket() throws Exception {
        // Get the prixTicket
        restPrixTicketMockMvc.perform(get("/api/prix-tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrixTicket() throws Exception {
        // Initialize the database
        prixTicketRepository.saveAndFlush(prixTicket);
        int databaseSizeBeforeUpdate = prixTicketRepository.findAll().size();

        // Update the prixTicket
        PrixTicket updatedPrixTicket = prixTicketRepository.findOne(prixTicket.getId());
        // Disconnect from session so that the updates on updatedPrixTicket are not directly saved in db
        em.detach(updatedPrixTicket);
        updatedPrixTicket
            .prix(UPDATED_PRIX)
            .code(UPDATED_CODE)
            .typePlace(UPDATED_TYPE_PLACE)
            .description(UPDATED_DESCRIPTION);
        PrixTicketDTO prixTicketDTO = prixTicketMapper.toDto(updatedPrixTicket);

        restPrixTicketMockMvc.perform(put("/api/prix-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prixTicketDTO)))
            .andExpect(status().isOk());

        // Validate the PrixTicket in the database
        List<PrixTicket> prixTicketList = prixTicketRepository.findAll();
        assertThat(prixTicketList).hasSize(databaseSizeBeforeUpdate);
        PrixTicket testPrixTicket = prixTicketList.get(prixTicketList.size() - 1);
        assertThat(testPrixTicket.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testPrixTicket.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrixTicket.getTypePlace()).isEqualTo(UPDATED_TYPE_PLACE);
        assertThat(testPrixTicket.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPrixTicket() throws Exception {
        int databaseSizeBeforeUpdate = prixTicketRepository.findAll().size();

        // Create the PrixTicket
        PrixTicketDTO prixTicketDTO = prixTicketMapper.toDto(prixTicket);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrixTicketMockMvc.perform(put("/api/prix-tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prixTicketDTO)))
            .andExpect(status().isCreated());

        // Validate the PrixTicket in the database
        List<PrixTicket> prixTicketList = prixTicketRepository.findAll();
        assertThat(prixTicketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrixTicket() throws Exception {
        // Initialize the database
        prixTicketRepository.saveAndFlush(prixTicket);
        int databaseSizeBeforeDelete = prixTicketRepository.findAll().size();

        // Get the prixTicket
        restPrixTicketMockMvc.perform(delete("/api/prix-tickets/{id}", prixTicket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrixTicket> prixTicketList = prixTicketRepository.findAll();
        assertThat(prixTicketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrixTicket.class);
        PrixTicket prixTicket1 = new PrixTicket();
        prixTicket1.setId(1L);
        PrixTicket prixTicket2 = new PrixTicket();
        prixTicket2.setId(prixTicket1.getId());
        assertThat(prixTicket1).isEqualTo(prixTicket2);
        prixTicket2.setId(2L);
        assertThat(prixTicket1).isNotEqualTo(prixTicket2);
        prixTicket1.setId(null);
        assertThat(prixTicket1).isNotEqualTo(prixTicket2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrixTicketDTO.class);
        PrixTicketDTO prixTicketDTO1 = new PrixTicketDTO();
        prixTicketDTO1.setId(1L);
        PrixTicketDTO prixTicketDTO2 = new PrixTicketDTO();
        assertThat(prixTicketDTO1).isNotEqualTo(prixTicketDTO2);
        prixTicketDTO2.setId(prixTicketDTO1.getId());
        assertThat(prixTicketDTO1).isEqualTo(prixTicketDTO2);
        prixTicketDTO2.setId(2L);
        assertThat(prixTicketDTO1).isNotEqualTo(prixTicketDTO2);
        prixTicketDTO1.setId(null);
        assertThat(prixTicketDTO1).isNotEqualTo(prixTicketDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prixTicketMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prixTicketMapper.fromId(null)).isNull();
    }
}
