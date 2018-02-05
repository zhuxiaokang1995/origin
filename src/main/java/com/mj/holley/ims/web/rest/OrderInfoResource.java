package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.repository.OrderInfoRepository;
import com.mj.holley.ims.service.ProcessInformationService;
import com.mj.holley.ims.service.dto.ProcessesDTO;
import com.mj.holley.ims.service.dto.StepsDTO;
import com.mj.holley.ims.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrderInfo.
 */
@RestController
@RequestMapping("/api")
public class OrderInfoResource {

    private final Logger log = LoggerFactory.getLogger(OrderInfoResource.class);

    private static final String ENTITY_NAME = "orderInfo";

    private final OrderInfoRepository orderInfoRepository;

//    @Autowired
//    private ProcessInformationService processInformationService;

    public OrderInfoResource(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    /**
     * POST  /order-infos : Create a new orderInfo.
     *
     * @param orderInfo the orderInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderInfo, or with status 400 (Bad Request) if the orderInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-infos")
    @Timed
    public ResponseEntity<OrderInfo> createOrderInfo(@RequestBody OrderInfo orderInfo) throws URISyntaxException {
        log.debug("REST request to save OrderInfo : {}", orderInfo);
        if (orderInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new orderInfo cannot already have an ID")).body(null);
        }
        OrderInfo result = orderInfoRepository.save(orderInfo);
        return ResponseEntity.created(new URI("/api/order-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-infos : Updates an existing orderInfo.
     *
     * @param orderInfo the orderInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderInfo,
     * or with status 400 (Bad Request) if the orderInfo is not valid,
     * or with status 500 (Internal Server Error) if the orderInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-infos")
    @Timed
    public ResponseEntity<OrderInfo> updateOrderInfo(@RequestBody OrderInfo orderInfo) throws URISyntaxException {
        log.debug("REST request to update OrderInfo : {}", orderInfo);
        if (orderInfo.getId() == null) {
            return createOrderInfo(orderInfo);
        }
        OrderInfo result = orderInfoRepository.save(orderInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-infos : get all the orderInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderInfos in body
     */
    @GetMapping("/order-infos")
    @Timed
    public List<OrderInfo> getAllOrderInfos() {
        log.debug("REST request to get all OrderInfos");
        List<OrderInfo> orderInfos = orderInfoRepository.findAll();
        return orderInfos;
    }

    /**
     * GET  /order-infos/:id : get the "id" orderInfo.
     *
     * @param id the id of the orderInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderInfo, or with status 404 (Not Found)
     */
    @GetMapping("/order-infos/{id}")
    @Timed
    public ResponseEntity<OrderInfo> getOrderInfo(@PathVariable Long id) {
        log.debug("REST request to get OrderInfo : {}", id);
        OrderInfo orderInfo = orderInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orderInfo));
    }

    /**
     * DELETE  /order-infos/:id : delete the "id" orderInfo.
     *
     * @param id the id of the orderInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderInfo(@PathVariable Long id) {
        log.debug("REST request to delete OrderInfo : {}", id);
        orderInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     *  工艺基础信息下发
     *
     * @param orderInfo
     * @param stepsDTO
     * @param processesDTO
     * @throws URISyntaxException
     */
//    @PostMapping("/")
//    @Timed
//    public void ProcessInformationBase(@RequestBody Optional<OrderInfo> orderInfo , List<StepsDTO> stepsDTO , List<ProcessesDTO> processesDTO) throws URISyntaxException {
//        log.debug("REST request to save materialIn : {}", orderInfo , stepsDTO , processesDTO);
//        processInformationService.ProcessInformation(orderInfo , stepsDTO , processesDTO);
//
//    }

}
