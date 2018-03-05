package com.mj.holley.ims.web.rest;

import com.mj.holley.ims.HolleyImsApp;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.repository.OrderInfoRepository;
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
 * Test class for the OrderInfoResource REST controller.
 *
 * @see OrderInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HolleyImsApp.class)
public class OrderInfoResourceIntTest {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEF_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEF_DESCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_DEF_DESCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_LINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_B_OPID = "AAAAAAAAAA";
    private static final String UPDATED_B_OPID = "BBBBBBBBBB";

    private static final String DEFAULT_P_PR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_P_PR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPART_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEPART_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderInfoMockMvc;

    private OrderInfo orderInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            OrderInfoResource orderInfoResource = new OrderInfoResource(orderInfoRepository);
        this.restOrderInfoMockMvc = MockMvcBuilders.standaloneSetup(orderInfoResource)
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
    public static OrderInfo createEntity(EntityManager em) {
        OrderInfo orderInfo = new OrderInfo()
                .orderID(DEFAULT_ORDER_ID)
                .defID(DEFAULT_DEF_ID)
                .defDescript(DEFAULT_DEF_DESCRIPT)
                .lineID(DEFAULT_LINE_ID)
                .bOPID(DEFAULT_B_OPID)
                .pPRName(DEFAULT_P_PR_NAME)
                .departID(DEFAULT_DEPART_ID)
                .quantity(DEFAULT_QUANTITY);
        return orderInfo;
    }

    @Before
    public void initTest() {
        orderInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderInfo() throws Exception {
        int databaseSizeBeforeCreate = orderInfoRepository.findAll().size();

        // Create the OrderInfo

        restOrderInfoMockMvc.perform(post("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderInfo)))
            .andExpect(status().isCreated());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeCreate + 1);
        OrderInfo testOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
        assertThat(testOrderInfo.getOrderID()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrderInfo.getDefID()).isEqualTo(DEFAULT_DEF_ID);
        assertThat(testOrderInfo.getDefDescript()).isEqualTo(DEFAULT_DEF_DESCRIPT);
        assertThat(testOrderInfo.getLineID()).isEqualTo(DEFAULT_LINE_ID);
        assertThat(testOrderInfo.getbOPID()).isEqualTo(DEFAULT_B_OPID);
        assertThat(testOrderInfo.getpPRName()).isEqualTo(DEFAULT_P_PR_NAME);
        assertThat(testOrderInfo.getDepartID()).isEqualTo(DEFAULT_DEPART_ID);
        assertThat(testOrderInfo.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createOrderInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderInfoRepository.findAll().size();

        // Create the OrderInfo with an existing ID
        OrderInfo existingOrderInfo = new OrderInfo();
        existingOrderInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderInfoMockMvc.perform(post("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOrderInfo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderInfos() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);

        // Get all the orderInfoList
        restOrderInfoMockMvc.perform(get("/api/order-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].defID").value(hasItem(DEFAULT_DEF_ID.toString())))
            .andExpect(jsonPath("$.[*].defDescript").value(hasItem(DEFAULT_DEF_DESCRIPT.toString())))
            .andExpect(jsonPath("$.[*].lineID").value(hasItem(DEFAULT_LINE_ID.toString())))
            .andExpect(jsonPath("$.[*].bOPID").value(hasItem(DEFAULT_B_OPID.toString())))
            .andExpect(jsonPath("$.[*].pPRName").value(hasItem(DEFAULT_P_PR_NAME.toString())))
            .andExpect(jsonPath("$.[*].departID").value(hasItem(DEFAULT_DEPART_ID.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getOrderInfo() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);

        // Get the orderInfo
        restOrderInfoMockMvc.perform(get("/api/order-infos/{id}", orderInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderInfo.getId().intValue()))
            .andExpect(jsonPath("$.orderID").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.defID").value(DEFAULT_DEF_ID.toString()))
            .andExpect(jsonPath("$.defDescript").value(DEFAULT_DEF_DESCRIPT.toString()))
            .andExpect(jsonPath("$.lineID").value(DEFAULT_LINE_ID.toString()))
            .andExpect(jsonPath("$.bOPID").value(DEFAULT_B_OPID.toString()))
            .andExpect(jsonPath("$.pPRName").value(DEFAULT_P_PR_NAME.toString()))
            .andExpect(jsonPath("$.departID").value(DEFAULT_DEPART_ID.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingOrderInfo() throws Exception {
        // Get the orderInfo
        restOrderInfoMockMvc.perform(get("/api/order-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderInfo() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);
        int databaseSizeBeforeUpdate = orderInfoRepository.findAll().size();

        // Update the orderInfo
        OrderInfo updatedOrderInfo = orderInfoRepository.findOne(orderInfo.getId());
        updatedOrderInfo
                .orderID(UPDATED_ORDER_ID)
                .defID(UPDATED_DEF_ID)
                .defDescript(UPDATED_DEF_DESCRIPT)
                .lineID(UPDATED_LINE_ID)
                .bOPID(UPDATED_B_OPID)
                .pPRName(UPDATED_P_PR_NAME)
                .departID(UPDATED_DEPART_ID)
                .quantity(UPDATED_QUANTITY);

        restOrderInfoMockMvc.perform(put("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderInfo)))
            .andExpect(status().isOk());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeUpdate);
        OrderInfo testOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
        assertThat(testOrderInfo.getOrderID()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrderInfo.getDefID()).isEqualTo(UPDATED_DEF_ID);
        assertThat(testOrderInfo.getDefDescript()).isEqualTo(UPDATED_DEF_DESCRIPT);
        assertThat(testOrderInfo.getLineID()).isEqualTo(UPDATED_LINE_ID);
        assertThat(testOrderInfo.getbOPID()).isEqualTo(UPDATED_B_OPID);
        assertThat(testOrderInfo.getpPRName()).isEqualTo(UPDATED_P_PR_NAME);
        assertThat(testOrderInfo.getDepartID()).isEqualTo(UPDATED_DEPART_ID);
        assertThat(testOrderInfo.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderInfo() throws Exception {
        int databaseSizeBeforeUpdate = orderInfoRepository.findAll().size();

        // Create the OrderInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderInfoMockMvc.perform(put("/api/order-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderInfo)))
            .andExpect(status().isCreated());

        // Validate the OrderInfo in the database
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderInfo() throws Exception {
        // Initialize the database
        orderInfoRepository.saveAndFlush(orderInfo);
        int databaseSizeBeforeDelete = orderInfoRepository.findAll().size();

        // Get the orderInfo
        restOrderInfoMockMvc.perform(delete("/api/order-infos/{id}", orderInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
        assertThat(orderInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderInfo.class);
    }
}
