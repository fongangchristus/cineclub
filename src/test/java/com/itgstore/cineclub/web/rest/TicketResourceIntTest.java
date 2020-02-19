package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Ticket;
import com.itgstore.cineclub.repository.TicketRepository;
import com.itgstore.cineclub.service.TicketService;
import com.itgstore.cineclub.service.dto.TicketDTO;
import com.itgstore.cineclub.service.mapper.TicketMapper;
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
 * Test class for the TicketResource REST controller.
 *
 * @see TicketResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class TicketResourceIntTest {

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final Boolean DEFAULT_STATUT_DISPONIBILITE = false;
    private static final Boolean UPDATED_STATUT_DISPONIBILITE = true;

    private static final TypePlace DEFAULT_TYPE_PLACE = TypePlace.BALCON;
    private static final TypePlace UPDATED_TYPE_PLACE = TypePlace.CLASSIQUE;

    private static final Double DEFAULT_NOMBRE_TICKET_TOTAL = 1D;
    private static final Double UPDATED_NOMBRE_TICKET_TOTAL = 2D;

    private static final Double DEFAULT_NOMBRE_TICKET_RESTANT = 1D;
    private static final Double UPDATED_NOMBRE_TICKET_RESTANT = 2D;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTicketMockMvc;

    private Ticket ticket;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TicketResource ticketResource = new TicketResource(ticketService);
        this.restTicketMockMvc = MockMvcBuilders.standaloneSetup(ticketResource)
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
    public static Ticket createEntity(EntityManager em) {
        Ticket ticket = new Ticket()
            .prix(DEFAULT_PRIX)
            .statutDisponibilite(DEFAULT_STATUT_DISPONIBILITE)
            .typePlace(DEFAULT_TYPE_PLACE)
            .nombreTicketTotal(DEFAULT_NOMBRE_TICKET_TOTAL)
            .nombreTicketRestant(DEFAULT_NOMBRE_TICKET_RESTANT);
        return ticket;
    }

    @Before
    public void initTest() {
        ticket = createEntity(em);
    }

    @Test
    @Transactional
    public void createTicket() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);
        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate + 1);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testTicket.isStatutDisponibilite()).isEqualTo(DEFAULT_STATUT_DISPONIBILITE);
        assertThat(testTicket.getTypePlace()).isEqualTo(DEFAULT_TYPE_PLACE);
        assertThat(testTicket.getNombreTicketTotal()).isEqualTo(DEFAULT_NOMBRE_TICKET_TOTAL);
        assertThat(testTicket.getNombreTicketRestant()).isEqualTo(DEFAULT_NOMBRE_TICKET_RESTANT);
    }

    @Test
    @Transactional
    public void createTicketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketRepository.findAll().size();

        // Create the Ticket with an existing ID
        ticket.setId(1L);
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = ticketRepository.findAll().size();
        // set the field null
        ticket.setPrix(null);

        // Create the Ticket, which fails.
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        restTicketMockMvc.perform(post("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isBadRequest());

        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get all the ticketList
        restTicketMockMvc.perform(get("/api/tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticket.getId().intValue())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].statutDisponibilite").value(hasItem(DEFAULT_STATUT_DISPONIBILITE.booleanValue())))
            .andExpect(jsonPath("$.[*].typePlace").value(hasItem(DEFAULT_TYPE_PLACE.toString())))
            .andExpect(jsonPath("$.[*].nombreTicketTotal").value(hasItem(DEFAULT_NOMBRE_TICKET_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreTicketRestant").value(hasItem(DEFAULT_NOMBRE_TICKET_RESTANT.doubleValue())));
    }

    @Test
    @Transactional
    public void getTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);

        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", ticket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ticket.getId().intValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.statutDisponibilite").value(DEFAULT_STATUT_DISPONIBILITE.booleanValue()))
            .andExpect(jsonPath("$.typePlace").value(DEFAULT_TYPE_PLACE.toString()))
            .andExpect(jsonPath("$.nombreTicketTotal").value(DEFAULT_NOMBRE_TICKET_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.nombreTicketRestant").value(DEFAULT_NOMBRE_TICKET_RESTANT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTicket() throws Exception {
        // Get the ticket
        restTicketMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Update the ticket
        Ticket updatedTicket = ticketRepository.findOne(ticket.getId());
        // Disconnect from session so that the updates on updatedTicket are not directly saved in db
        em.detach(updatedTicket);
        updatedTicket
            .prix(UPDATED_PRIX)
            .statutDisponibilite(UPDATED_STATUT_DISPONIBILITE)
            .typePlace(UPDATED_TYPE_PLACE)
            .nombreTicketTotal(UPDATED_NOMBRE_TICKET_TOTAL)
            .nombreTicketRestant(UPDATED_NOMBRE_TICKET_RESTANT);
        TicketDTO ticketDTO = ticketMapper.toDto(updatedTicket);

        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isOk());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate);
        Ticket testTicket = ticketList.get(ticketList.size() - 1);
        assertThat(testTicket.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testTicket.isStatutDisponibilite()).isEqualTo(UPDATED_STATUT_DISPONIBILITE);
        assertThat(testTicket.getTypePlace()).isEqualTo(UPDATED_TYPE_PLACE);
        assertThat(testTicket.getNombreTicketTotal()).isEqualTo(UPDATED_NOMBRE_TICKET_TOTAL);
        assertThat(testTicket.getNombreTicketRestant()).isEqualTo(UPDATED_NOMBRE_TICKET_RESTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingTicket() throws Exception {
        int databaseSizeBeforeUpdate = ticketRepository.findAll().size();

        // Create the Ticket
        TicketDTO ticketDTO = ticketMapper.toDto(ticket);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTicketMockMvc.perform(put("/api/tickets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ticketDTO)))
            .andExpect(status().isCreated());

        // Validate the Ticket in the database
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTicket() throws Exception {
        // Initialize the database
        ticketRepository.saveAndFlush(ticket);
        int databaseSizeBeforeDelete = ticketRepository.findAll().size();

        // Get the ticket
        restTicketMockMvc.perform(delete("/api/tickets/{id}", ticket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ticket> ticketList = ticketRepository.findAll();
        assertThat(ticketList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ticket.class);
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(ticket1.getId());
        assertThat(ticket1).isEqualTo(ticket2);
        ticket2.setId(2L);
        assertThat(ticket1).isNotEqualTo(ticket2);
        ticket1.setId(null);
        assertThat(ticket1).isNotEqualTo(ticket2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketDTO.class);
        TicketDTO ticketDTO1 = new TicketDTO();
        ticketDTO1.setId(1L);
        TicketDTO ticketDTO2 = new TicketDTO();
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO2.setId(ticketDTO1.getId());
        assertThat(ticketDTO1).isEqualTo(ticketDTO2);
        ticketDTO2.setId(2L);
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
        ticketDTO1.setId(null);
        assertThat(ticketDTO1).isNotEqualTo(ticketDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ticketMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ticketMapper.fromId(null)).isNull();
    }
}
