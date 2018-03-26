package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.domain.Sn;

import com.mj.holley.ims.repository.SnRepository;
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
 * REST controller for managing Sn.
 */
@RestController
@RequestMapping("/api")
public class SnResource {

    private final Logger log = LoggerFactory.getLogger(SnResource.class);

    private static final String ENTITY_NAME = "sn";
        
    private final SnRepository snRepository;

    public SnResource(SnRepository snRepository) {
        this.snRepository = snRepository;
    }

    /**
     * POST  /sns : Create a new sn.
     *
     * @param sn the sn to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sn, or with status 400 (Bad Request) if the sn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sns")
    @Timed
    public ResponseEntity<Sn> createSn(@RequestBody Sn sn) throws URISyntaxException {
        log.debug("REST request to save Sn : {}", sn);
        if (sn.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sn cannot already have an ID")).body(null);
        }
        Sn result = snRepository.save(sn);
        return ResponseEntity.created(new URI("/api/sns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sns : Updates an existing sn.
     *
     * @param sn the sn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sn,
     * or with status 400 (Bad Request) if the sn is not valid,
     * or with status 500 (Internal Server Error) if the sn couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sns")
    @Timed
    public ResponseEntity<Sn> updateSn(@RequestBody Sn sn) throws URISyntaxException {
        log.debug("REST request to update Sn : {}", sn);
        if (sn.getId() == null) {
            return createSn(sn);
        }
        Sn result = snRepository.save(sn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sn.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sns : get all the sns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sns in body
     */
    @GetMapping("/sns")
    @Timed
    public List<Sn> getAllSns() {
        log.debug("REST request to get all Sns");
        List<Sn> sns = snRepository.findAll();
        return sns;
    }

    /**
     * GET  /sns/:id : get the "id" sn.
     *
     * @param id the id of the sn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sn, or with status 404 (Not Found)
     */
    @GetMapping("/sns/{id}")
    @Timed
    public ResponseEntity<Sn> getSn(@PathVariable Long id) {
        log.debug("REST request to get Sn : {}", id);
        Sn sn = snRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sn));
    }

    /**
     * DELETE  /sns/:id : delete the "id" sn.
     *
     * @param id the id of the sn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sns/{id}")
    @Timed
    public ResponseEntity<Void> deleteSn(@PathVariable Long id) {
        log.debug("REST request to delete Sn : {}", id);
        snRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
