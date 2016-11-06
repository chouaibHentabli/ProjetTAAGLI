package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Baac;
import com.univrennes1.repository.BaacRepository;
import com.univrennes1.service.BaacService;
import com.univrennes1.service.dto.BaacDTO;
import com.univrennes1.service.mapper.BaacMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BaacResource REST controller.
 *
 * @see BaacResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class BaacResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATION_STR = dateTimeFormatter.format(DEFAULT_DATE_CREATION);

    @Inject
    private BaacRepository baacRepository;

    @Inject
    private BaacMapper baacMapper;

    @Inject
    private BaacService baacService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBaacMockMvc;

    private Baac baac;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BaacResource baacResource = new BaacResource();
        ReflectionTestUtils.setField(baacResource, "baacService", baacService);
        this.restBaacMockMvc = MockMvcBuilders.standaloneSetup(baacResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Baac createEntity(EntityManager em) {
        Baac baac = new Baac()
                .dateCreation(DEFAULT_DATE_CREATION);
        return baac;
    }

    @Before
    public void initTest() {
        baac = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaac() throws Exception {
        int databaseSizeBeforeCreate = baacRepository.findAll().size();

        // Create the Baac
        BaacDTO baacDTO = baacMapper.baacToBaacDTO(baac);

        restBaacMockMvc.perform(post("/api/baacs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(baacDTO)))
                .andExpect(status().isCreated());

        // Validate the Baac in the database
        List<Baac> baacs = baacRepository.findAll();
        assertThat(baacs).hasSize(databaseSizeBeforeCreate + 1);
        Baac testBaac = baacs.get(baacs.size() - 1);
        assertThat(testBaac.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllBaacs() throws Exception {
        // Initialize the database
        baacRepository.saveAndFlush(baac);

        // Get all the baacs
        restBaacMockMvc.perform(get("/api/baacs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(baac.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION_STR)));
    }

    @Test
    @Transactional
    public void getBaac() throws Exception {
        // Initialize the database
        baacRepository.saveAndFlush(baac);

        // Get the baac
        restBaacMockMvc.perform(get("/api/baacs/{id}", baac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baac.getId().intValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBaac() throws Exception {
        // Get the baac
        restBaacMockMvc.perform(get("/api/baacs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaac() throws Exception {
        // Initialize the database
        baacRepository.saveAndFlush(baac);
        int databaseSizeBeforeUpdate = baacRepository.findAll().size();

        // Update the baac
        Baac updatedBaac = baacRepository.findOne(baac.getId());
        updatedBaac
                .dateCreation(UPDATED_DATE_CREATION);
        BaacDTO baacDTO = baacMapper.baacToBaacDTO(updatedBaac);

        restBaacMockMvc.perform(put("/api/baacs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(baacDTO)))
                .andExpect(status().isOk());

        // Validate the Baac in the database
        List<Baac> baacs = baacRepository.findAll();
        assertThat(baacs).hasSize(databaseSizeBeforeUpdate);
        Baac testBaac = baacs.get(baacs.size() - 1);
        assertThat(testBaac.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void deleteBaac() throws Exception {
        // Initialize the database
        baacRepository.saveAndFlush(baac);
        int databaseSizeBeforeDelete = baacRepository.findAll().size();

        // Get the baac
        restBaacMockMvc.perform(delete("/api/baacs/{id}", baac.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Baac> baacs = baacRepository.findAll();
        assertThat(baacs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
