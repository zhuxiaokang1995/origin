package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.AbnormalInformation;
import com.mj.holley.ims.repository.AbnormalInformationRepository;
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
 * Test class for the AbnormalInformationResource REST controller.
 *
 * @see AbnormalInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class AbnormalInformationResourceIntTest {

    private static final String DEFAULT_LINE_STATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINE_STATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ABNORMAL_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_ABNORMAL_CAUSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ABNORMAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ABNORMAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private AbnormalInformationRepository abnormalInformationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbnormalInformationMockMvc;

    private AbnormalInformation abnormalInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AbnormalInformationResource abnormalInformationResource = new AbnormalInformationResource(abnormalInformationRepository);
        this.restAbnormalInformationMockMvc = MockMvcBuilders.standaloneSetup(abnormalInformationResource)
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
    public static AbnormalInformation createEntity(EntityManager em) {
        AbnormalInformation abnormalInformation = new AbnormalInformation()
            .lineStationId(DEFAULT_LINE_STATION_ID)
            .abnormalCause(DEFAULT_ABNORMAL_CAUSE)
            .abnormalTime(DEFAULT_ABNORMAL_TIME)
            .remark(DEFAULT_REMARK);
        return abnormalInformation;
    }

    @Before
    public void initTest() {
        abnormalInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbnormalInformation() throws Exception {
        int databaseSizeBeforeCreate = abnormalInformationRepository.findAll().size();

        // Create the AbnormalInformation
        restAbnormalInformationMockMvc.perform(post("/api/abnormal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abnormalInformation)))
            .andExpect(status().isCreated());

        // Validate the AbnormalInformation in the database
        List<AbnormalInformation> abnormalInformationList = abnormalInformationRepository.findAll();
        assertThat(abnormalInformationList).hasSize(databaseSizeBeforeCreate + 1);
        AbnormalInformation testAbnormalInformation = abnormalInformationList.get(abnormalInformationList.size() - 1);
        assertThat(testAbnormalInformation.getLineStationId()).isEqualTo(DEFAULT_LINE_STATION_ID);
        assertThat(testAbnormalInformation.getAbnormalCause()).isEqualTo(DEFAULT_ABNORMAL_CAUSE);
        assertThat(testAbnormalInformation.getAbnormalTime()).isEqualTo(DEFAULT_ABNORMAL_TIME);
        assertThat(testAbnormalInformation.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createAbnormalInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abnormalInformationRepository.findAll().size();

        // Create the AbnormalInformation with an existing ID
        abnormalInformation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbnormalInformationMockMvc.perform(post("/api/abnormal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abnormalInformation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AbnormalInformation> abnormalInformationList = abnormalInformationRepository.findAll();
        assertThat(abnormalInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbnormalInformations() throws Exception {
        // Initialize the database
        abnormalInformationRepository.saveAndFlush(abnormalInformation);

        // Get all the abnormalInformationList
        restAbnormalInformationMockMvc.perform(get("/api/abnormal-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abnormalInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineStationId").value(hasItem(DEFAULT_LINE_STATION_ID.toString())))
            .andExpect(jsonPath("$.[*].abnormalCause").value(hasItem(DEFAULT_ABNORMAL_CAUSE.toString())))
            .andExpect(jsonPath("$.[*].abnormalTime").value(hasItem(sameInstant(DEFAULT_ABNORMAL_TIME))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getAbnormalInformation() throws Exception {
        // Initialize the database
        abnormalInformationRepository.saveAndFlush(abnormalInformation);

        // Get the abnormalInformation
        restAbnormalInformationMockMvc.perform(get("/api/abnormal-informations/{id}", abnormalInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abnormalInformation.getId().intValue()))
            .andExpect(jsonPath("$.lineStationId").value(DEFAULT_LINE_STATION_ID.toString()))
            .andExpect(jsonPath("$.abnormalCause").value(DEFAULT_ABNORMAL_CAUSE.toString()))
            .andExpect(jsonPath("$.abnormalTime").value(sameInstant(DEFAULT_ABNORMAL_TIME)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbnormalInformation() throws Exception {
        // Get the abnormalInformation
        restAbnormalInformationMockMvc.perform(get("/api/abnormal-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbnormalInformation() throws Exception {
        // Initialize the database
        abnormalInformationRepository.saveAndFlush(abnormalInformation);
        int databaseSizeBeforeUpdate = abnormalInformationRepository.findAll().size();

        // Update the abnormalInformation
        AbnormalInformation updatedAbnormalInformation = abnormalInformationRepository.findOne(abnormalInformation.getId());
        updatedAbnormalInformation
            .lineStationId(UPDATED_LINE_STATION_ID)
            .abnormalCause(UPDATED_ABNORMAL_CAUSE)
            .abnormalTime(UPDATED_ABNORMAL_TIME)
            .remark(UPDATED_REMARK);

        restAbnormalInformationMockMvc.perform(put("/api/abnormal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbnormalInformation)))
            .andExpect(status().isOk());

        // Validate the AbnormalInformation in the database
        List<AbnormalInformation> abnormalInformationList = abnormalInformationRepository.findAll();
        assertThat(abnormalInformationList).hasSize(databaseSizeBeforeUpdate);
        AbnormalInformation testAbnormalInformation = abnormalInformationList.get(abnormalInformationList.size() - 1);
        assertThat(testAbnormalInformation.getLineStationId()).isEqualTo(UPDATED_LINE_STATION_ID);
        assertThat(testAbnormalInformation.getAbnormalCause()).isEqualTo(UPDATED_ABNORMAL_CAUSE);
        assertThat(testAbnormalInformation.getAbnormalTime()).isEqualTo(UPDATED_ABNORMAL_TIME);
        assertThat(testAbnormalInformation.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingAbnormalInformation() throws Exception {
        int databaseSizeBeforeUpdate = abnormalInformationRepository.findAll().size();

        // Create the AbnormalInformation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbnormalInformationMockMvc.perform(put("/api/abnormal-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abnormalInformation)))
            .andExpect(status().isCreated());

        // Validate the AbnormalInformation in the database
        List<AbnormalInformation> abnormalInformationList = abnormalInformationRepository.findAll();
        assertThat(abnormalInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAbnormalInformation() throws Exception {
        // Initialize the database
        abnormalInformationRepository.saveAndFlush(abnormalInformation);
        int databaseSizeBeforeDelete = abnormalInformationRepository.findAll().size();

        // Get the abnormalInformation
        restAbnormalInformationMockMvc.perform(delete("/api/abnormal-informations/{id}", abnormalInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AbnormalInformation> abnormalInformationList = abnormalInformationRepository.findAll();
        assertThat(abnormalInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbnormalInformation.class);
    }
}
