package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.repository.TransportTaskRepository;
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
 * Test class for the TransportTaskResource REST controller.
 *
 * @see TransportTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class TransportTaskResourceIntTest {

    private static final String DEFAULT_FUN_ID = "AAAAAAAAAA";
    private static final String UPDATED_FUN_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERIAL_ID = 1;
    private static final Integer UPDATED_SERIAL_ID = 2;

    private static final Integer DEFAULT_TASK_ID = 1;
    private static final Integer UPDATED_TASK_ID = 2;

    private static final String DEFAULT_TASK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TASK_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_PRTY = "AAAAAAAAAA";
    private static final String UPDATED_TASK_PRTY = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_TASK_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_L_PN = "AAAAAAAAAA";
    private static final String UPDATED_L_PN = "BBBBBBBBBB";

    private static final String DEFAULT_FRM_POS = "AAAAAAAAAA";
    private static final String UPDATED_FRM_POS = "BBBBBBBBBB";

    private static final String DEFAULT_FRM_POS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FRM_POS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TO_POS = "AAAAAAAAAA";
    private static final String UPDATED_TO_POS = "BBBBBBBBBB";

    private static final String DEFAULT_TO_POS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TO_POS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OP_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_OP_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private TransportTaskRepository transportTaskRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransportTaskMockMvc;

    private TransportTask transportTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransportTaskResource transportTaskResource = new TransportTaskResource(transportTaskRepository);
        this.restTransportTaskMockMvc = MockMvcBuilders.standaloneSetup(transportTaskResource)
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
    public static TransportTask createEntity(EntityManager em) {
        TransportTask transportTask = new TransportTask()
            .funID(DEFAULT_FUN_ID)
            .serialID(DEFAULT_SERIAL_ID)
            .taskID(DEFAULT_TASK_ID)
            .taskType(DEFAULT_TASK_TYPE)
            .taskPrty(DEFAULT_TASK_PRTY)
            .taskFlag(DEFAULT_TASK_FLAG)
            .lPN(DEFAULT_L_PN)
            .frmPos(DEFAULT_FRM_POS)
            .frmPosType(DEFAULT_FRM_POS_TYPE)
            .toPos(DEFAULT_TO_POS)
            .toPosType(DEFAULT_TO_POS_TYPE)
            .opFlag(DEFAULT_OP_FLAG)
            .remark(DEFAULT_REMARK);
        return transportTask;
    }

    @Before
    public void initTest() {
        transportTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransportTask() throws Exception {
        int databaseSizeBeforeCreate = transportTaskRepository.findAll().size();

        // Create the TransportTask
        restTransportTaskMockMvc.perform(post("/api/transport-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportTask)))
            .andExpect(status().isCreated());

        // Validate the TransportTask in the database
        List<TransportTask> transportTaskList = transportTaskRepository.findAll();
        assertThat(transportTaskList).hasSize(databaseSizeBeforeCreate + 1);
        TransportTask testTransportTask = transportTaskList.get(transportTaskList.size() - 1);
        assertThat(testTransportTask.getFunID()).isEqualTo(DEFAULT_FUN_ID);
        assertThat(testTransportTask.getSerialID()).isEqualTo(DEFAULT_SERIAL_ID);
        assertThat(testTransportTask.getTaskID()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testTransportTask.getTaskType()).isEqualTo(DEFAULT_TASK_TYPE);
        assertThat(testTransportTask.getTaskPrty()).isEqualTo(DEFAULT_TASK_PRTY);
        assertThat(testTransportTask.getTaskFlag()).isEqualTo(DEFAULT_TASK_FLAG);
        assertThat(testTransportTask.getlPN()).isEqualTo(DEFAULT_L_PN);
        assertThat(testTransportTask.getFrmPos()).isEqualTo(DEFAULT_FRM_POS);
        assertThat(testTransportTask.getFrmPosType()).isEqualTo(DEFAULT_FRM_POS_TYPE);
        assertThat(testTransportTask.getToPos()).isEqualTo(DEFAULT_TO_POS);
        assertThat(testTransportTask.getToPosType()).isEqualTo(DEFAULT_TO_POS_TYPE);
        assertThat(testTransportTask.getOpFlag()).isEqualTo(DEFAULT_OP_FLAG);
        assertThat(testTransportTask.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createTransportTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportTaskRepository.findAll().size();

        // Create the TransportTask with an existing ID
        transportTask.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportTaskMockMvc.perform(post("/api/transport-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportTask)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransportTask> transportTaskList = transportTaskRepository.findAll();
        assertThat(transportTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransportTasks() throws Exception {
        // Initialize the database
        transportTaskRepository.saveAndFlush(transportTask);

        // Get all the transportTaskList
        restTransportTaskMockMvc.perform(get("/api/transport-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transportTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].funID").value(hasItem(DEFAULT_FUN_ID.toString())))
            .andExpect(jsonPath("$.[*].serialID").value(hasItem(DEFAULT_SERIAL_ID)))
            .andExpect(jsonPath("$.[*].taskID").value(hasItem(DEFAULT_TASK_ID)))
            .andExpect(jsonPath("$.[*].taskType").value(hasItem(DEFAULT_TASK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].taskPrty").value(hasItem(DEFAULT_TASK_PRTY.toString())))
            .andExpect(jsonPath("$.[*].taskFlag").value(hasItem(DEFAULT_TASK_FLAG.toString())))
            .andExpect(jsonPath("$.[*].lPN").value(hasItem(DEFAULT_L_PN.toString())))
            .andExpect(jsonPath("$.[*].frmPos").value(hasItem(DEFAULT_FRM_POS.toString())))
            .andExpect(jsonPath("$.[*].frmPosType").value(hasItem(DEFAULT_FRM_POS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].toPos").value(hasItem(DEFAULT_TO_POS.toString())))
            .andExpect(jsonPath("$.[*].toPosType").value(hasItem(DEFAULT_TO_POS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].opFlag").value(hasItem(DEFAULT_OP_FLAG.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getTransportTask() throws Exception {
        // Initialize the database
        transportTaskRepository.saveAndFlush(transportTask);

        // Get the transportTask
        restTransportTaskMockMvc.perform(get("/api/transport-tasks/{id}", transportTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transportTask.getId().intValue()))
            .andExpect(jsonPath("$.funID").value(DEFAULT_FUN_ID.toString()))
            .andExpect(jsonPath("$.serialID").value(DEFAULT_SERIAL_ID))
            .andExpect(jsonPath("$.taskID").value(DEFAULT_TASK_ID))
            .andExpect(jsonPath("$.taskType").value(DEFAULT_TASK_TYPE.toString()))
            .andExpect(jsonPath("$.taskPrty").value(DEFAULT_TASK_PRTY.toString()))
            .andExpect(jsonPath("$.taskFlag").value(DEFAULT_TASK_FLAG.toString()))
            .andExpect(jsonPath("$.lPN").value(DEFAULT_L_PN.toString()))
            .andExpect(jsonPath("$.frmPos").value(DEFAULT_FRM_POS.toString()))
            .andExpect(jsonPath("$.frmPosType").value(DEFAULT_FRM_POS_TYPE.toString()))
            .andExpect(jsonPath("$.toPos").value(DEFAULT_TO_POS.toString()))
            .andExpect(jsonPath("$.toPosType").value(DEFAULT_TO_POS_TYPE.toString()))
            .andExpect(jsonPath("$.opFlag").value(DEFAULT_OP_FLAG.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransportTask() throws Exception {
        // Get the transportTask
        restTransportTaskMockMvc.perform(get("/api/transport-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransportTask() throws Exception {
        // Initialize the database
        transportTaskRepository.saveAndFlush(transportTask);
        int databaseSizeBeforeUpdate = transportTaskRepository.findAll().size();

        // Update the transportTask
        TransportTask updatedTransportTask = transportTaskRepository.findOne(transportTask.getId());
        updatedTransportTask
            .funID(UPDATED_FUN_ID)
            .serialID(UPDATED_SERIAL_ID)
            .taskID(UPDATED_TASK_ID)
            .taskType(UPDATED_TASK_TYPE)
            .taskPrty(UPDATED_TASK_PRTY)
            .taskFlag(UPDATED_TASK_FLAG)
            .lPN(UPDATED_L_PN)
            .frmPos(UPDATED_FRM_POS)
            .frmPosType(UPDATED_FRM_POS_TYPE)
            .toPos(UPDATED_TO_POS)
            .toPosType(UPDATED_TO_POS_TYPE)
            .opFlag(UPDATED_OP_FLAG)
            .remark(UPDATED_REMARK);

        restTransportTaskMockMvc.perform(put("/api/transport-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransportTask)))
            .andExpect(status().isOk());

        // Validate the TransportTask in the database
        List<TransportTask> transportTaskList = transportTaskRepository.findAll();
        assertThat(transportTaskList).hasSize(databaseSizeBeforeUpdate);
        TransportTask testTransportTask = transportTaskList.get(transportTaskList.size() - 1);
        assertThat(testTransportTask.getFunID()).isEqualTo(UPDATED_FUN_ID);
        assertThat(testTransportTask.getSerialID()).isEqualTo(UPDATED_SERIAL_ID);
        assertThat(testTransportTask.getTaskID()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTransportTask.getTaskType()).isEqualTo(UPDATED_TASK_TYPE);
        assertThat(testTransportTask.getTaskPrty()).isEqualTo(UPDATED_TASK_PRTY);
        assertThat(testTransportTask.getTaskFlag()).isEqualTo(UPDATED_TASK_FLAG);
        assertThat(testTransportTask.getlPN()).isEqualTo(UPDATED_L_PN);
        assertThat(testTransportTask.getFrmPos()).isEqualTo(UPDATED_FRM_POS);
        assertThat(testTransportTask.getFrmPosType()).isEqualTo(UPDATED_FRM_POS_TYPE);
        assertThat(testTransportTask.getToPos()).isEqualTo(UPDATED_TO_POS);
        assertThat(testTransportTask.getToPosType()).isEqualTo(UPDATED_TO_POS_TYPE);
        assertThat(testTransportTask.getOpFlag()).isEqualTo(UPDATED_OP_FLAG);
        assertThat(testTransportTask.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingTransportTask() throws Exception {
        int databaseSizeBeforeUpdate = transportTaskRepository.findAll().size();

        // Create the TransportTask

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransportTaskMockMvc.perform(put("/api/transport-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transportTask)))
            .andExpect(status().isCreated());

        // Validate the TransportTask in the database
        List<TransportTask> transportTaskList = transportTaskRepository.findAll();
        assertThat(transportTaskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransportTask() throws Exception {
        // Initialize the database
        transportTaskRepository.saveAndFlush(transportTask);
        int databaseSizeBeforeDelete = transportTaskRepository.findAll().size();

        // Get the transportTask
        restTransportTaskMockMvc.perform(delete("/api/transport-tasks/{id}", transportTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransportTask> transportTaskList = transportTaskRepository.findAll();
        assertThat(transportTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransportTask.class);
    }
}
