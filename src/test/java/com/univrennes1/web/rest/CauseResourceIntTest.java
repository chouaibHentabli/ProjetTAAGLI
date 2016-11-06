package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Cause;
import com.univrennes1.repository.CauseRepository;
import com.univrennes1.service.CauseService;
import com.univrennes1.service.dto.CauseDTO;
import com.univrennes1.service.mapper.CauseMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CauseResource REST controller.
 *
 * @see CauseResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class CauseResourceIntTest {
    private static final String DEFAULT_CAUSE_INTITULE = "AAAAA";
    private static final String UPDATED_CAUSE_INTITULE = "BBBBB";

    @Inject
    private CauseRepository causeRepository;

    @Inject
    private CauseMapper causeMapper;

    @Inject
    private CauseService causeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCauseMockMvc;

    private Cause cause;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CauseResource causeResource = new CauseResource();
        ReflectionTestUtils.setField(causeResource, "causeService", causeService);
        this.restCauseMockMvc = MockMvcBuilders.standaloneSetup(causeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cause createEntity(EntityManager em) {
        Cause cause = new Cause()
                .causeIntitule(DEFAULT_CAUSE_INTITULE);
        return cause;
    }

    @Before
    public void initTest() {
        cause = createEntity(em);
    }

    @Test
    @Transactional
    public void createCause() throws Exception {
        int databaseSizeBeforeCreate = causeRepository.findAll().size();

        // Create the Cause
        CauseDTO causeDTO = causeMapper.causeToCauseDTO(cause);

        restCauseMockMvc.perform(post("/api/causes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(causeDTO)))
                .andExpect(status().isCreated());

        // Validate the Cause in the database
        List<Cause> causes = causeRepository.findAll();
        assertThat(causes).hasSize(databaseSizeBeforeCreate + 1);
        Cause testCause = causes.get(causes.size() - 1);
        assertThat(testCause.getCauseIntitule()).isEqualTo(DEFAULT_CAUSE_INTITULE);
    }

    @Test
    @Transactional
    public void getAllCauses() throws Exception {
        // Initialize the database
        causeRepository.saveAndFlush(cause);

        // Get all the causes
        restCauseMockMvc.perform(get("/api/causes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cause.getId().intValue())))
                .andExpect(jsonPath("$.[*].causeIntitule").value(hasItem(DEFAULT_CAUSE_INTITULE.toString())));
    }

    @Test
    @Transactional
    public void getCause() throws Exception {
        // Initialize the database
        causeRepository.saveAndFlush(cause);

        // Get the cause
        restCauseMockMvc.perform(get("/api/causes/{id}", cause.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cause.getId().intValue()))
            .andExpect(jsonPath("$.causeIntitule").value(DEFAULT_CAUSE_INTITULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCause() throws Exception {
        // Get the cause
        restCauseMockMvc.perform(get("/api/causes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCause() throws Exception {
        // Initialize the database
        causeRepository.saveAndFlush(cause);
        int databaseSizeBeforeUpdate = causeRepository.findAll().size();

        // Update the cause
        Cause updatedCause = causeRepository.findOne(cause.getId());
        updatedCause
                .causeIntitule(UPDATED_CAUSE_INTITULE);
        CauseDTO causeDTO = causeMapper.causeToCauseDTO(updatedCause);

        restCauseMockMvc.perform(put("/api/causes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(causeDTO)))
                .andExpect(status().isOk());

        // Validate the Cause in the database
        List<Cause> causes = causeRepository.findAll();
        assertThat(causes).hasSize(databaseSizeBeforeUpdate);
        Cause testCause = causes.get(causes.size() - 1);
        assertThat(testCause.getCauseIntitule()).isEqualTo(UPDATED_CAUSE_INTITULE);
    }

    @Test
    @Transactional
    public void deleteCause() throws Exception {
        // Initialize the database
        causeRepository.saveAndFlush(cause);
        int databaseSizeBeforeDelete = causeRepository.findAll().size();

        // Get the cause
        restCauseMockMvc.perform(delete("/api/causes/{id}", cause.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cause> causes = causeRepository.findAll();
        assertThat(causes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
