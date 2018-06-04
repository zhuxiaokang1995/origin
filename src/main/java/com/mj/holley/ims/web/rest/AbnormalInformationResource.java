package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.AbnormalInformation;

import com.mj.holley.ims.repository.AbnormalInformationRepository;
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
 * REST controller for managing AbnormalInformation.
 */
@RestController
@RequestMapping("/api")
public class AbnormalInformationResource {

    private final Logger log = LoggerFactory.getLogger(AbnormalInformationResource.class);

    private static final String ENTITY_NAME = "abnormalInformation";
        
    private final AbnormalInformationRepository abnormalInformationRepository;

    public AbnormalInformationResource(AbnormalInformationRepository abnormalInformationRepository) {
        this.abnormalInformationRepository = abnormalInformationRepository;
    }

    /**
     * POST  /abnormal-informations : Create a new abnormalInformation.
     *
     * @param abnormalInformation the abnormalInformation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new abnormalInformation, or with status 400 (Bad Request) if the abnormalInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abnormal-informations")
    @Timed
    public ResponseEntity<AbnormalInformation> createAbnormalInformation(@RequestBody AbnormalInformation abnormalInformation) throws URISyntaxException {
        log.debug("REST request to save AbnormalInformation : {}", abnormalInformation);
        if (abnormalInformation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new abnormalInformation cannot already have an ID")).body(null);
        }
        AbnormalInformation result = abnormalInformationRepository.save(abnormalInformation);
        return ResponseEntity.created(new URI("/api/abnormal-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abnormal-informations : Updates an existing abnormalInformation.
     *
     * @param abnormalInformation the abnormalInformation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated abnormalInformation,
     * or with status 400 (Bad Request) if the abnormalInformation is not valid,
     * or with status 500 (Internal Server Error) if the abnormalInformation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abnormal-informations")
    @Timed
    public ResponseEntity<AbnormalInformation> updateAbnormalInformation(@RequestBody AbnormalInformation abnormalInformation) throws URISyntaxException {
        log.debug("REST request to update AbnormalInformation : {}", abnormalInformation);
        if (abnormalInformation.getId() == null) {
            return createAbnormalInformation(abnormalInformation);
        }
        AbnormalInformation result = abnormalInformationRepository.save(abnormalInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, abnormalInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abnormal-informations : get all the abnormalInformations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of abnormalInformations in body
     */
    @GetMapping("/abnormal-informations")
    @Timed
    public List<AbnormalInformation> getAllAbnormalInformations() {
        log.debug("REST request to get all AbnormalInformations");
        List<AbnormalInformation> abnormalInformations = abnormalInformationRepository.findAll();
        return abnormalInformations;
    }

    /**
     * GET  /abnormal-informations/:id : get the "id" abnormalInformation.
     *
     * @param id the id of the abnormalInformation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the abnormalInformation, or with status 404 (Not Found)
     */
    @GetMapping("/abnormal-informations/{id}")
    @Timed
    public ResponseEntity<AbnormalInformation> getAbnormalInformation(@PathVariable Long id) {
        log.debug("REST request to get AbnormalInformation : {}", id);
        AbnormalInformation abnormalInformation = abnormalInformationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(abnormalInformation));
    }

    /**
     * DELETE  /abnormal-informations/:id : delete the "id" abnormalInformation.
     *
     * @param id the id of the abnormalInformation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abnormal-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAbnormalInformation(@PathVariable Long id) {
        log.debug("REST request to delete AbnormalInformation : {}", id);
        abnormalInformationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
