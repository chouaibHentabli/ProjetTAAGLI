package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Accident;
import com.univrennes1.repository.AccidentRepository;
import com.univrennes1.service.AccidentService;
import com.univrennes1.service.dto.AccidentDTO;
import com.univrennes1.service.mapper.AccidentMapper;

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
 * Test class for the AccidentResource REST controller.
 *
 * @see AccidentResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class AccidentResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_DATE_ACC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_ACC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_ACC_STR = dateTimeFormatter.format(DEFAULT_DATE_ACC);

    private static final ZonedDateTime DEFAULT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATION_STR = dateTimeFormatter.format(DEFAULT_DATE_CREATION);

    private static final ZonedDateTime DEFAULT_HEURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_HEURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_HEURE_STR = dateTimeFormatter.format(DEFAULT_HEURE);

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_NBR_BLESSE = 1;
    private static final Integer UPDATED_NBR_BLESSE = 2;

    private static final Integer DEFAULT_NBR_HOSPITALISE = 1;
    private static final Integer UPDATED_NBR_HOSPITALISE = 2;

    private static final Integer DEFAULT_NBR_MORTS = 1;
    private static final Integer UPDATED_NBR_MORTS = 2;

    private static final Integer DEFAULT_NBR_INDEMNE = 1;
    private static final Integer UPDATED_NBR_INDEMNE = 2;

    @Inject
    private AccidentRepository accidentRepository;

    @Inject
    private AccidentMapper accidentMapper;

    @Inject
    private AccidentService accidentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAccidentMockMvc;

    private Accident accident;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccidentResource accidentResource = new AccidentResource();
        ReflectionTestUtils.setField(accidentResource, "accidentService", accidentService);
        this.restAccidentMockMvc = MockMvcBuilders.standaloneSetup(accidentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accident createEntity(EntityManager em) {
        Accident accident = new Accident()
                .dateAcc(DEFAULT_DATE_ACC)
                .dateCreation(DEFAULT_DATE_CREATION)
                .heure(DEFAULT_HEURE)
                .latitude(DEFAULT_LATITUDE)
                .longitude(DEFAULT_LONGITUDE)
                .status(DEFAULT_STATUS)
                .nbrBlesse(DEFAULT_NBR_BLESSE)
                .nbrHospitalise(DEFAULT_NBR_HOSPITALISE)
                .nbrMorts(DEFAULT_NBR_MORTS)
                .nbrIndemne(DEFAULT_NBR_INDEMNE);
        return accident;
    }

    @Before
    public void initTest() {
        accident = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccident() throws Exception {
        int databaseSizeBeforeCreate = accidentRepository.findAll().size();

        // Create the Accident
        AccidentDTO accidentDTO = accidentMapper.accidentToAccidentDTO(accident);

        restAccidentMockMvc.perform(post("/api/accidents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accidentDTO)))
                .andExpect(status().isCreated());

        // Validate the Accident in the database
        List<Accident> accidents = accidentRepository.findAll();
        assertThat(accidents).hasSize(databaseSizeBeforeCreate + 1);
        Accident testAccident = accidents.get(accidents.size() - 1);
        assertThat(testAccident.getDateAcc()).isEqualTo(DEFAULT_DATE_ACC);
        assertThat(testAccident.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testAccident.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testAccident.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAccident.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAccident.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccident.getNbrBlesse()).isEqualTo(DEFAULT_NBR_BLESSE);
        assertThat(testAccident.getNbrHospitalise()).isEqualTo(DEFAULT_NBR_HOSPITALISE);
        assertThat(testAccident.getNbrMorts()).isEqualTo(DEFAULT_NBR_MORTS);
        assertThat(testAccident.getNbrIndemne()).isEqualTo(DEFAULT_NBR_INDEMNE);
    }

    @Test
    @Transactional
    public void getAllAccidents() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        // Get all the accidents
        restAccidentMockMvc.perform(get("/api/accidents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accident.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateAcc").value(hasItem(DEFAULT_DATE_ACC_STR)))
                .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION_STR)))
                .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE_STR)))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].nbrBlesse").value(hasItem(DEFAULT_NBR_BLESSE)))
                .andExpect(jsonPath("$.[*].nbrHospitalise").value(hasItem(DEFAULT_NBR_HOSPITALISE)))
                .andExpect(jsonPath("$.[*].nbrMorts").value(hasItem(DEFAULT_NBR_MORTS)))
                .andExpect(jsonPath("$.[*].nbrIndemne").value(hasItem(DEFAULT_NBR_INDEMNE)));
    }

    @Test
    @Transactional
    public void getAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        // Get the accident
        restAccidentMockMvc.perform(get("/api/accidents/{id}", accident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accident.getId().intValue()))
            .andExpect(jsonPath("$.dateAcc").value(DEFAULT_DATE_ACC_STR))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION_STR))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE_STR))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.nbrBlesse").value(DEFAULT_NBR_BLESSE))
            .andExpect(jsonPath("$.nbrHospitalise").value(DEFAULT_NBR_HOSPITALISE))
            .andExpect(jsonPath("$.nbrMorts").value(DEFAULT_NBR_MORTS))
            .andExpect(jsonPath("$.nbrIndemne").value(DEFAULT_NBR_INDEMNE));
    }

    @Test
    @Transactional
    public void getNonExistingAccident() throws Exception {
        // Get the accident
        restAccidentMockMvc.perform(get("/api/accidents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();

        // Update the accident
        Accident updatedAccident = accidentRepository.findOne(accident.getId());
        updatedAccident
                .dateAcc(UPDATED_DATE_ACC)
                .dateCreation(UPDATED_DATE_CREATION)
                .heure(UPDATED_HEURE)
                .latitude(UPDATED_LATITUDE)
                .longitude(UPDATED_LONGITUDE)
                .status(UPDATED_STATUS)
                .nbrBlesse(UPDATED_NBR_BLESSE)
                .nbrHospitalise(UPDATED_NBR_HOSPITALISE)
                .nbrMorts(UPDATED_NBR_MORTS)
                .nbrIndemne(UPDATED_NBR_INDEMNE);
        AccidentDTO accidentDTO = accidentMapper.accidentToAccidentDTO(updatedAccident);

        restAccidentMockMvc.perform(put("/api/accidents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accidentDTO)))
                .andExpect(status().isOk());

        // Validate the Accident in the database
        List<Accident> accidents = accidentRepository.findAll();
        assertThat(accidents).hasSize(databaseSizeBeforeUpdate);
        Accident testAccident = accidents.get(accidents.size() - 1);
        assertThat(testAccident.getDateAcc()).isEqualTo(UPDATED_DATE_ACC);
        assertThat(testAccident.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testAccident.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testAccident.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAccident.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAccident.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccident.getNbrBlesse()).isEqualTo(UPDATED_NBR_BLESSE);
        assertThat(testAccident.getNbrHospitalise()).isEqualTo(UPDATED_NBR_HOSPITALISE);
        assertThat(testAccident.getNbrMorts()).isEqualTo(UPDATED_NBR_MORTS);
        assertThat(testAccident.getNbrIndemne()).isEqualTo(UPDATED_NBR_INDEMNE);
    }

    @Test
    @Transactional
    public void deleteAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);
        int databaseSizeBeforeDelete = accidentRepository.findAll().size();

        // Get the accident
        restAccidentMockMvc.perform(delete("/api/accidents/{id}", accident.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Accident> accidents = accidentRepository.findAll();
        assertThat(accidents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
