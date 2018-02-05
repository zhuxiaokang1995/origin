package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.Steps;

import com.mj.holley.ims.repository.StepsRepository;
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
 * REST controller for managing Steps.
 */
@RestController
@RequestMapping("/api")
public class StepsResource {

    private final Logger log = LoggerFactory.getLogger(StepsResource.class);

    private static final String ENTITY_NAME = "steps";
        
    private final StepsRepository stepsRepository;

    public StepsResource(StepsRepository stepsRepository) {
        this.stepsRepository = stepsRepository;
    }

    /**
     * POST  /steps : Create a new steps.
     *
     * @param steps the steps to create
     * @return the ResponseEntity with status 201 (Created) and with body the new steps, or with status 400 (Bad Request) if the steps has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/steps")
    @Timed
    public ResponseEntity<Steps> createSteps(@RequestBody Steps steps) throws URISyntaxException {
        log.debug("REST request to save Steps : {}", steps);
        if (steps.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new steps cannot already have an ID")).body(null);
        }
        Steps result = stepsRepository.save(steps);
        return ResponseEntity.created(new URI("/api/steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /steps : Updates an existing steps.
     *
     * @param steps the steps to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated steps,
     * or with status 400 (Bad Request) if the steps is not valid,
     * or with status 500 (Internal Server Error) if the steps couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/steps")
    @Timed
    public ResponseEntity<Steps> updateSteps(@RequestBody Steps steps) throws URISyntaxException {
        log.debug("REST request to update Steps : {}", steps);
        if (steps.getId() == null) {
            return createSteps(steps);
        }
        Steps result = stepsRepository.save(steps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, steps.getId().toString()))
            .body(result);
    }

    /**
     * GET  /steps : get all the steps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of steps in body
     */
    @GetMapping("/steps")
    @Timed
    public List<Steps> getAllSteps() {
        log.debug("REST request to get all Steps");
        List<Steps> steps = stepsRepository.findAll();
        return steps;
    }

    /**
     * GET  /steps/:id : get the "id" steps.
     *
     * @param id the id of the steps to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the steps, or with status 404 (Not Found)
     */
    @GetMapping("/steps/{id}")
    @Timed
    public ResponseEntity<Steps> getSteps(@PathVariable Long id) {
        log.debug("REST request to get Steps : {}", id);
        Steps steps = stepsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(steps));
    }

    /**
     * DELETE  /steps/:id : delete the "id" steps.
     *
     * @param id the id of the steps to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/steps/{id}")
    @Timed
    public ResponseEntity<Void> deleteSteps(@PathVariable Long id) {
        log.debug("REST request to delete Steps : {}", id);
        stepsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
