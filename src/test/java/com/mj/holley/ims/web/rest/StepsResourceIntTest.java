package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.Steps;
import com.mj.holley.ims.repository.StepsRepository;
import com.mj.holley.ims.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StepsResource REST controller.
 *
 * @see StepsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class StepsResourceIntTest {

    private static final String DEFAULT_STEP_ID = "AAAAAAAAAA";
    private static final String UPDATED_STEP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STEP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STEP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEQUENCE = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE = "BBBBBBBBBB";

    private static final String DEFAULT_STEP_ATTR_ID = "AAAAAAAAAA";
    private static final String UPDATED_STEP_ATTR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATION_ID = "BBBBBBBBBB";

    @Autowired
    private StepsRepository stepsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStepsMockMvc;

    private Steps steps;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StepsResource stepsResource = new StepsResource(stepsRepository);
        this.restStepsMockMvc = MockMvcBuilders.standaloneSetup(stepsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Steps createEntity(EntityManager em) {
        Steps steps = new Steps()
            .stepID(DEFAULT_STEP_ID)
            .stepName(DEFAULT_STEP_NAME)
            .sequence(DEFAULT_SEQUENCE)
            .stepAttrID(DEFAULT_STEP_ATTR_ID)
            .stationID(DEFAULT_STATION_ID);
        return steps;
    }

    @Before
    public void initTest() {
        steps = createEntity(em);
    }

    @Test
    @Transactional
    public void createSteps() throws Exception {
        int databaseSizeBeforeCreate = stepsRepository.findAll().size();

        // Create the Steps
        restStepsMockMvc.perform(post("/api/steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(steps)))
            .andExpect(status().isCreated());

        // Validate the Steps in the database
        List<Steps> stepsList = stepsRepository.findAll();
        assertThat(stepsList).hasSize(databaseSizeBeforeCreate + 1);
        Steps testSteps = stepsList.get(stepsList.size() - 1);
        assertThat(testSteps.getStepID()).isEqualTo(DEFAULT_STEP_ID);
        assertThat(testSteps.getStepName()).isEqualTo(DEFAULT_STEP_NAME);
        assertThat(testSteps.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testSteps.getStepAttrID()).isEqualTo(DEFAULT_STEP_ATTR_ID);
        assertThat(testSteps.getStationID()).isEqualTo(DEFAULT_STATION_ID);
    }

    @Test
    @Transactional
    public void createStepsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stepsRepository.findAll().size();

        // Create the Steps with an existing ID
        steps.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStepsMockMvc.perform(post("/api/steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(steps)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Steps> stepsList = stepsRepository.findAll();
        assertThat(stepsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSteps() throws Exception {
        // Initialize the database
        stepsRepository.saveAndFlush(steps);

        // Get all the stepsList
        restStepsMockMvc.perform(get("/api/steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(steps.getId().intValue())))
            .andExpect(jsonPath("$.[*].stepID").value(hasItem(DEFAULT_STEP_ID.toString())))
            .andExpect(jsonPath("$.[*].stepName").value(hasItem(DEFAULT_STEP_NAME.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.toString())))
            .andExpect(jsonPath("$.[*].stepAttrID").value(hasItem(DEFAULT_STEP_ATTR_ID.toString())))
            .andExpect(jsonPath("$.[*].stationID").value(hasItem(DEFAULT_STATION_ID.toString())));
    }

    @Test
    @Transactional
    public void getSteps() throws Exception {
        // Initialize the database
        stepsRepository.saveAndFlush(steps);

        // Get the steps
        restStepsMockMvc.perform(get("/api/steps/{id}", steps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(steps.getId().intValue()))
            .andExpect(jsonPath("$.stepID").value(DEFAULT_STEP_ID.toString()))
            .andExpect(jsonPath("$.stepName").value(DEFAULT_STEP_NAME.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.toString()))
            .andExpect(jsonPath("$.stepAttrID").value(DEFAULT_STEP_ATTR_ID.toString()))
            .andExpect(jsonPath("$.stationID").value(DEFAULT_STATION_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSteps() throws Exception {
        // Get the steps
        restStepsMockMvc.perform(get("/api/steps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSteps() throws Exception {
        // Initialize the database
        stepsRepository.saveAndFlush(steps);
        int databaseSizeBeforeUpdate = stepsRepository.findAll().size();

        // Update the steps
        Steps updatedSteps = stepsRepository.findOne(steps.getId());
        updatedSteps
            .stepID(UPDATED_STEP_ID)
            .stepName(UPDATED_STEP_NAME)
            .sequence(UPDATED_SEQUENCE)
            .stepAttrID(UPDATED_STEP_ATTR_ID)
            .stationID(UPDATED_STATION_ID);

        restStepsMockMvc.perform(put("/api/steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSteps)))
            .andExpect(status().isOk());

        // Validate the Steps in the database
        List<Steps> stepsList = stepsRepository.findAll();
        assertThat(stepsList).hasSize(databaseSizeBeforeUpdate);
        Steps testSteps = stepsList.get(stepsList.size() - 1);
        assertThat(testSteps.getStepID()).isEqualTo(UPDATED_STEP_ID);
        assertThat(testSteps.getStepName()).isEqualTo(UPDATED_STEP_NAME);
        assertThat(testSteps.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testSteps.getStepAttrID()).isEqualTo(UPDATED_STEP_ATTR_ID);
        assertThat(testSteps.getStationID()).isEqualTo(UPDATED_STATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSteps() throws Exception {
        int databaseSizeBeforeUpdate = stepsRepository.findAll().size();

        // Create the Steps

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStepsMockMvc.perform(put("/api/steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(steps)))
            .andExpect(status().isCreated());

        // Validate the Steps in the database
        List<Steps> stepsList = stepsRepository.findAll();
        assertThat(stepsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSteps() throws Exception {
        // Initialize the database
        stepsRepository.saveAndFlush(steps);
        int databaseSizeBeforeDelete = stepsRepository.findAll().size();

        // Get the steps
        restStepsMockMvc.perform(delete("/api/steps/{id}", steps.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Steps> stepsList = stepsRepository.findAll();
        assertThat(stepsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Steps.class);
    }
}
