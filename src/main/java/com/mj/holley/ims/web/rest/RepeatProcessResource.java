package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.RepeatProcess;

import com.mj.holley.ims.repository.RepeatProcessRepository;
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
 * REST controller for managing RepeatProcess.
 */
@RestController
@RequestMapping("/api")
public class RepeatProcessResource {

    private final Logger log = LoggerFactory.getLogger(RepeatProcessResource.class);

    private static final String ENTITY_NAME = "repeatProcess";
        
    private final RepeatProcessRepository repeatProcessRepository;

    public RepeatProcessResource(RepeatProcessRepository repeatProcessRepository) {
        this.repeatProcessRepository = repeatProcessRepository;
    }

    /**
     * POST  /repeat-processes : Create a new repeatProcess.
     *
     * @param repeatProcess the repeatProcess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new repeatProcess, or with status 400 (Bad Request) if the repeatProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/repeat-processes")
    @Timed
    public ResponseEntity<RepeatProcess> createRepeatProcess(@RequestBody RepeatProcess repeatProcess) throws URISyntaxException {
        log.debug("REST request to save RepeatProcess : {}", repeatProcess);
        if (repeatProcess.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new repeatProcess cannot already have an ID")).body(null);
        }
        RepeatProcess result = repeatProcessRepository.save(repeatProcess);
        return ResponseEntity.created(new URI("/api/repeat-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /repeat-processes : Updates an existing repeatProcess.
     *
     * @param repeatProcess the repeatProcess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated repeatProcess,
     * or with status 400 (Bad Request) if the repeatProcess is not valid,
     * or with status 500 (Internal Server Error) if the repeatProcess couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/repeat-processes")
    @Timed
    public ResponseEntity<RepeatProcess> updateRepeatProcess(@RequestBody RepeatProcess repeatProcess) throws URISyntaxException {
        log.debug("REST request to update RepeatProcess : {}", repeatProcess);
        if (repeatProcess.getId() == null) {
            return createRepeatProcess(repeatProcess);
        }
        RepeatProcess result = repeatProcessRepository.save(repeatProcess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, repeatProcess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /repeat-processes : get all the repeatProcesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of repeatProcesses in body
     */
    @GetMapping("/repeat-processes")
    @Timed
    public List<RepeatProcess> getAllRepeatProcesses() {
        log.debug("REST request to get all RepeatProcesses");
        List<RepeatProcess> repeatProcesses = repeatProcessRepository.findAll();
        return repeatProcesses;
    }

    /**
     * GET  /repeat-processes/:id : get the "id" repeatProcess.
     *
     * @param id the id of the repeatProcess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the repeatProcess, or with status 404 (Not Found)
     */
    @GetMapping("/repeat-processes/{id}")
    @Timed
    public ResponseEntity<RepeatProcess> getRepeatProcess(@PathVariable Long id) {
        log.debug("REST request to get RepeatProcess : {}", id);
        RepeatProcess repeatProcess = repeatProcessRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(repeatProcess));
    }

    /**
     * DELETE  /repeat-processes/:id : delete the "id" repeatProcess.
     *
     * @param id the id of the repeatProcess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/repeat-processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRepeatProcess(@PathVariable Long id) {
        log.debug("REST request to delete RepeatProcess : {}", id);
        repeatProcessRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
