package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.TransportTask;

import com.mj.holley.ims.repository.TransportTaskRepository;
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
 * REST controller for managing TransportTask.
 */
@RestController
@RequestMapping("/api")
public class TransportTaskResource {

    private final Logger log = LoggerFactory.getLogger(TransportTaskResource.class);

    private static final String ENTITY_NAME = "transportTask";
        
    private final TransportTaskRepository transportTaskRepository;

    public TransportTaskResource(TransportTaskRepository transportTaskRepository) {
        this.transportTaskRepository = transportTaskRepository;
    }

    /**
     * POST  /transport-tasks : Create a new transportTask.
     *
     * @param transportTask the transportTask to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transportTask, or with status 400 (Bad Request) if the transportTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transport-tasks")
    @Timed
    public ResponseEntity<TransportTask> createTransportTask(@RequestBody TransportTask transportTask) throws URISyntaxException {
        log.debug("REST request to save TransportTask : {}", transportTask);
        if (transportTask.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transportTask cannot already have an ID")).body(null);
        }
        TransportTask result = transportTaskRepository.save(transportTask);
        return ResponseEntity.created(new URI("/api/transport-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transport-tasks : Updates an existing transportTask.
     *
     * @param transportTask the transportTask to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transportTask,
     * or with status 400 (Bad Request) if the transportTask is not valid,
     * or with status 500 (Internal Server Error) if the transportTask couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transport-tasks")
    @Timed
    public ResponseEntity<TransportTask> updateTransportTask(@RequestBody TransportTask transportTask) throws URISyntaxException {
        log.debug("REST request to update TransportTask : {}", transportTask);
        if (transportTask.getId() == null) {
            return createTransportTask(transportTask);
        }
        TransportTask result = transportTaskRepository.save(transportTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transportTask.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transport-tasks : get all the transportTasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transportTasks in body
     */
    @GetMapping("/transport-tasks")
    @Timed
    public List<TransportTask> getAllTransportTasks() {
        log.debug("REST request to get all TransportTasks");
        List<TransportTask> transportTasks = transportTaskRepository.findAll();
        return transportTasks;
    }

    /**
     * GET  /transport-tasks/:id : get the "id" transportTask.
     *
     * @param id the id of the transportTask to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transportTask, or with status 404 (Not Found)
     */
    @GetMapping("/transport-tasks/{id}")
    @Timed
    public ResponseEntity<TransportTask> getTransportTask(@PathVariable Long id) {
        log.debug("REST request to get TransportTask : {}", id);
        TransportTask transportTask = transportTaskRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transportTask));
    }

    /**
     * DELETE  /transport-tasks/:id : delete the "id" transportTask.
     *
     * @param id the id of the transportTask to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transport-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransportTask(@PathVariable Long id) {
        log.debug("REST request to delete TransportTask : {}", id);
        transportTaskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
