package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Rubrique;
import com.univrennes1.repository.RubriqueRepository;
import com.univrennes1.service.RubriqueService;
import com.univrennes1.service.dto.RubriqueDTO;
import com.univrennes1.service.mapper.RubriqueMapper;

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
 * Test class for the RubriqueResource REST controller.
 *
 * @see RubriqueResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class RubriqueResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final Boolean DEFAULT_RUB_UNIQUE = false;
    private static final Boolean UPDATED_RUB_UNIQUE = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private RubriqueRepository rubriqueRepository;

    @Inject
    private RubriqueMapper rubriqueMapper;

    @Inject
    private RubriqueService rubriqueService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRubriqueMockMvc;

    private Rubrique rubrique;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RubriqueResource rubriqueResource = new RubriqueResource();
        ReflectionTestUtils.setField(rubriqueResource, "rubriqueService", rubriqueService);
        this.restRubriqueMockMvc = MockMvcBuilders.standaloneSetup(rubriqueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubrique createEntity(EntityManager em) {
        Rubrique rubrique = new Rubrique()
                .nom(DEFAULT_NOM)
                .rubUnique(DEFAULT_RUB_UNIQUE)
                .active(DEFAULT_ACTIVE)
                .description(DEFAULT_DESCRIPTION);
        return rubrique;
    }

    @Before
    public void initTest() {
        rubrique = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubrique() throws Exception {
        int databaseSizeBeforeCreate = rubriqueRepository.findAll().size();

        // Create the Rubrique
        RubriqueDTO rubriqueDTO = rubriqueMapper.rubriqueToRubriqueDTO(rubrique);

        restRubriqueMockMvc.perform(post("/api/rubriques")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
                .andExpect(status().isCreated());

        // Validate the Rubrique in the database
        List<Rubrique> rubriques = rubriqueRepository.findAll();
        assertThat(rubriques).hasSize(databaseSizeBeforeCreate + 1);
        Rubrique testRubrique = rubriques.get(rubriques.size() - 1);
        assertThat(testRubrique.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRubrique.isRubUnique()).isEqualTo(DEFAULT_RUB_UNIQUE);
        assertThat(testRubrique.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testRubrique.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRubriques() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get all the rubriques
        restRubriqueMockMvc.perform(get("/api/rubriques?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rubrique.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].rubUnique").value(hasItem(DEFAULT_RUB_UNIQUE.booleanValue())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", rubrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubrique.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.rubUnique").value(DEFAULT_RUB_UNIQUE.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRubrique() throws Exception {
        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);
        int databaseSizeBeforeUpdate = rubriqueRepository.findAll().size();

        // Update the rubrique
        Rubrique updatedRubrique = rubriqueRepository.findOne(rubrique.getId());
        updatedRubrique
                .nom(UPDATED_NOM)
                .rubUnique(UPDATED_RUB_UNIQUE)
                .active(UPDATED_ACTIVE)
                .description(UPDATED_DESCRIPTION);
        RubriqueDTO rubriqueDTO = rubriqueMapper.rubriqueToRubriqueDTO(updatedRubrique);

        restRubriqueMockMvc.perform(put("/api/rubriques")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
                .andExpect(status().isOk());

        // Validate the Rubrique in the database
        List<Rubrique> rubriques = rubriqueRepository.findAll();
        assertThat(rubriques).hasSize(databaseSizeBeforeUpdate);
        Rubrique testRubrique = rubriques.get(rubriques.size() - 1);
        assertThat(testRubrique.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRubrique.isRubUnique()).isEqualTo(UPDATED_RUB_UNIQUE);
        assertThat(testRubrique.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testRubrique.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);
        int databaseSizeBeforeDelete = rubriqueRepository.findAll().size();

        // Get the rubrique
        restRubriqueMockMvc.perform(delete("/api/rubriques/{id}", rubrique.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rubrique> rubriques = rubriqueRepository.findAll();
        assertThat(rubriques).hasSize(databaseSizeBeforeDelete - 1);
    }
}
