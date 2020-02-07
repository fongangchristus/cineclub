package com.itgstore.cineclub.web.rest;

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

import com.itgstore.cineclub.CineclubApp;
import com.itgstore.cineclub.domain.SeqGen;
import com.itgstore.cineclub.repository.SeqGenRepository;
import com.itgstore.cineclub.web.rest.SeqGenResource;
import com.itgstore.cineclub.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itgstore.cineclub.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SeqGenResource REST controller.
 *
 * @see SeqGenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CineclubApp.class)
public class SeqGenResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_NEXTID = 1L;
    private static final Long UPDATED_NEXTID = 2L;

    @Autowired
    private SeqGenRepository seqGenRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeqGenMockMvc;

    private SeqGen seqGen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeqGenResource seqGenResource = new SeqGenResource(seqGenRepository);
        this.restSeqGenMockMvc = MockMvcBuilders.standaloneSetup(seqGenResource)
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
    public static SeqGen createEntity(EntityManager em) {
        SeqGen seqGen = new SeqGen()
            .code(DEFAULT_CODE)
            .nextid(DEFAULT_NEXTID);
        return seqGen;
    }

    @Before
    public void initTest() {
        seqGen = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeqGen() throws Exception {
        int databaseSizeBeforeCreate = seqGenRepository.findAll().size();

        // Create the SeqGen
        restSeqGenMockMvc.perform(post("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seqGen)))
            .andExpect(status().isCreated());

        // Validate the SeqGen in the database
        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeCreate + 1);
        SeqGen testSeqGen = seqGenList.get(seqGenList.size() - 1);
        assertThat(testSeqGen.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSeqGen.getNextid()).isEqualTo(DEFAULT_NEXTID);
    }

    @Test
    @Transactional
    public void createSeqGenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seqGenRepository.findAll().size();

        // Create the SeqGen with an existing ID
        seqGen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeqGenMockMvc.perform(post("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seqGen)))
            .andExpect(status().isBadRequest());

        // Validate the SeqGen in the database
        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = seqGenRepository.findAll().size();
        // set the field null
        seqGen.setCode(null);

        // Create the SeqGen, which fails.

        restSeqGenMockMvc.perform(post("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seqGen)))
            .andExpect(status().isBadRequest());

        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNextidIsRequired() throws Exception {
        int databaseSizeBeforeTest = seqGenRepository.findAll().size();
        // set the field null
        seqGen.setNextid(null);

        // Create the SeqGen, which fails.

        restSeqGenMockMvc.perform(post("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seqGen)))
            .andExpect(status().isBadRequest());

        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeqGens() throws Exception {
        // Initialize the database
        seqGenRepository.saveAndFlush(seqGen);

        // Get all the seqGenList
        restSeqGenMockMvc.perform(get("/api/seq-gens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seqGen.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].nextid").value(hasItem(DEFAULT_NEXTID.intValue())));
    }

    @Test
    @Transactional
    public void getSeqGen() throws Exception {
        // Initialize the database
        seqGenRepository.saveAndFlush(seqGen);

        // Get the seqGen
        restSeqGenMockMvc.perform(get("/api/seq-gens/{id}", seqGen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seqGen.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.nextid").value(DEFAULT_NEXTID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSeqGen() throws Exception {
        // Get the seqGen
        restSeqGenMockMvc.perform(get("/api/seq-gens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeqGen() throws Exception {
        // Initialize the database
        seqGenRepository.saveAndFlush(seqGen);
        int databaseSizeBeforeUpdate = seqGenRepository.findAll().size();

        // Update the seqGen
        SeqGen updatedSeqGen = seqGenRepository.findOne(seqGen.getId());
        // Disconnect from session so that the updates on updatedSeqGen are not directly saved in db
        em.detach(updatedSeqGen);
        updatedSeqGen
            .code(UPDATED_CODE)
            .nextid(UPDATED_NEXTID);

        restSeqGenMockMvc.perform(put("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeqGen)))
            .andExpect(status().isOk());

        // Validate the SeqGen in the database
        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeUpdate);
        SeqGen testSeqGen = seqGenList.get(seqGenList.size() - 1);
        assertThat(testSeqGen.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSeqGen.getNextid()).isEqualTo(UPDATED_NEXTID);
    }

    @Test
    @Transactional
    public void updateNonExistingSeqGen() throws Exception {
        int databaseSizeBeforeUpdate = seqGenRepository.findAll().size();

        // Create the SeqGen

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeqGenMockMvc.perform(put("/api/seq-gens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seqGen)))
            .andExpect(status().isCreated());

        // Validate the SeqGen in the database
        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeqGen() throws Exception {
        // Initialize the database
        seqGenRepository.saveAndFlush(seqGen);
        int databaseSizeBeforeDelete = seqGenRepository.findAll().size();

        // Get the seqGen
        restSeqGenMockMvc.perform(delete("/api/seq-gens/{id}", seqGen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeqGen> seqGenList = seqGenRepository.findAll();
        assertThat(seqGenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeqGen.class);
        SeqGen seqGen1 = new SeqGen();
        seqGen1.setId(1L);
        SeqGen seqGen2 = new SeqGen();
        seqGen2.setId(seqGen1.getId());
        assertThat(seqGen1).isEqualTo(seqGen2);
        seqGen2.setId(2L);
        assertThat(seqGen1).isNotEqualTo(seqGen2);
        seqGen1.setId(null);
        assertThat(seqGen1).isNotEqualTo(seqGen2);
    }
}
