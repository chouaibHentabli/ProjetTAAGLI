package com.univrennes1.web.rest;

import com.univrennes1.RsdataApp;

import com.univrennes1.domain.Variable;
import com.univrennes1.repository.VariableRepository;
import com.univrennes1.service.VariableService;
import com.univrennes1.service.dto.VariableDTO;
import com.univrennes1.service.mapper.VariableMapper;

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

import com.univrennes1.domain.enumeration.Type;
/**
 * Test class for the VariableResource REST controller.
 *
 * @see VariableResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = RsdataApp.class)

public class VariableResourceIntTest {
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_OBLIGATOIRE = false;
    private static final Boolean UPDATED_OBLIGATOIRE = true;

    private static final Type DEFAULT_VAR_TYPE = Type.NUMBER;
    private static final Type UPDATED_VAR_TYPE = Type.STRING;
    private static final String DEFAULT_VAR_REGEX = "AAAAA";
    private static final String UPDATED_VAR_REGEX = "BBBBB";

    @Inject
    private VariableRepository variableRepository;

    @Inject
    private VariableMapper variableMapper;

    @Inject
    private VariableService variableService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVariableMockMvc;

    private Variable variable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VariableResource variableResource = new VariableResource();
        ReflectionTestUtils.setField(variableResource, "variableService", variableService);
        this.restVariableMockMvc = MockMvcBuilders.standaloneSetup(variableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variable createEntity(EntityManager em) {
        Variable variable = new Variable()
                .nom(DEFAULT_NOM)
                .description(DEFAULT_DESCRIPTION)
                .obligatoire(DEFAULT_OBLIGATOIRE)
                .varType(DEFAULT_VAR_TYPE)
                .varRegex(DEFAULT_VAR_REGEX);
        return variable;
    }

    @Before
    public void initTest() {
        variable = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariable() throws Exception {
        int databaseSizeBeforeCreate = variableRepository.findAll().size();

        // Create the Variable
        VariableDTO variableDTO = variableMapper.variableToVariableDTO(variable);

        restVariableMockMvc.perform(post("/api/variables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(variableDTO)))
                .andExpect(status().isCreated());

        // Validate the Variable in the database
        List<Variable> variables = variableRepository.findAll();
        assertThat(variables).hasSize(databaseSizeBeforeCreate + 1);
        Variable testVariable = variables.get(variables.size() - 1);
        assertThat(testVariable.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVariable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVariable.isObligatoire()).isEqualTo(DEFAULT_OBLIGATOIRE);
        assertThat(testVariable.getVarType()).isEqualTo(DEFAULT_VAR_TYPE);
        assertThat(testVariable.getVarRegex()).isEqualTo(DEFAULT_VAR_REGEX);
    }

    @Test
    @Transactional
    public void getAllVariables() throws Exception {
        // Initialize the database
        variableRepository.saveAndFlush(variable);

        // Get all the variables
        restVariableMockMvc.perform(get("/api/variables?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(variable.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].obligatoire").value(hasItem(DEFAULT_OBLIGATOIRE.booleanValue())))
                .andExpect(jsonPath("$.[*].varType").value(hasItem(DEFAULT_VAR_TYPE.toString())))
                .andExpect(jsonPath("$.[*].varRegex").value(hasItem(DEFAULT_VAR_REGEX.toString())));
    }

    @Test
    @Transactional
    public void getVariable() throws Exception {
        // Initialize the database
        variableRepository.saveAndFlush(variable);

        // Get the variable
        restVariableMockMvc.perform(get("/api/variables/{id}", variable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variable.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.obligatoire").value(DEFAULT_OBLIGATOIRE.booleanValue()))
            .andExpect(jsonPath("$.varType").value(DEFAULT_VAR_TYPE.toString()))
            .andExpect(jsonPath("$.varRegex").value(DEFAULT_VAR_REGEX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVariable() throws Exception {
        // Get the variable
        restVariableMockMvc.perform(get("/api/variables/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariable() throws Exception {
        // Initialize the database
        variableRepository.saveAndFlush(variable);
        int databaseSizeBeforeUpdate = variableRepository.findAll().size();

        // Update the variable
        Variable updatedVariable = variableRepository.findOne(variable.getId());
        updatedVariable
                .nom(UPDATED_NOM)
                .description(UPDATED_DESCRIPTION)
                .obligatoire(UPDATED_OBLIGATOIRE)
                .varType(UPDATED_VAR_TYPE)
                .varRegex(UPDATED_VAR_REGEX);
        VariableDTO variableDTO = variableMapper.variableToVariableDTO(updatedVariable);

        restVariableMockMvc.perform(put("/api/variables")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(variableDTO)))
                .andExpect(status().isOk());

        // Validate the Variable in the database
        List<Variable> variables = variableRepository.findAll();
        assertThat(variables).hasSize(databaseSizeBeforeUpdate);
        Variable testVariable = variables.get(variables.size() - 1);
        assertThat(testVariable.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVariable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVariable.isObligatoire()).isEqualTo(UPDATED_OBLIGATOIRE);
        assertThat(testVariable.getVarType()).isEqualTo(UPDATED_VAR_TYPE);
        assertThat(testVariable.getVarRegex()).isEqualTo(UPDATED_VAR_REGEX);
    }

    @Test
    @Transactional
    public void deleteVariable() throws Exception {
        // Initialize the database
        variableRepository.saveAndFlush(variable);
        int databaseSizeBeforeDelete = variableRepository.findAll().size();

        // Get the variable
        restVariableMockMvc.perform(delete("/api/variables/{id}", variable.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Variable> variables = variableRepository.findAll();
        assertThat(variables).hasSize(databaseSizeBeforeDelete - 1);
    }
}
