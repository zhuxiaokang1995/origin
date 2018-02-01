package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.Processes;

import com.mj.holley.ims.repository.ProcessesRepository;
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
 * REST controller for managing Processes.
 */
@RestController
@RequestMapping("/api")
public class ProcessesResource {

    private final Logger log = LoggerFactory.getLogger(ProcessesResource.class);

    private static final String ENTITY_NAME = "processes";
        
    private final ProcessesRepository processesRepository;

    public ProcessesResource(ProcessesRepository processesRepository) {
        this.processesRepository = processesRepository;
    }

    /**
     * POST  /processes : Create a new processes.
     *
     * @param processes the processes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new processes, or with status 400 (Bad Request) if the processes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/processes")
    @Timed
    public ResponseEntity<Processes> createProcesses(@RequestBody Processes processes) throws URISyntaxException {
        log.debug("REST request to save Processes : {}", processes);
        if (processes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new processes cannot already have an ID")).body(null);
        }
        Processes result = processesRepository.save(processes);
        return ResponseEntity.created(new URI("/api/processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /processes : Updates an existing processes.
     *
     * @param processes the processes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated processes,
     * or with status 400 (Bad Request) if the processes is not valid,
     * or with status 500 (Internal Server Error) if the processes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/processes")
    @Timed
    public ResponseEntity<Processes> updateProcesses(@RequestBody Processes processes) throws URISyntaxException {
        log.debug("REST request to update Processes : {}", processes);
        if (processes.getId() == null) {
            return createProcesses(processes);
        }
        Processes result = processesRepository.save(processes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, processes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /processes : get all the processes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of processes in body
     */
    @GetMapping("/processes")
    @Timed
    public List<Processes> getAllProcesses() {
        log.debug("REST request to get all Processes");
        List<Processes> processes = processesRepository.findAll();
        return processes;
    }

    /**
     * GET  /processes/:id : get the "id" processes.
     *
     * @param id the id of the processes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the processes, or with status 404 (Not Found)
     */
    @GetMapping("/processes/{id}")
    @Timed
    public ResponseEntity<Processes> getProcesses(@PathVariable Long id) {
        log.debug("REST request to get Processes : {}", id);
        Processes processes = processesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processes));
    }

    /**
     * DELETE  /processes/:id : delete the "id" processes.
     *
     * @param id the id of the processes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcesses(@PathVariable Long id) {
        log.debug("REST request to delete Processes : {}", id);
        processesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
