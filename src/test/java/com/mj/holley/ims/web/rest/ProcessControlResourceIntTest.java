package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.ProcessControl;
import com.mj.holley.ims.repository.ProcessControlRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mj.holley.ims.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProcessControlResource REST controller.
 *
 * @see ProcessControlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class ProcessControlResourceIntTest {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HUT_ID = "AAAAAAAAAA";
    private static final String UPDATED_HUT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MOUNT_GUARD_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MOUNT_GUARD_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ProcessControlRepository processControlRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcessControlMockMvc;

    private ProcessControl processControl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcessControlResource processControlResource = new ProcessControlResource(processControlRepository);
        this.restProcessControlMockMvc = MockMvcBuilders.standaloneSetup(processControlResource)
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
    public static ProcessControl createEntity(EntityManager em) {
        ProcessControl processControl = new ProcessControl()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .hutID(DEFAULT_HUT_ID)
            .orderID(DEFAULT_ORDER_ID)
            .stationID(DEFAULT_STATION_ID)
            .result(DEFAULT_RESULT)
            .mountGuardTime(DEFAULT_MOUNT_GUARD_TIME);
        return processControl;
    }

    @Before
    public void initTest() {
        processControl = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessControl() throws Exception {
        int databaseSizeBeforeCreate = processControlRepository.findAll().size();

        // Create the ProcessControl
        restProcessControlMockMvc.perform(post("/api/process-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processControl)))
            .andExpect(status().isCreated());

        // Validate the ProcessControl in the database
        List<ProcessControl> processControlList = processControlRepository.findAll();
        assertThat(processControlList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessControl testProcessControl = processControlList.get(processControlList.size() - 1);
        assertThat(testProcessControl.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testProcessControl.getHutID()).isEqualTo(DEFAULT_HUT_ID);
        assertThat(testProcessControl.getOrderID()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testProcessControl.getStationID()).isEqualTo(DEFAULT_STATION_ID);
        assertThat(testProcessControl.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testProcessControl.getMountGuardTime()).isEqualTo(DEFAULT_MOUNT_GUARD_TIME);
    }

    @Test
    @Transactional
    public void createProcessControlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processControlRepository.findAll().size();

        // Create the ProcessControl with an existing ID
        processControl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessControlMockMvc.perform(post("/api/process-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processControl)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProcessControl> processControlList = processControlRepository.findAll();
        assertThat(processControlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcessControls() throws Exception {
        // Initialize the database
        processControlRepository.saveAndFlush(processControl);

        // Get all the processControlList
        restProcessControlMockMvc.perform(get("/api/process-controls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processControl.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hutID").value(hasItem(DEFAULT_HUT_ID.toString())))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].stationID").value(hasItem(DEFAULT_STATION_ID.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].mountGuardTime").value(hasItem(sameInstant(DEFAULT_MOUNT_GUARD_TIME))));
    }

    @Test
    @Transactional
    public void getProcessControl() throws Exception {
        // Initialize the database
        processControlRepository.saveAndFlush(processControl);

        // Get the processControl
        restProcessControlMockMvc.perform(get("/api/process-controls/{id}", processControl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processControl.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.hutID").value(DEFAULT_HUT_ID.toString()))
            .andExpect(jsonPath("$.orderID").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.stationID").value(DEFAULT_STATION_ID.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.mountGuardTime").value(sameInstant(DEFAULT_MOUNT_GUARD_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingProcessControl() throws Exception {
        // Get the processControl
        restProcessControlMockMvc.perform(get("/api/process-controls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessControl() throws Exception {
        // Initialize the database
        processControlRepository.saveAndFlush(processControl);
        int databaseSizeBeforeUpdate = processControlRepository.findAll().size();

        // Update the processControl
        ProcessControl updatedProcessControl = processControlRepository.findOne(processControl.getId());
        updatedProcessControl
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .hutID(UPDATED_HUT_ID)
            .orderID(UPDATED_ORDER_ID)
            .stationID(UPDATED_STATION_ID)
            .result(UPDATED_RESULT)
            .mountGuardTime(UPDATED_MOUNT_GUARD_TIME);

        restProcessControlMockMvc.perform(put("/api/process-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessControl)))
            .andExpect(status().isOk());

        // Validate the ProcessControl in the database
        List<ProcessControl> processControlList = processControlRepository.findAll();
        assertThat(processControlList).hasSize(databaseSizeBeforeUpdate);
        ProcessControl testProcessControl = processControlList.get(processControlList.size() - 1);
        assertThat(testProcessControl.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testProcessControl.getHutID()).isEqualTo(UPDATED_HUT_ID);
        assertThat(testProcessControl.getOrderID()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testProcessControl.getStationID()).isEqualTo(UPDATED_STATION_ID);
        assertThat(testProcessControl.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testProcessControl.getMountGuardTime()).isEqualTo(UPDATED_MOUNT_GUARD_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessControl() throws Exception {
        int databaseSizeBeforeUpdate = processControlRepository.findAll().size();

        // Create the ProcessControl

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcessControlMockMvc.perform(put("/api/process-controls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processControl)))
            .andExpect(status().isCreated());

        // Validate the ProcessControl in the database
        List<ProcessControl> processControlList = processControlRepository.findAll();
        assertThat(processControlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcessControl() throws Exception {
        // Initialize the database
        processControlRepository.saveAndFlush(processControl);
        int databaseSizeBeforeDelete = processControlRepository.findAll().size();

        // Get the processControl
        restProcessControlMockMvc.perform(delete("/api/process-controls/{id}", processControl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcessControl> processControlList = processControlRepository.findAll();
        assertThat(processControlList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessControl.class);
    }
}
