package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.TicketReserve;
import com.itgstore.cineclub.repository.TicketReserveRepository;
import com.itgstore.cineclub.service.TicketReserveService;
import com.itgstore.cineclub.service.dto.TicketReserveDTO;
import com.itgstore.cineclub.service.mapper.TicketReserveMapper;
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
 * Test class for the TicketReserveResource REST controller.
 *
 * @see TicketReserveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class TicketReserveResourceIntTest {

    private static final String DEFAULT_CODE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PLACE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Boolean DEFAULT_STATUT_TICKET = false;
    private static final Boolean UPDATED_STATUT_TICKET = true;

    private static final TypePlace DEFAULT_TYPE_PLACE = TypePlace.BALCON;
    private static final TypePlace UPDATED_TYPE_PLACE = TypePlace.CLASSIQUE;

    private static final String DEFAULT_CODE_SUP = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SUP = "BBBBBBBBBB";

    @Autowired
    private TicketReserveRepository ticketReserveRepository;

    @Autowired
    private TicketReserveMapper ticketReserveMapper;

    @Autowired
    private TicketReserveService ticketReserveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTicketReserveMockMvc;

    private TicketReserve ticketReserve;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TicketReserveResource ticketReserveResource = new TicketReserveResource(ticketReserveService);
        this.restTicketReserveMockMvc = MockMvcBuilders.standaloneSetup(ticketReserveResource)
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
    public static TicketReserve createEntity(EntityManager em) {
        TicketReserve ticketReserve = new TicketReserve()
            .codePaiement(DEFAULT_CODE_PAIEMENT)
            .numeroPlace(DEFAULT_NUMERO_PLACE)
            .prix(DEFAULT_PRIX)
            .statutTicket(DEFAULT_STATUT_TICKET)
            .typePlace(DEFAULT_TYPE_PLACE)
            .codeSup(DEFAULT_CODE_SUP);
        return ticketReserve;
    }

    @Before
    public void initTest() {
        ticketReserve = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicketReserve() throws Exception {
        int databaseSizeBeforeCreate = ticketReserveRepository.findAll().size();

        // Create the TicketReserve
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);
        restTicketReserveMockMvc.perform(post("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketReserve in the database
        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeCreate + 1);
        TicketReserve testTicketReserve = ticketReserveList.get(ticketReserveList.size() - 1);
        assertThat(testTicketReserve.getCodePaiement()).isEqualTo(DEFAULT_CODE_PAIEMENT);
        assertThat(testTicketReserve.getNumeroPlace()).isEqualTo(DEFAULT_NUMERO_PLACE);
        assertThat(testTicketReserve.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testTicketReserve.isStatutTicket()).isEqualTo(DEFAULT_STATUT_TICKET);
        assertThat(testTicketReserve.getTypePlace()).isEqualTo(DEFAULT_TYPE_PLACE);
        assertThat(testTicketReserve.getCodeSup()).isEqualTo(DEFAULT_CODE_SUP);
    }

    @Test
    @Transactional
    public void createTicketReserveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketReserveRepository.findAll().size();

        // Create the TicketReserve with an existing ID
        ticketReserve.setId(1L);
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketReserveMockMvc.perform(post("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketReserve in the database
        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodePaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketReserveRepository.findAll().size();
        // set the field null
        ticketReserve.setCodePaiement(null);

        // Create the TicketReserve, which fails.
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);

        restTicketReserveMockMvc.perform(post("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isBadRequest());

        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketReserveRepository.findAll().size();
        // set the field null
        ticketReserve.setNumeroPlace(null);

        // Create the TicketReserve, which fails.
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);

        restTicketReserveMockMvc.perform(post("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isBadRequest());

        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketReserveRepository.findAll().size();
        // set the field null
        ticketReserve.setPrix(null);

        // Create the TicketReserve, which fails.
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);

        restTicketReserveMockMvc.perform(post("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isBadRequest());

        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTicketReserves() throws Exception {
        // Initialize the database
        ticketReserveRepository.saveAndFlush(ticketReserve);

        // Get all the ticketReserveList
        restTicketReserveMockMvc.perform(get("/api/ticket-reserves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketReserve.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePaiement").value(hasItem(DEFAULT_CODE_PAIEMENT.toString())))
            .andExpect(jsonPath("$.[*].numeroPlace").value(hasItem(DEFAULT_NUMERO_PLACE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].statutTicket").value(hasItem(DEFAULT_STATUT_TICKET.booleanValue())))
            .andExpect(jsonPath("$.[*].typePlace").value(hasItem(DEFAULT_TYPE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].codeSup").value(hasItem(DEFAULT_CODE_SUP.toString())));
    }

    @Test
    @Transactional
    public void getTicketReserve() throws Exception {
        // Initialize the database
        ticketReserveRepository.saveAndFlush(ticketReserve);

        // Get the ticketReserve
        restTicketReserveMockMvc.perform(get("/api/ticket-reserves/{id}", ticketReserve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticketReserve.getId().intValue()))
            .andExpect(jsonPath("$.codePaiement").value(DEFAULT_CODE_PAIEMENT.toString()))
            .andExpect(jsonPath("$.numeroPlace").value(DEFAULT_NUMERO_PLACE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.statutTicket").value(DEFAULT_STATUT_TICKET.booleanValue()))
            .andExpect(jsonPath("$.typePlace").value(DEFAULT_TYPE_PLACE.toString()))
            .andExpect(jsonPath("$.codeSup").value(DEFAULT_CODE_SUP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTicketReserve() throws Exception {
        // Get the ticketReserve
        restTicketReserveMockMvc.perform(get("/api/ticket-reserves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicketReserve() throws Exception {
        // Initialize the database
        ticketReserveRepository.saveAndFlush(ticketReserve);
        int databaseSizeBeforeUpdate = ticketReserveRepository.findAll().size();

        // Update the ticketReserve
        TicketReserve updatedTicketReserve = ticketReserveRepository.findOne(ticketReserve.getId());
        // Disconnect from session so that the updates on updatedTicketReserve are not directly saved in db
        em.detach(updatedTicketReserve);
        updatedTicketReserve
            .codePaiement(UPDATED_CODE_PAIEMENT)
            .numeroPlace(UPDATED_NUMERO_PLACE)
            .prix(UPDATED_PRIX)
            .statutTicket(UPDATED_STATUT_TICKET)
            .typePlace(UPDATED_TYPE_PLACE)
            .codeSup(UPDATED_CODE_SUP);
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(updatedTicketReserve);

        restTicketReserveMockMvc.perform(put("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isOk());

        // Validate the TicketReserve in the database
        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeUpdate);
        TicketReserve testTicketReserve = ticketReserveList.get(ticketReserveList.size() - 1);
        assertThat(testTicketReserve.getCodePaiement()).isEqualTo(UPDATED_CODE_PAIEMENT);
        assertThat(testTicketReserve.getNumeroPlace()).isEqualTo(UPDATED_NUMERO_PLACE);
        assertThat(testTicketReserve.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testTicketReserve.isStatutTicket()).isEqualTo(UPDATED_STATUT_TICKET);
        assertThat(testTicketReserve.getTypePlace()).isEqualTo(UPDATED_TYPE_PLACE);
        assertThat(testTicketReserve.getCodeSup()).isEqualTo(UPDATED_CODE_SUP);
    }

    @Test
    @Transactional
    public void updateNonExistingTicketReserve() throws Exception {
        int databaseSizeBeforeUpdate = ticketReserveRepository.findAll().size();

        // Create the TicketReserve
        TicketReserveDTO ticketReserveDTO = ticketReserveMapper.toDto(ticketReserve);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketReserveMockMvc.perform(put("/api/ticket-reserves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketReserveDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketReserve in the database
        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicketReserve() throws Exception {
        // Initialize the database
        ticketReserveRepository.saveAndFlush(ticketReserve);
        int databaseSizeBeforeDelete = ticketReserveRepository.findAll().size();

        // Get the ticketReserve
        restTicketReserveMockMvc.perform(delete("/api/ticket-reserves/{id}", ticketReserve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TicketReserve> ticketReserveList = ticketReserveRepository.findAll();
        assertThat(ticketReserveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketReserve.class);
        TicketReserve ticketReserve1 = new TicketReserve();
        ticketReserve1.setId(1L);
        TicketReserve ticketReserve2 = new TicketReserve();
        ticketReserve2.setId(ticketReserve1.getId());
        assertThat(ticketReserve1).isEqualTo(ticketReserve2);
        ticketReserve2.setId(2L);
        assertThat(ticketReserve1).isNotEqualTo(ticketReserve2);
        ticketReserve1.setId(null);
        assertThat(ticketReserve1).isNotEqualTo(ticketReserve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketReserveDTO.class);
        TicketReserveDTO ticketReserveDTO1 = new TicketReserveDTO();
        ticketReserveDTO1.setId(1L);
        TicketReserveDTO ticketReserveDTO2 = new TicketReserveDTO();
        assertThat(ticketReserveDTO1).isNotEqualTo(ticketReserveDTO2);
        ticketReserveDTO2.setId(ticketReserveDTO1.getId());
        assertThat(ticketReserveDTO1).isEqualTo(ticketReserveDTO2);
        ticketReserveDTO2.setId(2L);
        assertThat(ticketReserveDTO1).isNotEqualTo(ticketReserveDTO2);
        ticketReserveDTO1.setId(null);
        assertThat(ticketReserveDTO1).isNotEqualTo(ticketReserveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ticketReserveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ticketReserveMapper.fromId(null)).isNull();
    }
}
