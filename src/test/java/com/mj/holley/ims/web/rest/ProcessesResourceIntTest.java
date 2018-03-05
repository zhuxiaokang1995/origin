package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.Processes;
import com.mj.holley.ims.repository.ProcessesRepository;
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
 * Test class for the ProcessesResource REST controller.
 *
 * @see ProcessesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class ProcessesResourceIntTest {

    private static final String DEFAULT_SUB_BOP_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUB_BOP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GENERAL_SOP_PATH = "AAAAAAAAAA";
    private static final String UPDATED_GENERAL_SOP_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_BOP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUB_BOP_NAME = "BBBBBBBBBB";

    @Autowired
    private ProcessesRepository processesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcessesMockMvc;

    private Processes processes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ProcessesResource processesResource = new ProcessesResource(processesRepository);
        this.restProcessesMockMvc = MockMvcBuilders.standaloneSetup(processesResource)
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
    public static Processes createEntity(EntityManager em) {
        Processes processes = new Processes()
                .subBopID(DEFAULT_SUB_BOP_ID)
                .processID(DEFAULT_PROCESS_ID)
                .generalSopPath(DEFAULT_GENERAL_SOP_PATH)
                .subBopName(DEFAULT_SUB_BOP_NAME);
        return processes;
    }

    @Before
    public void initTest() {
        processes = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesses() throws Exception {
        int databaseSizeBeforeCreate = processesRepository.findAll().size();

        // Create the Processes

        restProcessesMockMvc.perform(post("/api/processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processes)))
            .andExpect(status().isCreated());

        // Validate the Processes in the database
        List<Processes> processesList = processesRepository.findAll();
        assertThat(processesList).hasSize(databaseSizeBeforeCreate + 1);
        Processes testProcesses = processesList.get(processesList.size() - 1);
        assertThat(testProcesses.getSubBopID()).isEqualTo(DEFAULT_SUB_BOP_ID);
        assertThat(testProcesses.getProcessID()).isEqualTo(DEFAULT_PROCESS_ID);
        assertThat(testProcesses.getGeneralSopPath()).isEqualTo(DEFAULT_GENERAL_SOP_PATH);
        assertThat(testProcesses.getSubBopName()).isEqualTo(DEFAULT_SUB_BOP_NAME);
    }

    @Test
    @Transactional
    public void createProcessesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processesRepository.findAll().size();

        // Create the Processes with an existing ID
        Processes existingProcesses = new Processes();
        existingProcesses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessesMockMvc.perform(post("/api/processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProcesses)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Processes> processesList = processesRepository.findAll();
        assertThat(processesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcesses() throws Exception {
        // Initialize the database
        processesRepository.saveAndFlush(processes);

        // Get all the processesList
        restProcessesMockMvc.perform(get("/api/processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processes.getId().intValue())))
            .andExpect(jsonPath("$.[*].subBopID").value(hasItem(DEFAULT_SUB_BOP_ID.toString())))
            .andExpect(jsonPath("$.[*].processID").value(hasItem(DEFAULT_PROCESS_ID.toString())))
            .andExpect(jsonPath("$.[*].generalSopPath").value(hasItem(DEFAULT_GENERAL_SOP_PATH.toString())))
            .andExpect(jsonPath("$.[*].subBopName").value(hasItem(DEFAULT_SUB_BOP_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProcesses() throws Exception {
        // Initialize the database
        processesRepository.saveAndFlush(processes);

        // Get the processes
        restProcessesMockMvc.perform(get("/api/processes/{id}", processes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processes.getId().intValue()))
            .andExpect(jsonPath("$.subBopID").value(DEFAULT_SUB_BOP_ID.toString()))
            .andExpect(jsonPath("$.processID").value(DEFAULT_PROCESS_ID.toString()))
            .andExpect(jsonPath("$.generalSopPath").value(DEFAULT_GENERAL_SOP_PATH.toString()))
            .andExpect(jsonPath("$.subBopName").value(DEFAULT_SUB_BOP_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcesses() throws Exception {
        // Get the processes
        restProcessesMockMvc.perform(get("/api/processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesses() throws Exception {
        // Initialize the database
        processesRepository.saveAndFlush(processes);
        int databaseSizeBeforeUpdate = processesRepository.findAll().size();

        // Update the processes
        Processes updatedProcesses = processesRepository.findOne(processes.getId());
        updatedProcesses
                .subBopID(UPDATED_SUB_BOP_ID)
                .processID(UPDATED_PROCESS_ID)
                .generalSopPath(UPDATED_GENERAL_SOP_PATH)
                .subBopName(UPDATED_SUB_BOP_NAME);

        restProcessesMockMvc.perform(put("/api/processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcesses)))
            .andExpect(status().isOk());

        // Validate the Processes in the database
        List<Processes> processesList = processesRepository.findAll();
        assertThat(processesList).hasSize(databaseSizeBeforeUpdate);
        Processes testProcesses = processesList.get(processesList.size() - 1);
        assertThat(testProcesses.getSubBopID()).isEqualTo(UPDATED_SUB_BOP_ID);
        assertThat(testProcesses.getProcessID()).isEqualTo(UPDATED_PROCESS_ID);
        assertThat(testProcesses.getGeneralSopPath()).isEqualTo(UPDATED_GENERAL_SOP_PATH);
        assertThat(testProcesses.getSubBopName()).isEqualTo(UPDATED_SUB_BOP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesses() throws Exception {
        int databaseSizeBeforeUpdate = processesRepository.findAll().size();

        // Create the Processes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcessesMockMvc.perform(put("/api/processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processes)))
            .andExpect(status().isCreated());

        // Validate the Processes in the database
        List<Processes> processesList = processesRepository.findAll();
        assertThat(processesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcesses() throws Exception {
        // Initialize the database
        processesRepository.saveAndFlush(processes);
        int databaseSizeBeforeDelete = processesRepository.findAll().size();

        // Get the processes
        restProcessesMockMvc.perform(delete("/api/processes/{id}", processes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Processes> processesList = processesRepository.findAll();
        assertThat(processesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processes.class);
    }
}
