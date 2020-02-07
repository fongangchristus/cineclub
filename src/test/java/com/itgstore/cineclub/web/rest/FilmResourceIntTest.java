package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Film;
import com.itgstore.cineclub.repository.FilmRepository;
import com.itgstore.cineclub.service.FilmService;
import com.itgstore.cineclub.service.dto.FilmDTO;
import com.itgstore.cineclub.service.mapper.FilmMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the FilmResource REST controller.
 *
 * @see FilmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class FilmResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_COUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_PATH_COUVERTURE = "BBBBBBBBBB";

    private static final String DEFAULT_BANDE_ANNONCE = "AAAAAAAAAA";
    private static final String UPDATED_BANDE_ANNONCE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COUVERTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COUVERTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COUVERTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COUVERTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final String DEFAULT_REALISATEUR = "AAAAAAAAAA";
    private static final String UPDATED_REALISATEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilmMockMvc;

    private Film film;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilmResource filmResource = new FilmResource(filmService);
        this.restFilmMockMvc = MockMvcBuilders.standaloneSetup(filmResource)
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
    public static Film createEntity(EntityManager em) {
        Film film = new Film()
            .titre(DEFAULT_TITRE)
            .pathCouverture(DEFAULT_PATH_COUVERTURE)
            .bandeAnnonce(DEFAULT_BANDE_ANNONCE)
            .couverture(DEFAULT_COUVERTURE)
            .couvertureContentType(DEFAULT_COUVERTURE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .resume(DEFAULT_RESUME)
            .duree(DEFAULT_DUREE)
            .realisateur(DEFAULT_REALISATEUR)
            .dateSortie(DEFAULT_DATE_SORTIE);
        return film;
    }

    @Before
    public void initTest() {
        film = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilm() throws Exception {
        int databaseSizeBeforeCreate = filmRepository.findAll().size();

        // Create the Film
        FilmDTO filmDTO = filmMapper.toDto(film);
        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isCreated());

        // Validate the Film in the database
        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeCreate + 1);
        Film testFilm = filmList.get(filmList.size() - 1);
        assertThat(testFilm.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testFilm.getPathCouverture()).isEqualTo(DEFAULT_PATH_COUVERTURE);
        assertThat(testFilm.getBandeAnnonce()).isEqualTo(DEFAULT_BANDE_ANNONCE);
        assertThat(testFilm.getCouverture()).isEqualTo(DEFAULT_COUVERTURE);
        assertThat(testFilm.getCouvertureContentType()).isEqualTo(DEFAULT_COUVERTURE_CONTENT_TYPE);
        assertThat(testFilm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFilm.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testFilm.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testFilm.getRealisateur()).isEqualTo(DEFAULT_REALISATEUR);
        assertThat(testFilm.getDateSortie()).isEqualTo(DEFAULT_DATE_SORTIE);
    }

    @Test
    @Transactional
    public void createFilmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filmRepository.findAll().size();

        // Create the Film with an existing ID
        film.setId(1L);
        FilmDTO filmDTO = filmMapper.toDto(film);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Film in the database
        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setTitre(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.toDto(film);

        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathCouvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setPathCouverture(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.toDto(film);

        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setDescription(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.toDto(film);

        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDureeIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setDuree(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.toDto(film);

        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRealisateurIsRequired() throws Exception {
        int databaseSizeBeforeTest = filmRepository.findAll().size();
        // set the field null
        film.setRealisateur(null);

        // Create the Film, which fails.
        FilmDTO filmDTO = filmMapper.toDto(film);

        restFilmMockMvc.perform(post("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isBadRequest());

        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilms() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);

        // Get all the filmList
        restFilmMockMvc.perform(get("/api/films?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(film.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].pathCouverture").value(hasItem(DEFAULT_PATH_COUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].bandeAnnonce").value(hasItem(DEFAULT_BANDE_ANNONCE.toString())))
            .andExpect(jsonPath("$.[*].couvertureContentType").value(hasItem(DEFAULT_COUVERTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].couverture").value(hasItem(Base64Utils.encodeToString(DEFAULT_COUVERTURE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())))
            .andExpect(jsonPath("$.[*].realisateur").value(hasItem(DEFAULT_REALISATEUR.toString())))
            .andExpect(jsonPath("$.[*].dateSortie").value(hasItem(DEFAULT_DATE_SORTIE.toString())));
    }

    @Test
    @Transactional
    public void getFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);

        // Get the film
        restFilmMockMvc.perform(get("/api/films/{id}", film.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(film.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.pathCouverture").value(DEFAULT_PATH_COUVERTURE.toString()))
            .andExpect(jsonPath("$.bandeAnnonce").value(DEFAULT_BANDE_ANNONCE.toString()))
            .andExpect(jsonPath("$.couvertureContentType").value(DEFAULT_COUVERTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.couverture").value(Base64Utils.encodeToString(DEFAULT_COUVERTURE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.toString()))
            .andExpect(jsonPath("$.realisateur").value(DEFAULT_REALISATEUR.toString()))
            .andExpect(jsonPath("$.dateSortie").value(DEFAULT_DATE_SORTIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilm() throws Exception {
        // Get the film
        restFilmMockMvc.perform(get("/api/films/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);
        int databaseSizeBeforeUpdate = filmRepository.findAll().size();

        // Update the film
        Film updatedFilm = filmRepository.findOne(film.getId());
        // Disconnect from session so that the updates on updatedFilm are not directly saved in db
        em.detach(updatedFilm);
        updatedFilm
            .titre(UPDATED_TITRE)
            .pathCouverture(UPDATED_PATH_COUVERTURE)
            .bandeAnnonce(UPDATED_BANDE_ANNONCE)
            .couverture(UPDATED_COUVERTURE)
            .couvertureContentType(UPDATED_COUVERTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .resume(UPDATED_RESUME)
            .duree(UPDATED_DUREE)
            .realisateur(UPDATED_REALISATEUR)
            .dateSortie(UPDATED_DATE_SORTIE);
        FilmDTO filmDTO = filmMapper.toDto(updatedFilm);

        restFilmMockMvc.perform(put("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isOk());

        // Validate the Film in the database
        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeUpdate);
        Film testFilm = filmList.get(filmList.size() - 1);
        assertThat(testFilm.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testFilm.getPathCouverture()).isEqualTo(UPDATED_PATH_COUVERTURE);
        assertThat(testFilm.getBandeAnnonce()).isEqualTo(UPDATED_BANDE_ANNONCE);
        assertThat(testFilm.getCouverture()).isEqualTo(UPDATED_COUVERTURE);
        assertThat(testFilm.getCouvertureContentType()).isEqualTo(UPDATED_COUVERTURE_CONTENT_TYPE);
        assertThat(testFilm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFilm.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testFilm.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testFilm.getRealisateur()).isEqualTo(UPDATED_REALISATEUR);
        assertThat(testFilm.getDateSortie()).isEqualTo(UPDATED_DATE_SORTIE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilm() throws Exception {
        int databaseSizeBeforeUpdate = filmRepository.findAll().size();

        // Create the Film
        FilmDTO filmDTO = filmMapper.toDto(film);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilmMockMvc.perform(put("/api/films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filmDTO)))
            .andExpect(status().isCreated());

        // Validate the Film in the database
        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilm() throws Exception {
        // Initialize the database
        filmRepository.saveAndFlush(film);
        int databaseSizeBeforeDelete = filmRepository.findAll().size();

        // Get the film
        restFilmMockMvc.perform(delete("/api/films/{id}", film.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Film> filmList = filmRepository.findAll();
        assertThat(filmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Film.class);
        Film film1 = new Film();
        film1.setId(1L);
        Film film2 = new Film();
        film2.setId(film1.getId());
        assertThat(film1).isEqualTo(film2);
        film2.setId(2L);
        assertThat(film1).isNotEqualTo(film2);
        film1.setId(null);
        assertThat(film1).isNotEqualTo(film2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilmDTO.class);
        FilmDTO filmDTO1 = new FilmDTO();
        filmDTO1.setId(1L);
        FilmDTO filmDTO2 = new FilmDTO();
        assertThat(filmDTO1).isNotEqualTo(filmDTO2);
        filmDTO2.setId(filmDTO1.getId());
        assertThat(filmDTO1).isEqualTo(filmDTO2);
        filmDTO2.setId(2L);
        assertThat(filmDTO1).isNotEqualTo(filmDTO2);
        filmDTO1.setId(null);
        assertThat(filmDTO1).isNotEqualTo(filmDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(filmMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(filmMapper.fromId(null)).isNull();
    }
}
