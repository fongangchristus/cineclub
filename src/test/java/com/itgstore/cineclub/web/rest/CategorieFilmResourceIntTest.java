package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.CategorieFilm;
import com.itgstore.cineclub.repository.CategorieFilmRepository;
import com.itgstore.cineclub.service.CategorieFilmService;
import com.itgstore.cineclub.service.dto.CategorieFilmDTO;
import com.itgstore.cineclub.service.mapper.CategorieFilmMapper;
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
import java.util.List;

import static com.itgstore.cineclub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CategorieFilmResource REST controller.
 *
 * @see CategorieFilmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class CategorieFilmResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_PATH_COUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_PATH_COUVERTURE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COUVERTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COUVERTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COUVERTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COUVERTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CategorieFilmRepository categorieFilmRepository;

    @Autowired
    private CategorieFilmMapper categorieFilmMapper;

    @Autowired
    private CategorieFilmService categorieFilmService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategorieFilmMockMvc;

    private CategorieFilm categorieFilm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieFilmResource categorieFilmResource = new CategorieFilmResource(categorieFilmService);
        this.restCategorieFilmMockMvc = MockMvcBuilders.standaloneSetup(categorieFilmResource)
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
    public static CategorieFilm createEntity(EntityManager em) {
        CategorieFilm categorieFilm = new CategorieFilm()
            .libele(DEFAULT_LIBELE)
            .pathCouverture(DEFAULT_PATH_COUVERTURE)
            .couverture(DEFAULT_COUVERTURE)
            .couvertureContentType(DEFAULT_COUVERTURE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return categorieFilm;
    }

    @Before
    public void initTest() {
        categorieFilm = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieFilm() throws Exception {
        int databaseSizeBeforeCreate = categorieFilmRepository.findAll().size();

        // Create the CategorieFilm
        CategorieFilmDTO categorieFilmDTO = categorieFilmMapper.toDto(categorieFilm);
        restCategorieFilmMockMvc.perform(post("/api/categorie-films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFilmDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieFilm in the database
        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieFilm testCategorieFilm = categorieFilmList.get(categorieFilmList.size() - 1);
        assertThat(testCategorieFilm.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testCategorieFilm.getPathCouverture()).isEqualTo(DEFAULT_PATH_COUVERTURE);
        assertThat(testCategorieFilm.getCouverture()).isEqualTo(DEFAULT_COUVERTURE);
        assertThat(testCategorieFilm.getCouvertureContentType()).isEqualTo(DEFAULT_COUVERTURE_CONTENT_TYPE);
        assertThat(testCategorieFilm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCategorieFilmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieFilmRepository.findAll().size();

        // Create the CategorieFilm with an existing ID
        categorieFilm.setId(1L);
        CategorieFilmDTO categorieFilmDTO = categorieFilmMapper.toDto(categorieFilm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieFilmMockMvc.perform(post("/api/categorie-films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFilmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieFilm in the database
        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibeleIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieFilmRepository.findAll().size();
        // set the field null
        categorieFilm.setLibele(null);

        // Create the CategorieFilm, which fails.
        CategorieFilmDTO categorieFilmDTO = categorieFilmMapper.toDto(categorieFilm);

        restCategorieFilmMockMvc.perform(post("/api/categorie-films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFilmDTO)))
            .andExpect(status().isBadRequest());

        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieFilms() throws Exception {
        // Initialize the database
        categorieFilmRepository.saveAndFlush(categorieFilm);

        // Get all the categorieFilmList
        restCategorieFilmMockMvc.perform(get("/api/categorie-films?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieFilm.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].pathCouverture").value(hasItem(DEFAULT_PATH_COUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].couvertureContentType").value(hasItem(DEFAULT_COUVERTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].couverture").value(hasItem(Base64Utils.encodeToString(DEFAULT_COUVERTURE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCategorieFilm() throws Exception {
        // Initialize the database
        categorieFilmRepository.saveAndFlush(categorieFilm);

        // Get the categorieFilm
        restCategorieFilmMockMvc.perform(get("/api/categorie-films/{id}", categorieFilm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorieFilm.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.pathCouverture").value(DEFAULT_PATH_COUVERTURE.toString()))
            .andExpect(jsonPath("$.couvertureContentType").value(DEFAULT_COUVERTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.couverture").value(Base64Utils.encodeToString(DEFAULT_COUVERTURE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieFilm() throws Exception {
        // Get the categorieFilm
        restCategorieFilmMockMvc.perform(get("/api/categorie-films/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieFilm() throws Exception {
        // Initialize the database
        categorieFilmRepository.saveAndFlush(categorieFilm);
        int databaseSizeBeforeUpdate = categorieFilmRepository.findAll().size();

        // Update the categorieFilm
        CategorieFilm updatedCategorieFilm = categorieFilmRepository.findOne(categorieFilm.getId());
        // Disconnect from session so that the updates on updatedCategorieFilm are not directly saved in db
        em.detach(updatedCategorieFilm);
        updatedCategorieFilm
            .libele(UPDATED_LIBELE)
            .pathCouverture(UPDATED_PATH_COUVERTURE)
            .couverture(UPDATED_COUVERTURE)
            .couvertureContentType(UPDATED_COUVERTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        CategorieFilmDTO categorieFilmDTO = categorieFilmMapper.toDto(updatedCategorieFilm);

        restCategorieFilmMockMvc.perform(put("/api/categorie-films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFilmDTO)))
            .andExpect(status().isOk());

        // Validate the CategorieFilm in the database
        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeUpdate);
        CategorieFilm testCategorieFilm = categorieFilmList.get(categorieFilmList.size() - 1);
        assertThat(testCategorieFilm.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testCategorieFilm.getPathCouverture()).isEqualTo(UPDATED_PATH_COUVERTURE);
        assertThat(testCategorieFilm.getCouverture()).isEqualTo(UPDATED_COUVERTURE);
        assertThat(testCategorieFilm.getCouvertureContentType()).isEqualTo(UPDATED_COUVERTURE_CONTENT_TYPE);
        assertThat(testCategorieFilm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieFilm() throws Exception {
        int databaseSizeBeforeUpdate = categorieFilmRepository.findAll().size();

        // Create the CategorieFilm
        CategorieFilmDTO categorieFilmDTO = categorieFilmMapper.toDto(categorieFilm);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategorieFilmMockMvc.perform(put("/api/categorie-films")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFilmDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieFilm in the database
        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategorieFilm() throws Exception {
        // Initialize the database
        categorieFilmRepository.saveAndFlush(categorieFilm);
        int databaseSizeBeforeDelete = categorieFilmRepository.findAll().size();

        // Get the categorieFilm
        restCategorieFilmMockMvc.perform(delete("/api/categorie-films/{id}", categorieFilm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategorieFilm> categorieFilmList = categorieFilmRepository.findAll();
        assertThat(categorieFilmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieFilm.class);
        CategorieFilm categorieFilm1 = new CategorieFilm();
        categorieFilm1.setId(1L);
        CategorieFilm categorieFilm2 = new CategorieFilm();
        categorieFilm2.setId(categorieFilm1.getId());
        assertThat(categorieFilm1).isEqualTo(categorieFilm2);
        categorieFilm2.setId(2L);
        assertThat(categorieFilm1).isNotEqualTo(categorieFilm2);
        categorieFilm1.setId(null);
        assertThat(categorieFilm1).isNotEqualTo(categorieFilm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieFilmDTO.class);
        CategorieFilmDTO categorieFilmDTO1 = new CategorieFilmDTO();
        categorieFilmDTO1.setId(1L);
        CategorieFilmDTO categorieFilmDTO2 = new CategorieFilmDTO();
        assertThat(categorieFilmDTO1).isNotEqualTo(categorieFilmDTO2);
        categorieFilmDTO2.setId(categorieFilmDTO1.getId());
        assertThat(categorieFilmDTO1).isEqualTo(categorieFilmDTO2);
        categorieFilmDTO2.setId(2L);
        assertThat(categorieFilmDTO1).isNotEqualTo(categorieFilmDTO2);
        categorieFilmDTO1.setId(null);
        assertThat(categorieFilmDTO1).isNotEqualTo(categorieFilmDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categorieFilmMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categorieFilmMapper.fromId(null)).isNull();
    }
}
