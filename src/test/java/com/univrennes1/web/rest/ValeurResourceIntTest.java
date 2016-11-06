package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Valeur;
import com.univrennes1.repository.ValeurRepository;
import com.univrennes1.service.ValeurService;
import com.univrennes1.service.dto.ValeurDTO;
import com.univrennes1.service.mapper.ValeurMapper;

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
 * Test class for the ValeurResource REST controller.
 *
 * @see ValeurResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class ValeurResourceIntTest {
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ValeurRepository valeurRepository;

    @Inject
    private ValeurMapper valeurMapper;

    @Inject
    private ValeurService valeurService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restValeurMockMvc;

    private Valeur valeur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValeurResource valeurResource = new ValeurResource();
        ReflectionTestUtils.setField(valeurResource, "valeurService", valeurService);
        this.restValeurMockMvc = MockMvcBuilders.standaloneSetup(valeurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valeur createEntity(EntityManager em) {
        Valeur valeur = new Valeur()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION);
        return valeur;
    }

    @Before
    public void initTest() {
        valeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createValeur() throws Exception {
        int databaseSizeBeforeCreate = valeurRepository.findAll().size();

        // Create the Valeur
        ValeurDTO valeurDTO = valeurMapper.valeurToValeurDTO(valeur);

        restValeurMockMvc.perform(post("/api/valeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valeurDTO)))
                .andExpect(status().isCreated());

        // Validate the Valeur in the database
        List<Valeur> valeurs = valeurRepository.findAll();
        assertThat(valeurs).hasSize(databaseSizeBeforeCreate + 1);
        Valeur testValeur = valeurs.get(valeurs.size() - 1);
        assertThat(testValeur.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testValeur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllValeurs() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get all the valeurs
        restValeurMockMvc.perform(get("/api/valeurs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(valeur.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getValeur() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);

        // Get the valeur
        restValeurMockMvc.perform(get("/api/valeurs/{id}", valeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valeur.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingValeur() throws Exception {
        // Get the valeur
        restValeurMockMvc.perform(get("/api/valeurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValeur() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);
        int databaseSizeBeforeUpdate = valeurRepository.findAll().size();

        // Update the valeur
        Valeur updatedValeur = valeurRepository.findOne(valeur.getId());
        updatedValeur
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION);
        ValeurDTO valeurDTO = valeurMapper.valeurToValeurDTO(updatedValeur);

        restValeurMockMvc.perform(put("/api/valeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(valeurDTO)))
                .andExpect(status().isOk());

        // Validate the Valeur in the database
        List<Valeur> valeurs = valeurRepository.findAll();
        assertThat(valeurs).hasSize(databaseSizeBeforeUpdate);
        Valeur testValeur = valeurs.get(valeurs.size() - 1);
        assertThat(testValeur.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testValeur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteValeur() throws Exception {
        // Initialize the database
        valeurRepository.saveAndFlush(valeur);
        int databaseSizeBeforeDelete = valeurRepository.findAll().size();

        // Get the valeur
        restValeurMockMvc.perform(delete("/api/valeurs/{id}", valeur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Valeur> valeurs = valeurRepository.findAll();
        assertThat(valeurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
