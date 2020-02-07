package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Cinema;
import com.itgstore.cineclub.repository.CinemaRepository;
import com.itgstore.cineclub.service.CinemaService;
import com.itgstore.cineclub.service.dto.CinemaDTO;
import com.itgstore.cineclub.service.mapper.CinemaMapper;
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
 * Test class for the CinemaResource REST controller.
 *
 * @see CinemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class CinemaResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROPRIETAIRE = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_LIENS_WATHSAPP = "AAAAAAAAAA";
    private static final String UPDATED_LIENS_WATHSAPP = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_YOUTUBE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_YOUTUBE = "BBBBBBBBBB";

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCinemaMockMvc;

    private Cinema cinema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CinemaResource cinemaResource = new CinemaResource(cinemaService);
        this.restCinemaMockMvc = MockMvcBuilders.standaloneSetup(cinemaResource)
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
    public static Cinema createEntity(EntityManager em) {
        Cinema cinema = new Cinema()
            .libele(DEFAULT_LIBELE)
            .description(DEFAULT_DESCRIPTION)
            .proprietaire(DEFAULT_PROPRIETAIRE)
            .lienFacebook(DEFAULT_LIEN_FACEBOOK)
            .liensWathsapp(DEFAULT_LIENS_WATHSAPP)
            .lienYoutube(DEFAULT_LIEN_YOUTUBE);
        return cinema;
    }

    @Before
    public void initTest() {
        cinema = createEntity(em);
    }

    @Test
    @Transactional
    public void createCinema() throws Exception {
        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);
        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate + 1);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testCinema.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCinema.getProprietaire()).isEqualTo(DEFAULT_PROPRIETAIRE);
        assertThat(testCinema.getLienFacebook()).isEqualTo(DEFAULT_LIEN_FACEBOOK);
        assertThat(testCinema.getLiensWathsapp()).isEqualTo(DEFAULT_LIENS_WATHSAPP);
        assertThat(testCinema.getLienYoutube()).isEqualTo(DEFAULT_LIEN_YOUTUBE);
    }

    @Test
    @Transactional
    public void createCinemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();

        // Create the Cinema with an existing ID
        cinema.setId(1L);
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibeleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cinemaRepository.findAll().size();
        // set the field null
        cinema.setLibele(null);

        // Create the Cinema, which fails.
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isBadRequest());

        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCinemas() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get all the cinemaList
        restCinemaMockMvc.perform(get("/api/cinemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinema.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].proprietaire").value(hasItem(DEFAULT_PROPRIETAIRE.toString())))
            .andExpect(jsonPath("$.[*].lienFacebook").value(hasItem(DEFAULT_LIEN_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].liensWathsapp").value(hasItem(DEFAULT_LIENS_WATHSAPP.toString())))
            .andExpect(jsonPath("$.[*].lienYoutube").value(hasItem(DEFAULT_LIEN_YOUTUBE.toString())));
    }

    @Test
    @Transactional
    public void getCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get the cinema
        restCinemaMockMvc.perform(get("/api/cinemas/{id}", cinema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cinema.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.proprietaire").value(DEFAULT_PROPRIETAIRE.toString()))
            .andExpect(jsonPath("$.lienFacebook").value(DEFAULT_LIEN_FACEBOOK.toString()))
            .andExpect(jsonPath("$.liensWathsapp").value(DEFAULT_LIENS_WATHSAPP.toString()))
            .andExpect(jsonPath("$.lienYoutube").value(DEFAULT_LIEN_YOUTUBE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCinema() throws Exception {
        // Get the cinema
        restCinemaMockMvc.perform(get("/api/cinemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Update the cinema
        Cinema updatedCinema = cinemaRepository.findOne(cinema.getId());
        // Disconnect from session so that the updates on updatedCinema are not directly saved in db
        em.detach(updatedCinema);
        updatedCinema
            .libele(UPDATED_LIBELE)
            .description(UPDATED_DESCRIPTION)
            .proprietaire(UPDATED_PROPRIETAIRE)
            .lienFacebook(UPDATED_LIEN_FACEBOOK)
            .liensWathsapp(UPDATED_LIENS_WATHSAPP)
            .lienYoutube(UPDATED_LIEN_YOUTUBE);
        CinemaDTO cinemaDTO = cinemaMapper.toDto(updatedCinema);

        restCinemaMockMvc.perform(put("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isOk());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testCinema.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCinema.getProprietaire()).isEqualTo(UPDATED_PROPRIETAIRE);
        assertThat(testCinema.getLienFacebook()).isEqualTo(UPDATED_LIEN_FACEBOOK);
        assertThat(testCinema.getLiensWathsapp()).isEqualTo(UPDATED_LIENS_WATHSAPP);
        assertThat(testCinema.getLienYoutube()).isEqualTo(UPDATED_LIEN_YOUTUBE);
    }

    @Test
    @Transactional
    public void updateNonExistingCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Create the Cinema
        CinemaDTO cinemaDTO = cinemaMapper.toDto(cinema);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCinemaMockMvc.perform(put("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinemaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);
        int databaseSizeBeforeDelete = cinemaRepository.findAll().size();

        // Get the cinema
        restCinemaMockMvc.perform(delete("/api/cinemas/{id}", cinema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cinema.class);
        Cinema cinema1 = new Cinema();
        cinema1.setId(1L);
        Cinema cinema2 = new Cinema();
        cinema2.setId(cinema1.getId());
        assertThat(cinema1).isEqualTo(cinema2);
        cinema2.setId(2L);
        assertThat(cinema1).isNotEqualTo(cinema2);
        cinema1.setId(null);
        assertThat(cinema1).isNotEqualTo(cinema2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CinemaDTO.class);
        CinemaDTO cinemaDTO1 = new CinemaDTO();
        cinemaDTO1.setId(1L);
        CinemaDTO cinemaDTO2 = new CinemaDTO();
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
        cinemaDTO2.setId(cinemaDTO1.getId());
        assertThat(cinemaDTO1).isEqualTo(cinemaDTO2);
        cinemaDTO2.setId(2L);
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
        cinemaDTO1.setId(null);
        assertThat(cinemaDTO1).isNotEqualTo(cinemaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cinemaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cinemaMapper.fromId(null)).isNull();
    }
}
