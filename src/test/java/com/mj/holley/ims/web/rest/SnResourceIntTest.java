package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.Sn;
import com.mj.holley.ims.repository.SnRepository;
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
 * Test class for the SnResource REST controller.
 *
 * @see SnResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class SnResourceIntTest {

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HUT_ID = "AAAAAAAAAA";
    private static final String UPDATED_HUT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_BINDING = false;
    private static final Boolean UPDATED_IS_BINDING = true;

    private static final ZonedDateTime DEFAULT_BINDING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BINDING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UNBUNDLING_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UNBUNDLING_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SnRepository snRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSnMockMvc;

    private Sn sn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SnResource snResource = new SnResource(snRepository);
        this.restSnMockMvc = MockMvcBuilders.standaloneSetup(snResource)
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
    public static Sn createEntity(EntityManager em) {
        Sn sn = new Sn()
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .hutID(DEFAULT_HUT_ID)
            .orderID(DEFAULT_ORDER_ID)
            .isBinding(DEFAULT_IS_BINDING)
            .bindingTime(DEFAULT_BINDING_TIME)
            .unbundlingTime(DEFAULT_UNBUNDLING_TIME);
        return sn;
    }

    @Before
    public void initTest() {
        sn = createEntity(em);
    }

    @Test
    @Transactional
    public void createSn() throws Exception {
        int databaseSizeBeforeCreate = snRepository.findAll().size();

        // Create the Sn
        restSnMockMvc.perform(post("/api/sns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sn)))
            .andExpect(status().isCreated());

        // Validate the Sn in the database
        List<Sn> snList = snRepository.findAll();
        assertThat(snList).hasSize(databaseSizeBeforeCreate + 1);
        Sn testSn = snList.get(snList.size() - 1);
        assertThat(testSn.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testSn.getHutID()).isEqualTo(DEFAULT_HUT_ID);
        assertThat(testSn.getOrderID()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testSn.isIsBinding()).isEqualTo(DEFAULT_IS_BINDING);
        assertThat(testSn.getBindingTime()).isEqualTo(DEFAULT_BINDING_TIME);
        assertThat(testSn.getUnbundlingTime()).isEqualTo(DEFAULT_UNBUNDLING_TIME);
    }

    @Test
    @Transactional
    public void createSnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = snRepository.findAll().size();

        // Create the Sn with an existing ID
        sn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSnMockMvc.perform(post("/api/sns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sn)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sn> snList = snRepository.findAll();
        assertThat(snList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSns() throws Exception {
        // Initialize the database
        snRepository.saveAndFlush(sn);

        // Get all the snList
        restSnMockMvc.perform(get("/api/sns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sn.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hutID").value(hasItem(DEFAULT_HUT_ID.toString())))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].isBinding").value(hasItem(DEFAULT_IS_BINDING.booleanValue())))
            .andExpect(jsonPath("$.[*].bindingTime").value(hasItem(sameInstant(DEFAULT_BINDING_TIME))))
            .andExpect(jsonPath("$.[*].unbundlingTime").value(hasItem(sameInstant(DEFAULT_UNBUNDLING_TIME))));
    }

    @Test
    @Transactional
    public void getSn() throws Exception {
        // Initialize the database
        snRepository.saveAndFlush(sn);

        // Get the sn
        restSnMockMvc.perform(get("/api/sns/{id}", sn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sn.getId().intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.hutID").value(DEFAULT_HUT_ID.toString()))
            .andExpect(jsonPath("$.orderID").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.isBinding").value(DEFAULT_IS_BINDING.booleanValue()))
            .andExpect(jsonPath("$.bindingTime").value(sameInstant(DEFAULT_BINDING_TIME)))
            .andExpect(jsonPath("$.unbundlingTime").value(sameInstant(DEFAULT_UNBUNDLING_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSn() throws Exception {
        // Get the sn
        restSnMockMvc.perform(get("/api/sns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSn() throws Exception {
        // Initialize the database
        snRepository.saveAndFlush(sn);
        int databaseSizeBeforeUpdate = snRepository.findAll().size();

        // Update the sn
        Sn updatedSn = snRepository.findOne(sn.getId());
        updatedSn
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .hutID(UPDATED_HUT_ID)
            .orderID(UPDATED_ORDER_ID)
            .isBinding(UPDATED_IS_BINDING)
            .bindingTime(UPDATED_BINDING_TIME)
            .unbundlingTime(UPDATED_UNBUNDLING_TIME);

        restSnMockMvc.perform(put("/api/sns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSn)))
            .andExpect(status().isOk());

        // Validate the Sn in the database
        List<Sn> snList = snRepository.findAll();
        assertThat(snList).hasSize(databaseSizeBeforeUpdate);
        Sn testSn = snList.get(snList.size() - 1);
        assertThat(testSn.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testSn.getHutID()).isEqualTo(UPDATED_HUT_ID);
        assertThat(testSn.getOrderID()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testSn.isIsBinding()).isEqualTo(UPDATED_IS_BINDING);
        assertThat(testSn.getBindingTime()).isEqualTo(UPDATED_BINDING_TIME);
        assertThat(testSn.getUnbundlingTime()).isEqualTo(UPDATED_UNBUNDLING_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSn() throws Exception {
        int databaseSizeBeforeUpdate = snRepository.findAll().size();

        // Create the Sn

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSnMockMvc.perform(put("/api/sns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sn)))
            .andExpect(status().isCreated());

        // Validate the Sn in the database
        List<Sn> snList = snRepository.findAll();
        assertThat(snList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSn() throws Exception {
        // Initialize the database
        snRepository.saveAndFlush(sn);
        int databaseSizeBeforeDelete = snRepository.findAll().size();

        // Get the sn
        restSnMockMvc.perform(delete("/api/sns/{id}", sn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sn> snList = snRepository.findAll();
        assertThat(snList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sn.class);
    }
}
