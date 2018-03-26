package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.ProcessControl;

import com.mj.holley.ims.repository.ProcessControlRepository;
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
 * REST controller for managing ProcessControl.
 */
@RestController
@RequestMapping("/api")
public class ProcessControlResource {

    private final Logger log = LoggerFactory.getLogger(ProcessControlResource.class);

    private static final String ENTITY_NAME = "processControl";
        
    private final ProcessControlRepository processControlRepository;

    public ProcessControlResource(ProcessControlRepository processControlRepository) {
        this.processControlRepository = processControlRepository;
    }

    /**
     * POST  /process-controls : Create a new processControl.
     *
     * @param processControl the processControl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new processControl, or with status 400 (Bad Request) if the processControl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/process-controls")
    @Timed
    public ResponseEntity<ProcessControl> createProcessControl(@RequestBody ProcessControl processControl) throws URISyntaxException {
        log.debug("REST request to save ProcessControl : {}", processControl);
        if (processControl.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new processControl cannot already have an ID")).body(null);
        }
        ProcessControl result = processControlRepository.save(processControl);
        return ResponseEntity.created(new URI("/api/process-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /process-controls : Updates an existing processControl.
     *
     * @param processControl the processControl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated processControl,
     * or with status 400 (Bad Request) if the processControl is not valid,
     * or with status 500 (Internal Server Error) if the processControl couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/process-controls")
    @Timed
    public ResponseEntity<ProcessControl> updateProcessControl(@RequestBody ProcessControl processControl) throws URISyntaxException {
        log.debug("REST request to update ProcessControl : {}", processControl);
        if (processControl.getId() == null) {
            return createProcessControl(processControl);
        }
        ProcessControl result = processControlRepository.save(processControl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, processControl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /process-controls : get all the processControls.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of processControls in body
     */
    @GetMapping("/process-controls")
    @Timed
    public List<ProcessControl> getAllProcessControls() {
        log.debug("REST request to get all ProcessControls");
        List<ProcessControl> processControls = processControlRepository.findAll();
        return processControls;
    }

    /**
     * GET  /process-controls/:id : get the "id" processControl.
     *
     * @param id the id of the processControl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the processControl, or with status 404 (Not Found)
     */
    @GetMapping("/process-controls/{id}")
    @Timed
    public ResponseEntity<ProcessControl> getProcessControl(@PathVariable Long id) {
        log.debug("REST request to get ProcessControl : {}", id);
        ProcessControl processControl = processControlRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processControl));
    }

    /**
     * DELETE  /process-controls/:id : delete the "id" processControl.
     *
     * @param id the id of the processControl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/process-controls/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcessControl(@PathVariable Long id) {
        log.debug("REST request to delete ProcessControl : {}", id);
        processControlRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
