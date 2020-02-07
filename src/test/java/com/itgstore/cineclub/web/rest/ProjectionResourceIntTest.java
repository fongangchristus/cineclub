package com.itgstore.cineclub.web.rest;

import com.itgstore.cineclub.CineclubApp;

import com.itgstore.cineclub.domain.Projection;
import com.itgstore.cineclub.repository.ProjectionRepository;
import com.itgstore.cineclub.service.ProjectionService;
import com.itgstore.cineclub.service.dto.ProjectionDTO;
import com.itgstore.cineclub.service.mapper.ProjectionMapper;
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

import com.itgstore.cineclub.domain.enumeration.Jour;
import com.itgstore.cineclub.domain.enumeration.TypeProjection;
/**
 * Test class for the ProjectionResource REST controller.
 *
 * @see ProjectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class ProjectionResourceIntTest {

    private static final String DEFAULT_LIBELE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELE = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Jour DEFAULT_JOUR = Jour.LUNDI;
    private static final Jour UPDATED_JOUR = Jour.MARDI;

    private static final TypeProjection DEFAULT_TYPE_PROJECTION = TypeProjection.AVANT_PREMIERE;
    private static final TypeProjection UPDATED_TYPE_PROJECTION = TypeProjection.CLASSIQUE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private ProjectionMapper projectionMapper;

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectionMockMvc;

    private Projection projection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectionResource projectionResource = new ProjectionResource(projectionService);
        this.restProjectionMockMvc = MockMvcBuilders.standaloneSetup(projectionResource)
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
    public static Projection createEntity(EntityManager em) {
        Projection projection = new Projection()
            .libele(DEFAULT_LIBELE)
            .duree(DEFAULT_DUREE)
            .code(DEFAULT_CODE)
            .jour(DEFAULT_JOUR)
            .typeProjection(DEFAULT_TYPE_PROJECTION)
            .description(DEFAULT_DESCRIPTION);
        return projection;
    }

    @Before
    public void initTest() {
        projection = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjection() throws Exception {
        int databaseSizeBeforeCreate = projectionRepository.findAll().size();

        // Create the Projection
        ProjectionDTO projectionDTO = projectionMapper.toDto(projection);
        restProjectionMockMvc.perform(post("/api/projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Projection in the database
        List<Projection> projectionList = projectionRepository.findAll();
        assertThat(projectionList).hasSize(databaseSizeBeforeCreate + 1);
        Projection testProjection = projectionList.get(projectionList.size() - 1);
        assertThat(testProjection.getLibele()).isEqualTo(DEFAULT_LIBELE);
        assertThat(testProjection.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testProjection.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjection.getJour()).isEqualTo(DEFAULT_JOUR);
        assertThat(testProjection.getTypeProjection()).isEqualTo(DEFAULT_TYPE_PROJECTION);
        assertThat(testProjection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectionRepository.findAll().size();

        // Create the Projection with an existing ID
        projection.setId(1L);
        ProjectionDTO projectionDTO = projectionMapper.toDto(projection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectionMockMvc.perform(post("/api/projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projection in the database
        List<Projection> projectionList = projectionRepository.findAll();
        assertThat(projectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjections() throws Exception {
        // Initialize the database
        projectionRepository.saveAndFlush(projection);

        // Get all the projectionList
        restProjectionMockMvc.perform(get("/api/projections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projection.getId().intValue())))
            .andExpect(jsonPath("$.[*].libele").value(hasItem(DEFAULT_LIBELE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].jour").value(hasItem(DEFAULT_JOUR.toString())))
            .andExpect(jsonPath("$.[*].typeProjection").value(hasItem(DEFAULT_TYPE_PROJECTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProjection() throws Exception {
        // Initialize the database
        projectionRepository.saveAndFlush(projection);

        // Get the projection
        restProjectionMockMvc.perform(get("/api/projections/{id}", projection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projection.getId().intValue()))
            .andExpect(jsonPath("$.libele").value(DEFAULT_LIBELE.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.jour").value(DEFAULT_JOUR.toString()))
            .andExpect(jsonPath("$.typeProjection").value(DEFAULT_TYPE_PROJECTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjection() throws Exception {
        // Get the projection
        restProjectionMockMvc.perform(get("/api/projections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjection() throws Exception {
        // Initialize the database
        projectionRepository.saveAndFlush(projection);
        int databaseSizeBeforeUpdate = projectionRepository.findAll().size();

        // Update the projection
        Projection updatedProjection = projectionRepository.findOne(projection.getId());
        // Disconnect from session so that the updates on updatedProjection are not directly saved in db
        em.detach(updatedProjection);
        updatedProjection
            .libele(UPDATED_LIBELE)
            .duree(UPDATED_DUREE)
            .code(UPDATED_CODE)
            .jour(UPDATED_JOUR)
            .typeProjection(UPDATED_TYPE_PROJECTION)
            .description(UPDATED_DESCRIPTION);
        ProjectionDTO projectionDTO = projectionMapper.toDto(updatedProjection);

        restProjectionMockMvc.perform(put("/api/projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectionDTO)))
            .andExpect(status().isOk());

        // Validate the Projection in the database
        List<Projection> projectionList = projectionRepository.findAll();
        assertThat(projectionList).hasSize(databaseSizeBeforeUpdate);
        Projection testProjection = projectionList.get(projectionList.size() - 1);
        assertThat(testProjection.getLibele()).isEqualTo(UPDATED_LIBELE);
        assertThat(testProjection.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testProjection.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjection.getJour()).isEqualTo(UPDATED_JOUR);
        assertThat(testProjection.getTypeProjection()).isEqualTo(UPDATED_TYPE_PROJECTION);
        assertThat(testProjection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjection() throws Exception {
        int databaseSizeBeforeUpdate = projectionRepository.findAll().size();

        // Create the Projection
        ProjectionDTO projectionDTO = projectionMapper.toDto(projection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectionMockMvc.perform(put("/api/projections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Projection in the database
        List<Projection> projectionList = projectionRepository.findAll();
        assertThat(projectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjection() throws Exception {
        // Initialize the database
        projectionRepository.saveAndFlush(projection);
        int databaseSizeBeforeDelete = projectionRepository.findAll().size();

        // Get the projection
        restProjectionMockMvc.perform(delete("/api/projections/{id}", projection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projection> projectionList = projectionRepository.findAll();
        assertThat(projectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projection.class);
        Projection projection1 = new Projection();
        projection1.setId(1L);
        Projection projection2 = new Projection();
        projection2.setId(projection1.getId());
        assertThat(projection1).isEqualTo(projection2);
        projection2.setId(2L);
        assertThat(projection1).isNotEqualTo(projection2);
        projection1.setId(null);
        assertThat(projection1).isNotEqualTo(projection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectionDTO.class);
        ProjectionDTO projectionDTO1 = new ProjectionDTO();
        projectionDTO1.setId(1L);
        ProjectionDTO projectionDTO2 = new ProjectionDTO();
        assertThat(projectionDTO1).isNotEqualTo(projectionDTO2);
        projectionDTO2.setId(projectionDTO1.getId());
        assertThat(projectionDTO1).isEqualTo(projectionDTO2);
        projectionDTO2.setId(2L);
        assertThat(projectionDTO1).isNotEqualTo(projectionDTO2);
        projectionDTO1.setId(null);
        assertThat(projectionDTO1).isNotEqualTo(projectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(projectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(projectionMapper.fromId(null)).isNull();
    }
}
