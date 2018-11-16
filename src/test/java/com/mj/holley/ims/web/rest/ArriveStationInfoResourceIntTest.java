package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.ArriveStationInfo;
import com.mj.holley.ims.repository.ArriveStationInfoRepository;
import com.mj.holley.ims.service.ArriveStationInfoService;
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


import static com.mj.holley.ims.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArriveStationInfoResource REST controller.
 *
 * @see ArriveStationInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class ArriveStationInfoResourceIntTest {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZP_02 = 1;
    private static final Integer UPDATED_ZP_02 = 2;

    private static final Integer DEFAULT_ZP_03 = 1;
    private static final Integer UPDATED_ZP_03 = 2;

    private static final Integer DEFAULT_ZP_04 = 1;
    private static final Integer UPDATED_ZP_04 = 2;

    private static final Integer DEFAULT_ZP_05 = 1;
    private static final Integer UPDATED_ZP_05 = 2;

    private static final Integer DEFAULT_ZP_06 = 1;
    private static final Integer UPDATED_ZP_06 = 2;

    private static final Integer DEFAULT_ZP_07 = 1;
    private static final Integer UPDATED_ZP_07 = 2;

    private static final Integer DEFAULT_ZP_08 = 1;
    private static final Integer UPDATED_ZP_08 = 2;

    private static final Integer DEFAULT_ZP_09 = 1;
    private static final Integer UPDATED_ZP_09 = 2;

    @Autowired
    private ArriveStationInfoRepository arriveStationInfoRepository;

    

    @Autowired
    private ArriveStationInfoService arriveStationInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArriveStationInfoMockMvc;

    private ArriveStationInfo arriveStationInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArriveStationInfoResource arriveStationInfoResource = new ArriveStationInfoResource(arriveStationInfoService);
        this.restArriveStationInfoMockMvc = MockMvcBuilders.standaloneSetup(arriveStationInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArriveStationInfo createEntity(EntityManager em) {
        ArriveStationInfo arriveStationInfo = new ArriveStationInfo()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .zp02(DEFAULT_ZP_02)
            .zp03(DEFAULT_ZP_03)
            .zp04(DEFAULT_ZP_04)
            .zp05(DEFAULT_ZP_05)
            .zp06(DEFAULT_ZP_06)
            .zp07(DEFAULT_ZP_07)
            .zp08(DEFAULT_ZP_08)
            .zp09(DEFAULT_ZP_09);
        return arriveStationInfo;
    }

    @Before
    public void initTest() {
        arriveStationInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArriveStationInfo() throws Exception {
        int databaseSizeBeforeCreate = arriveStationInfoRepository.findAll().size();

        // Create the ArriveStationInfo
        restArriveStationInfoMockMvc.perform(post("/api/arrive-station-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arriveStationInfo)))
            .andExpect(status().isCreated());

        // Validate the ArriveStationInfo in the database
        List<ArriveStationInfo> arriveStationInfoList = arriveStationInfoRepository.findAll();
        assertThat(arriveStationInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ArriveStationInfo testArriveStationInfo = arriveStationInfoList.get(arriveStationInfoList.size() - 1);
        assertThat(testArriveStationInfo.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testArriveStationInfo.getZp02()).isEqualTo(DEFAULT_ZP_02);
        assertThat(testArriveStationInfo.getZp03()).isEqualTo(DEFAULT_ZP_03);
        assertThat(testArriveStationInfo.getZp04()).isEqualTo(DEFAULT_ZP_04);
        assertThat(testArriveStationInfo.getZp05()).isEqualTo(DEFAULT_ZP_05);
        assertThat(testArriveStationInfo.getZp06()).isEqualTo(DEFAULT_ZP_06);
        assertThat(testArriveStationInfo.getZp07()).isEqualTo(DEFAULT_ZP_07);
        assertThat(testArriveStationInfo.getZp08()).isEqualTo(DEFAULT_ZP_08);
        assertThat(testArriveStationInfo.getZp09()).isEqualTo(DEFAULT_ZP_09);
    }

    @Test
    @Transactional
    public void createArriveStationInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arriveStationInfoRepository.findAll().size();

        // Create the ArriveStationInfo with an existing ID
        arriveStationInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArriveStationInfoMockMvc.perform(post("/api/arrive-station-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arriveStationInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ArriveStationInfo in the database
        List<ArriveStationInfo> arriveStationInfoList = arriveStationInfoRepository.findAll();
        assertThat(arriveStationInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArriveStationInfos() throws Exception {
        // Initialize the database
        arriveStationInfoRepository.saveAndFlush(arriveStationInfo);

        // Get all the arriveStationInfoList
        restArriveStationInfoMockMvc.perform(get("/api/arrive-station-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arriveStationInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].zp02").value(hasItem(DEFAULT_ZP_02)))
            .andExpect(jsonPath("$.[*].zp03").value(hasItem(DEFAULT_ZP_03)))
            .andExpect(jsonPath("$.[*].zp04").value(hasItem(DEFAULT_ZP_04)))
            .andExpect(jsonPath("$.[*].zp05").value(hasItem(DEFAULT_ZP_05)))
            .andExpect(jsonPath("$.[*].zp06").value(hasItem(DEFAULT_ZP_06)))
            .andExpect(jsonPath("$.[*].zp07").value(hasItem(DEFAULT_ZP_07)))
            .andExpect(jsonPath("$.[*].zp08").value(hasItem(DEFAULT_ZP_08)))
            .andExpect(jsonPath("$.[*].zp09").value(hasItem(DEFAULT_ZP_09)));
    }
    

    @Test
    @Transactional
    public void getArriveStationInfo() throws Exception {
        // Initialize the database
        arriveStationInfoRepository.saveAndFlush(arriveStationInfo);

        // Get the arriveStationInfo
        restArriveStationInfoMockMvc.perform(get("/api/arrive-station-infos/{id}", arriveStationInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arriveStationInfo.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.zp02").value(DEFAULT_ZP_02))
            .andExpect(jsonPath("$.zp03").value(DEFAULT_ZP_03))
            .andExpect(jsonPath("$.zp04").value(DEFAULT_ZP_04))
            .andExpect(jsonPath("$.zp05").value(DEFAULT_ZP_05))
            .andExpect(jsonPath("$.zp06").value(DEFAULT_ZP_06))
            .andExpect(jsonPath("$.zp07").value(DEFAULT_ZP_07))
            .andExpect(jsonPath("$.zp08").value(DEFAULT_ZP_08))
            .andExpect(jsonPath("$.zp09").value(DEFAULT_ZP_09));
    }
    @Test
    @Transactional
    public void getNonExistingArriveStationInfo() throws Exception {
        // Get the arriveStationInfo
        restArriveStationInfoMockMvc.perform(get("/api/arrive-station-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArriveStationInfo() throws Exception {
        // Initialize the database
        arriveStationInfoService.save(arriveStationInfo);

        int databaseSizeBeforeUpdate = arriveStationInfoRepository.findAll().size();

        // Update the arriveStationInfo
        ArriveStationInfo updatedArriveStationInfo = arriveStationInfoRepository.findById(arriveStationInfo.getId()).get();
        // Disconnect from session so that the updates on updatedArriveStationInfo are not directly saved in db
        em.detach(updatedArriveStationInfo);
        updatedArriveStationInfo
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .zp02(UPDATED_ZP_02)
            .zp03(UPDATED_ZP_03)
            .zp04(UPDATED_ZP_04)
            .zp05(UPDATED_ZP_05)
            .zp06(UPDATED_ZP_06)
            .zp07(UPDATED_ZP_07)
            .zp08(UPDATED_ZP_08)
            .zp09(UPDATED_ZP_09);

        restArriveStationInfoMockMvc.perform(put("/api/arrive-station-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArriveStationInfo)))
            .andExpect(status().isOk());

        // Validate the ArriveStationInfo in the database
        List<ArriveStationInfo> arriveStationInfoList = arriveStationInfoRepository.findAll();
        assertThat(arriveStationInfoList).hasSize(databaseSizeBeforeUpdate);
        ArriveStationInfo testArriveStationInfo = arriveStationInfoList.get(arriveStationInfoList.size() - 1);
        assertThat(testArriveStationInfo.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testArriveStationInfo.getZp02()).isEqualTo(UPDATED_ZP_02);
        assertThat(testArriveStationInfo.getZp03()).isEqualTo(UPDATED_ZP_03);
        assertThat(testArriveStationInfo.getZp04()).isEqualTo(UPDATED_ZP_04);
        assertThat(testArriveStationInfo.getZp05()).isEqualTo(UPDATED_ZP_05);
        assertThat(testArriveStationInfo.getZp06()).isEqualTo(UPDATED_ZP_06);
        assertThat(testArriveStationInfo.getZp07()).isEqualTo(UPDATED_ZP_07);
        assertThat(testArriveStationInfo.getZp08()).isEqualTo(UPDATED_ZP_08);
        assertThat(testArriveStationInfo.getZp09()).isEqualTo(UPDATED_ZP_09);
    }

    @Test
    @Transactional
    public void updateNonExistingArriveStationInfo() throws Exception {
        int databaseSizeBeforeUpdate = arriveStationInfoRepository.findAll().size();

        // Create the ArriveStationInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArriveStationInfoMockMvc.perform(put("/api/arrive-station-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arriveStationInfo)))
            .andExpect(status().isBadRequest());

        // Validate the ArriveStationInfo in the database
        List<ArriveStationInfo> arriveStationInfoList = arriveStationInfoRepository.findAll();
        assertThat(arriveStationInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArriveStationInfo() throws Exception {
        // Initialize the database
        arriveStationInfoService.save(arriveStationInfo);

        int databaseSizeBeforeDelete = arriveStationInfoRepository.findAll().size();

        // Get the arriveStationInfo
        restArriveStationInfoMockMvc.perform(delete("/api/arrive-station-infos/{id}", arriveStationInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArriveStationInfo> arriveStationInfoList = arriveStationInfoRepository.findAll();
        assertThat(arriveStationInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArriveStationInfo.class);
        ArriveStationInfo arriveStationInfo1 = new ArriveStationInfo();
        arriveStationInfo1.setId(1L);
        ArriveStationInfo arriveStationInfo2 = new ArriveStationInfo();
        arriveStationInfo2.setId(arriveStationInfo1.getId());
        assertThat(arriveStationInfo1).isEqualTo(arriveStationInfo2);
        arriveStationInfo2.setId(2L);
        assertThat(arriveStationInfo1).isNotEqualTo(arriveStationInfo2);
        arriveStationInfo1.setId(null);
        assertThat(arriveStationInfo1).isNotEqualTo(arriveStationInfo2);
    }
}
