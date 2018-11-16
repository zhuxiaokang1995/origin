package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.ArriveStationInfo;
import com.mj.holley.ims.service.ArriveStationInfoService;
import com.mj.holley.ims.web.rest.errors.BadRequestAlertException;
import com.mj.holley.ims.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ArriveStationInfo.
 */
@RestController
@RequestMapping("/api")
public class ArriveStationInfoResource {

    private final Logger log = LoggerFactory.getLogger(ArriveStationInfoResource.class);

    private static final String ENTITY_NAME = "arriveStationInfo";

    private final ArriveStationInfoService arriveStationInfoService;

    public ArriveStationInfoResource(ArriveStationInfoService arriveStationInfoService) {
        this.arriveStationInfoService = arriveStationInfoService;
    }

    /**
     * POST  /arrive-station-infos : Create a new arriveStationInfo.
     *
     * @param arriveStationInfo the arriveStationInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new arriveStationInfo, or with status 400 (Bad Request) if the arriveStationInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/arrive-station-infos")
    @Timed
    public ResponseEntity<ArriveStationInfo> createArriveStationInfo(@RequestBody ArriveStationInfo arriveStationInfo) throws URISyntaxException {
        log.debug("REST request to save ArriveStationInfo : {}", arriveStationInfo);
        if (arriveStationInfo.getId() != null) {
            throw new BadRequestAlertException("A new arriveStationInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArriveStationInfo result = arriveStationInfoService.save(arriveStationInfo);
        return ResponseEntity.created(new URI("/api/arrive-station-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /arrive-station-infos : Updates an existing arriveStationInfo.
     *
     * @param arriveStationInfo the arriveStationInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated arriveStationInfo,
     * or with status 400 (Bad Request) if the arriveStationInfo is not valid,
     * or with status 500 (Internal Server Error) if the arriveStationInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/arrive-station-infos")
    @Timed
    public ResponseEntity<ArriveStationInfo> updateArriveStationInfo(@RequestBody ArriveStationInfo arriveStationInfo) throws URISyntaxException {
        log.debug("REST request to update ArriveStationInfo : {}", arriveStationInfo);
        if (arriveStationInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArriveStationInfo result = arriveStationInfoService.save(arriveStationInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, arriveStationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /arrive-station-infos : get all the arriveStationInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of arriveStationInfos in body
     */
    @GetMapping("/arrive-station-infos")
    @Timed
    public List<ArriveStationInfo> getAllArriveStationInfos() {
        log.debug("REST request to get all ArriveStationInfos");
        return arriveStationInfoService.findAll();
    }

    /**
     * GET  /arrive-station-infos/:id : get the "id" arriveStationInfo.
     *
     * @param id the id of the arriveStationInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the arriveStationInfo, or with status 404 (Not Found)
     */
    @GetMapping("/arrive-station-infos/{id}")
    @Timed
    public ResponseEntity<ArriveStationInfo> getArriveStationInfo(@PathVariable Long id) {
        log.debug("REST request to get ArriveStationInfo : {}", id);
        Optional<ArriveStationInfo> arriveStationInfo = arriveStationInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arriveStationInfo);
    }

    /**
     * DELETE  /arrive-station-infos/:id : delete the "id" arriveStationInfo.
     *
     * @param id the id of the arriveStationInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/arrive-station-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteArriveStationInfo(@PathVariable Long id) {
        log.debug("REST request to delete ArriveStationInfo : {}", id);
        arriveStationInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
