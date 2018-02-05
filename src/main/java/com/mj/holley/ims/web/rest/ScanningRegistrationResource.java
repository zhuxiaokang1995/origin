package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.ScanningRegistration;
import com.mj.holley.ims.repository.ScanningRegistrationRepository;
import com.mj.holley.ims.service.MesSubmitService;
import com.mj.holley.ims.service.RedisService;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import com.mj.holley.ims.web.rest.Constants.WebRestConstants;
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
 * REST controller for managing ScanningRegistration.
 */
@RestController
@RequestMapping("/api")
public class ScanningRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(ScanningRegistrationResource.class);

    private static final String ENTITY_NAME = "scanningRegistration";

    private final ScanningRegistrationRepository scanningRegistrationRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MesSubmitService mesSubmitService;

    public ScanningRegistrationResource(ScanningRegistrationRepository scanningRegistrationRepository) {
        this.scanningRegistrationRepository = scanningRegistrationRepository;
    }

    /**
     * POST  /scanning-registrations : Create a new scanningRegistration.
     *
     * @param scanningRegistration the scanningRegistration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scanningRegistration, or with status 400 (Bad Request) if the scanningRegistration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scanning-registrations")
    @Timed
    public ResponseEntity<ScanningRegistration> createScanningRegistration(@RequestBody ScanningRegistration scanningRegistration) throws URISyntaxException {
        log.debug("REST request to save ScanningRegistration : {}", scanningRegistration);
        if (scanningRegistration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new scanningRegistration cannot already have an ID")).body(null);
        }
        ScanningRegistration result = scanningRegistrationRepository.save(scanningRegistration);
        return ResponseEntity.created(new URI("/api/scanning-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scanning-registrations : Updates an existing scanningRegistration.
     *
     * @param scanningRegistration the scanningRegistration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scanningRegistration,
     * or with status 400 (Bad Request) if the scanningRegistration is not valid,
     * or with status 500 (Internal Server Error) if the scanningRegistration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scanning-registrations")
    @Timed
    public ResponseEntity<ScanningRegistration> updateScanningRegistration(@RequestBody ScanningRegistration scanningRegistration) throws URISyntaxException {
        log.debug("REST request to update ScanningRegistration : {}", scanningRegistration);
        if (scanningRegistration.getId() == null) {
            return createScanningRegistration(scanningRegistration);
        }
        ScanningRegistration result = scanningRegistrationRepository.save(scanningRegistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scanningRegistration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scanning-registrations : get all the scanningRegistrations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of scanningRegistrations in body
     */
    @GetMapping("/scanning-registrations")
    @Timed
    public List<ScanningRegistration> getAllScanningRegistrations() {
        log.debug("REST request to get all ScanningRegistrations");
        List<ScanningRegistration> scanningRegistrations = scanningRegistrationRepository.findAll();
        return scanningRegistrations;
    }

    /**
     * GET  /scanning-registrations/:id : get the "id" scanningRegistration.
     *
     * @param id the id of the scanningRegistration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scanningRegistration, or with status 404 (Not Found)
     */
    @GetMapping("/scanning-registrations/{id}")
    @Timed
    public ResponseEntity<ScanningRegistration> getScanningRegistration(@PathVariable Long id) {
        log.debug("REST request to get ScanningRegistration : {}", id);
        ScanningRegistration scanningRegistration = scanningRegistrationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scanningRegistration));
    }

    /**
     * DELETE  /scanning-registrations/:id : delete the "id" scanningRegistration.
     *
     * @param id the id of the scanningRegistration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scanning-registrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteScanningRegistration(@PathVariable Long id) {
        log.debug("REST request to delete ScanningRegistration : {}", id);
        scanningRegistrationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    //扫描组装登记缺陷
    @PostMapping("/mesScanningRegistration")
    @Timed
    public void mesScanningRegistration(@RequestBody ScanningResgistrationDTO sr) {
        redisService.incr(WebRestConstants.MES_SCANNING_REGISTRATION);
        int pk = Integer.parseInt(redisService.readObject(WebRestConstants.MES_SCANNING_REGISTRATION).toString());
        mesSubmitService.submitScanningRegistration(new ScanningResgistrationDTO(pk,sr.getSerialNumber(),sr.getStationID(),sr.getScanType(),sr.getDefectCode(),sr.getResult(),sr.getSubSn()));
    }

}
