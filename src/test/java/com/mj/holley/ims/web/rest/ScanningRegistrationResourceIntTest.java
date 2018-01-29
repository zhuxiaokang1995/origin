package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.ScanningRegistration;
import com.mj.holley.ims.repository.ScanningRegistrationRepository;
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
 * Test class for the ScanningRegistrationResource REST controller.
 *
 * @see ScanningRegistrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class ScanningRegistrationResourceIntTest {

    private static final Integer DEFAULT_PK = 1;
    private static final Integer UPDATED_PK = 2;

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SCAN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SCAN_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEFECT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_SN = "AAAAAAAAAA";
    private static final String UPDATED_SUB_SN = "BBBBBBBBBB";

    @Autowired
    private ScanningRegistrationRepository scanningRegistrationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScanningRegistrationMockMvc;

    private ScanningRegistration scanningRegistration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScanningRegistrationResource scanningRegistrationResource = new ScanningRegistrationResource(scanningRegistrationRepository);
        this.restScanningRegistrationMockMvc = MockMvcBuilders.standaloneSetup(scanningRegistrationResource)
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
    public static ScanningRegistration createEntity(EntityManager em) {
        ScanningRegistration scanningRegistration = new ScanningRegistration()
            .pk(DEFAULT_PK)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .stationID(DEFAULT_STATION_ID)
            .scanType(DEFAULT_SCAN_TYPE)
            .defectCode(DEFAULT_DEFECT_CODE)
            .result(DEFAULT_RESULT)
            .subSn(DEFAULT_SUB_SN);
        return scanningRegistration;
    }

    @Before
    public void initTest() {
        scanningRegistration = createEntity(em);
    }

    @Test
    @Transactional
    public void createScanningRegistration() throws Exception {
        int databaseSizeBeforeCreate = scanningRegistrationRepository.findAll().size();

        // Create the ScanningRegistration
        restScanningRegistrationMockMvc.perform(post("/api/scanning-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scanningRegistration)))
            .andExpect(status().isCreated());

        // Validate the ScanningRegistration in the database
        List<ScanningRegistration> scanningRegistrationList = scanningRegistrationRepository.findAll();
        assertThat(scanningRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        ScanningRegistration testScanningRegistration = scanningRegistrationList.get(scanningRegistrationList.size() - 1);
        assertThat(testScanningRegistration.getPk()).isEqualTo(DEFAULT_PK);
        assertThat(testScanningRegistration.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testScanningRegistration.getStationID()).isEqualTo(DEFAULT_STATION_ID);
        assertThat(testScanningRegistration.getScanType()).isEqualTo(DEFAULT_SCAN_TYPE);
        assertThat(testScanningRegistration.getDefectCode()).isEqualTo(DEFAULT_DEFECT_CODE);
        assertThat(testScanningRegistration.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testScanningRegistration.getSubSn()).isEqualTo(DEFAULT_SUB_SN);
    }

    @Test
    @Transactional
    public void createScanningRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scanningRegistrationRepository.findAll().size();

        // Create the ScanningRegistration with an existing ID
        scanningRegistration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScanningRegistrationMockMvc.perform(post("/api/scanning-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scanningRegistration)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ScanningRegistration> scanningRegistrationList = scanningRegistrationRepository.findAll();
        assertThat(scanningRegistrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllScanningRegistrations() throws Exception {
        // Initialize the database
        scanningRegistrationRepository.saveAndFlush(scanningRegistration);

        // Get all the scanningRegistrationList
        restScanningRegistrationMockMvc.perform(get("/api/scanning-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scanningRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].pk").value(hasItem(DEFAULT_PK)))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].stationID").value(hasItem(DEFAULT_STATION_ID.toString())))
            .andExpect(jsonPath("$.[*].scanType").value(hasItem(DEFAULT_SCAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].defectCode").value(hasItem(DEFAULT_DEFECT_CODE.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].subSn").value(hasItem(DEFAULT_SUB_SN.toString())));
    }

    @Test
    @Transactional
    public void getScanningRegistration() throws Exception {
        // Initialize the database
        scanningRegistrationRepository.saveAndFlush(scanningRegistration);

        // Get the scanningRegistration
        restScanningRegistrationMockMvc.perform(get("/api/scanning-registrations/{id}", scanningRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scanningRegistration.getId().intValue()))
            .andExpect(jsonPath("$.pk").value(DEFAULT_PK))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.stationID").value(DEFAULT_STATION_ID.toString()))
            .andExpect(jsonPath("$.scanType").value(DEFAULT_SCAN_TYPE.toString()))
            .andExpect(jsonPath("$.defectCode").value(DEFAULT_DEFECT_CODE.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.subSn").value(DEFAULT_SUB_SN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScanningRegistration() throws Exception {
        // Get the scanningRegistration
        restScanningRegistrationMockMvc.perform(get("/api/scanning-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScanningRegistration() throws Exception {
        // Initialize the database
        scanningRegistrationRepository.saveAndFlush(scanningRegistration);
        int databaseSizeBeforeUpdate = scanningRegistrationRepository.findAll().size();

        // Update the scanningRegistration
        ScanningRegistration updatedScanningRegistration = scanningRegistrationRepository.findOne(scanningRegistration.getId());
        updatedScanningRegistration
            .pk(UPDATED_PK)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .stationID(UPDATED_STATION_ID)
            .scanType(UPDATED_SCAN_TYPE)
            .defectCode(UPDATED_DEFECT_CODE)
            .result(UPDATED_RESULT)
            .subSn(UPDATED_SUB_SN);

        restScanningRegistrationMockMvc.perform(put("/api/scanning-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScanningRegistration)))
            .andExpect(status().isOk());

        // Validate the ScanningRegistration in the database
        List<ScanningRegistration> scanningRegistrationList = scanningRegistrationRepository.findAll();
        assertThat(scanningRegistrationList).hasSize(databaseSizeBeforeUpdate);
        ScanningRegistration testScanningRegistration = scanningRegistrationList.get(scanningRegistrationList.size() - 1);
        assertThat(testScanningRegistration.getPk()).isEqualTo(UPDATED_PK);
        assertThat(testScanningRegistration.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testScanningRegistration.getStationID()).isEqualTo(UPDATED_STATION_ID);
        assertThat(testScanningRegistration.getScanType()).isEqualTo(UPDATED_SCAN_TYPE);
        assertThat(testScanningRegistration.getDefectCode()).isEqualTo(UPDATED_DEFECT_CODE);
        assertThat(testScanningRegistration.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testScanningRegistration.getSubSn()).isEqualTo(UPDATED_SUB_SN);
    }

    @Test
    @Transactional
    public void updateNonExistingScanningRegistration() throws Exception {
        int databaseSizeBeforeUpdate = scanningRegistrationRepository.findAll().size();

        // Create the ScanningRegistration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScanningRegistrationMockMvc.perform(put("/api/scanning-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scanningRegistration)))
            .andExpect(status().isCreated());

        // Validate the ScanningRegistration in the database
        List<ScanningRegistration> scanningRegistrationList = scanningRegistrationRepository.findAll();
        assertThat(scanningRegistrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScanningRegistration() throws Exception {
        // Initialize the database
        scanningRegistrationRepository.saveAndFlush(scanningRegistration);
        int databaseSizeBeforeDelete = scanningRegistrationRepository.findAll().size();

        // Get the scanningRegistration
        restScanningRegistrationMockMvc.perform(delete("/api/scanning-registrations/{id}", scanningRegistration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ScanningRegistration> scanningRegistrationList = scanningRegistrationRepository.findAll();
        assertThat(scanningRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScanningRegistration.class);
    }
}
