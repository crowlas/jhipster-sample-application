package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.Rapport;
import io.github.jhipster.application.repository.RapportRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RapportResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class RapportResourceIT {

    private static final String DEFAULT_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_METEO = "AAAAAAAAAA";
    private static final String UPDATED_METEO = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME = "BBBBBBBBBB";

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRapportMockMvc;

    private Rapport rapport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RapportResource rapportResource = new RapportResource(rapportRepository);
        this.restRapportMockMvc = MockMvcBuilders.standaloneSetup(rapportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .month(DEFAULT_MONTH)
            .meteo(DEFAULT_METEO)
            .resume(DEFAULT_RESUME);
        return rapport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createUpdatedEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .month(UPDATED_MONTH)
            .meteo(UPDATED_METEO)
            .resume(UPDATED_RESUME);
        return rapport;
    }

    @BeforeEach
    public void initTest() {
        rapport = createEntity(em);
    }

    @Test
    @Transactional
    public void createRapport() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isCreated());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate + 1);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testRapport.getMeteo()).isEqualTo(DEFAULT_METEO);
        assertThat(testRapport.getResume()).isEqualTo(DEFAULT_RESUME);
    }

    @Test
    @Transactional
    public void createRapportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // Create the Rapport with an existing ID
        rapport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportMockMvc.perform(post("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRapports() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get all the rapportList
        restRapportMockMvc.perform(get("/api/rapports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].meteo").value(hasItem(DEFAULT_METEO.toString())))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())));
    }
    
    @Test
    @Transactional
    public void getRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rapport.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.meteo").value(DEFAULT_METEO.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRapport() throws Exception {
        // Get the rapport
        restRapportMockMvc.perform(get("/api/rapports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport
        Rapport updatedRapport = rapportRepository.findById(rapport.getId()).get();
        // Disconnect from session so that the updates on updatedRapport are not directly saved in db
        em.detach(updatedRapport);
        updatedRapport
            .month(UPDATED_MONTH)
            .meteo(UPDATED_METEO)
            .resume(UPDATED_RESUME);

        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRapport)))
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testRapport.getMeteo()).isEqualTo(UPDATED_METEO);
        assertThat(testRapport.getResume()).isEqualTo(UPDATED_RESUME);
    }

    @Test
    @Transactional
    public void updateNonExistingRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Create the Rapport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc.perform(put("/api/rapports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rapport)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeDelete = rapportRepository.findAll().size();

        // Delete the rapport
        restRapportMockMvc.perform(delete("/api/rapports/{id}", rapport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rapport.class);
        Rapport rapport1 = new Rapport();
        rapport1.setId(1L);
        Rapport rapport2 = new Rapport();
        rapport2.setId(rapport1.getId());
        assertThat(rapport1).isEqualTo(rapport2);
        rapport2.setId(2L);
        assertThat(rapport1).isNotEqualTo(rapport2);
        rapport1.setId(null);
        assertThat(rapport1).isNotEqualTo(rapport2);
    }
}
