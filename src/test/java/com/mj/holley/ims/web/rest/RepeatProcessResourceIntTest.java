package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.RepeatProcess;
import com.mj.holley.ims.repository.RepeatProcessRepository;
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
 * Test class for the RepeatProcessResource REST controller.
 *
 * @see RepeatProcessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class RepeatProcessResourceIntTest {

    private static final String DEFAULT_PROCESS_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPLE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPLE = "BBBBBBBBBB";

    @Autowired
    private RepeatProcessRepository repeatProcessRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRepeatProcessMockMvc;

    private RepeatProcess repeatProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            RepeatProcessResource repeatProcessResource = new RepeatProcessResource(repeatProcessRepository);
        this.restRepeatProcessMockMvc = MockMvcBuilders.standaloneSetup(repeatProcessResource)
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
    public static RepeatProcess createEntity(EntityManager em) {
        RepeatProcess repeatProcess = new RepeatProcess()
                .processNum(DEFAULT_PROCESS_NUM)
                .processName(DEFAULT_PROCESS_NAME)
                .descriple(DEFAULT_DESCRIPLE);
        return repeatProcess;
    }

    @Before
    public void initTest() {
        repeatProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepeatProcess() throws Exception {
        int databaseSizeBeforeCreate = repeatProcessRepository.findAll().size();

        // Create the RepeatProcess

        restRepeatProcessMockMvc.perform(post("/api/repeat-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatProcess)))
            .andExpect(status().isCreated());

        // Validate the RepeatProcess in the database
        List<RepeatProcess> repeatProcessList = repeatProcessRepository.findAll();
        assertThat(repeatProcessList).hasSize(databaseSizeBeforeCreate + 1);
        RepeatProcess testRepeatProcess = repeatProcessList.get(repeatProcessList.size() - 1);
        assertThat(testRepeatProcess.getProcessNum()).isEqualTo(DEFAULT_PROCESS_NUM);
        assertThat(testRepeatProcess.getProcessName()).isEqualTo(DEFAULT_PROCESS_NAME);
        assertThat(testRepeatProcess.getDescriple()).isEqualTo(DEFAULT_DESCRIPLE);
    }

    @Test
    @Transactional
    public void createRepeatProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repeatProcessRepository.findAll().size();

        // Create the RepeatProcess with an existing ID
        RepeatProcess existingRepeatProcess = new RepeatProcess();
        existingRepeatProcess.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepeatProcessMockMvc.perform(post("/api/repeat-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRepeatProcess)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RepeatProcess> repeatProcessList = repeatProcessRepository.findAll();
        assertThat(repeatProcessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRepeatProcesses() throws Exception {
        // Initialize the database
        repeatProcessRepository.saveAndFlush(repeatProcess);

        // Get all the repeatProcessList
        restRepeatProcessMockMvc.perform(get("/api/repeat-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].processNum").value(hasItem(DEFAULT_PROCESS_NUM.toString())))
            .andExpect(jsonPath("$.[*].processName").value(hasItem(DEFAULT_PROCESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].descriple").value(hasItem(DEFAULT_DESCRIPLE.toString())));
    }

    @Test
    @Transactional
    public void getRepeatProcess() throws Exception {
        // Initialize the database
        repeatProcessRepository.saveAndFlush(repeatProcess);

        // Get the repeatProcess
        restRepeatProcessMockMvc.perform(get("/api/repeat-processes/{id}", repeatProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repeatProcess.getId().intValue()))
            .andExpect(jsonPath("$.processNum").value(DEFAULT_PROCESS_NUM.toString()))
            .andExpect(jsonPath("$.processName").value(DEFAULT_PROCESS_NAME.toString()))
            .andExpect(jsonPath("$.descriple").value(DEFAULT_DESCRIPLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRepeatProcess() throws Exception {
        // Get the repeatProcess
        restRepeatProcessMockMvc.perform(get("/api/repeat-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepeatProcess() throws Exception {
        // Initialize the database
        repeatProcessRepository.saveAndFlush(repeatProcess);
        int databaseSizeBeforeUpdate = repeatProcessRepository.findAll().size();

        // Update the repeatProcess
        RepeatProcess updatedRepeatProcess = repeatProcessRepository.findOne(repeatProcess.getId());
        updatedRepeatProcess
                .processNum(UPDATED_PROCESS_NUM)
                .processName(UPDATED_PROCESS_NAME)
                .descriple(UPDATED_DESCRIPLE);

        restRepeatProcessMockMvc.perform(put("/api/repeat-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRepeatProcess)))
            .andExpect(status().isOk());

        // Validate the RepeatProcess in the database
        List<RepeatProcess> repeatProcessList = repeatProcessRepository.findAll();
        assertThat(repeatProcessList).hasSize(databaseSizeBeforeUpdate);
        RepeatProcess testRepeatProcess = repeatProcessList.get(repeatProcessList.size() - 1);
        assertThat(testRepeatProcess.getProcessNum()).isEqualTo(UPDATED_PROCESS_NUM);
        assertThat(testRepeatProcess.getProcessName()).isEqualTo(UPDATED_PROCESS_NAME);
        assertThat(testRepeatProcess.getDescriple()).isEqualTo(UPDATED_DESCRIPLE);
    }

    @Test
    @Transactional
    public void updateNonExistingRepeatProcess() throws Exception {
        int databaseSizeBeforeUpdate = repeatProcessRepository.findAll().size();

        // Create the RepeatProcess

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRepeatProcessMockMvc.perform(put("/api/repeat-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatProcess)))
            .andExpect(status().isCreated());

        // Validate the RepeatProcess in the database
        List<RepeatProcess> repeatProcessList = repeatProcessRepository.findAll();
        assertThat(repeatProcessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRepeatProcess() throws Exception {
        // Initialize the database
        repeatProcessRepository.saveAndFlush(repeatProcess);
        int databaseSizeBeforeDelete = repeatProcessRepository.findAll().size();

        // Get the repeatProcess
        restRepeatProcessMockMvc.perform(delete("/api/repeat-processes/{id}", repeatProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RepeatProcess> repeatProcessList = repeatProcessRepository.findAll();
        assertThat(repeatProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatProcess.class);
    }
}
