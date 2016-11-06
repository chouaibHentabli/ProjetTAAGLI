package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Modification;
import com.univrennes1.repository.ModificationRepository;
import com.univrennes1.service.ModificationService;
import com.univrennes1.service.dto.ModificationDTO;
import com.univrennes1.service.mapper.ModificationMapper;

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
 * Test class for the ModificationResource REST controller.
 *
 * @see ModificationResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class ModificationResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_DATE_MOD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_MOD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_MOD_STR = dateTimeFormatter.format(DEFAULT_DATE_MOD);

    @Inject
    private ModificationRepository modificationRepository;

    @Inject
    private ModificationMapper modificationMapper;

    @Inject
    private ModificationService modificationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restModificationMockMvc;

    private Modification modification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModificationResource modificationResource = new ModificationResource();
        ReflectionTestUtils.setField(modificationResource, "modificationService", modificationService);
        this.restModificationMockMvc = MockMvcBuilders.standaloneSetup(modificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modification createEntity(EntityManager em) {
        Modification modification = new Modification()
                .dateMod(DEFAULT_DATE_MOD);
        return modification;
    }

    @Before
    public void initTest() {
        modification = createEntity(em);
    }

    @Test
    @Transactional
    public void createModification() throws Exception {
        int databaseSizeBeforeCreate = modificationRepository.findAll().size();

        // Create the Modification
        ModificationDTO modificationDTO = modificationMapper.modificationToModificationDTO(modification);

        restModificationMockMvc.perform(post("/api/modifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(modificationDTO)))
                .andExpect(status().isCreated());

        // Validate the Modification in the database
        List<Modification> modifications = modificationRepository.findAll();
        assertThat(modifications).hasSize(databaseSizeBeforeCreate + 1);
        Modification testModification = modifications.get(modifications.size() - 1);
        assertThat(testModification.getDateMod()).isEqualTo(DEFAULT_DATE_MOD);
    }

    @Test
    @Transactional
    public void getAllModifications() throws Exception {
        // Initialize the database
        modificationRepository.saveAndFlush(modification);

        // Get all the modifications
        restModificationMockMvc.perform(get("/api/modifications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(modification.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateMod").value(hasItem(DEFAULT_DATE_MOD_STR)));
    }

    @Test
    @Transactional
    public void getModification() throws Exception {
        // Initialize the database
        modificationRepository.saveAndFlush(modification);

        // Get the modification
        restModificationMockMvc.perform(get("/api/modifications/{id}", modification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modification.getId().intValue()))
            .andExpect(jsonPath("$.dateMod").value(DEFAULT_DATE_MOD_STR));
    }

    @Test
    @Transactional
    public void getNonExistingModification() throws Exception {
        // Get the modification
        restModificationMockMvc.perform(get("/api/modifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModification() throws Exception {
        // Initialize the database
        modificationRepository.saveAndFlush(modification);
        int databaseSizeBeforeUpdate = modificationRepository.findAll().size();

        // Update the modification
        Modification updatedModification = modificationRepository.findOne(modification.getId());
        updatedModification
                .dateMod(UPDATED_DATE_MOD);
        ModificationDTO modificationDTO = modificationMapper.modificationToModificationDTO(updatedModification);

        restModificationMockMvc.perform(put("/api/modifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(modificationDTO)))
                .andExpect(status().isOk());

        // Validate the Modification in the database
        List<Modification> modifications = modificationRepository.findAll();
        assertThat(modifications).hasSize(databaseSizeBeforeUpdate);
        Modification testModification = modifications.get(modifications.size() - 1);
        assertThat(testModification.getDateMod()).isEqualTo(UPDATED_DATE_MOD);
    }

    @Test
    @Transactional
    public void deleteModification() throws Exception {
        // Initialize the database
        modificationRepository.saveAndFlush(modification);
        int databaseSizeBeforeDelete = modificationRepository.findAll().size();

        // Get the modification
        restModificationMockMvc.perform(delete("/api/modifications/{id}", modification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Modification> modifications = modificationRepository.findAll();
        assertThat(modifications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
