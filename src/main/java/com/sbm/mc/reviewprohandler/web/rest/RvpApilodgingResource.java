package com.sbm.mc.reviewprohandler.web.rest;

import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import com.sbm.mc.reviewprohandler.repository.RvpApilodgingRepository;
import com.sbm.mc.reviewprohandler.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbm.mc.reviewprohandler.domain.RvpApilodging}.
 */
@RestController
@RequestMapping("/api/rvp-apilodgings")
public class RvpApilodgingResource {

    private final Logger log = LoggerFactory.getLogger(RvpApilodgingResource.class);

    private static final String ENTITY_NAME = "rvpApilodging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvpApilodgingRepository rvpApilodgingRepository;

    public RvpApilodgingResource(RvpApilodgingRepository rvpApilodgingRepository) {
        this.rvpApilodgingRepository = rvpApilodgingRepository;
    }

    /**
     * {@code POST  /rvp-apilodgings} : Create a new rvpApilodging.
     *
     * @param rvpApilodging the rvpApilodging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvpApilodging, or with status {@code 400 (Bad Request)} if the rvpApilodging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RvpApilodging> createRvpApilodging(@RequestBody RvpApilodging rvpApilodging) throws URISyntaxException {
        log.debug("REST request to save RvpApilodging : {}", rvpApilodging);
        if (rvpApilodging.getId() != null) {
            throw new BadRequestAlertException("A new rvpApilodging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rvpApilodging = rvpApilodgingRepository.save(rvpApilodging);
        return ResponseEntity.created(new URI("/api/rvp-apilodgings/" + rvpApilodging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rvpApilodging.getId()))
            .body(rvpApilodging);
    }

    /**
     * {@code PUT  /rvp-apilodgings/:id} : Updates an existing rvpApilodging.
     *
     * @param id the id of the rvpApilodging to save.
     * @param rvpApilodging the rvpApilodging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApilodging,
     * or with status {@code 400 (Bad Request)} if the rvpApilodging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvpApilodging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RvpApilodging> updateRvpApilodging(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RvpApilodging rvpApilodging
    ) throws URISyntaxException {
        log.debug("REST request to update RvpApilodging : {}, {}", id, rvpApilodging);
        if (rvpApilodging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApilodging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApilodgingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rvpApilodging = rvpApilodgingRepository.save(rvpApilodging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApilodging.getId()))
            .body(rvpApilodging);
    }

    /**
     * {@code PATCH  /rvp-apilodgings/:id} : Partial updates given fields of an existing rvpApilodging, field will ignore if it is null
     *
     * @param id the id of the rvpApilodging to save.
     * @param rvpApilodging the rvpApilodging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApilodging,
     * or with status {@code 400 (Bad Request)} if the rvpApilodging is not valid,
     * or with status {@code 404 (Not Found)} if the rvpApilodging is not found,
     * or with status {@code 500 (Internal Server Error)} if the rvpApilodging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RvpApilodging> partialUpdateRvpApilodging(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RvpApilodging rvpApilodging
    ) throws URISyntaxException {
        log.debug("REST request to partial update RvpApilodging partially : {}, {}", id, rvpApilodging);
        if (rvpApilodging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApilodging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApilodgingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RvpApilodging> result = rvpApilodgingRepository
            .findById(rvpApilodging.getId())
            .map(existingRvpApilodging -> {
                if (rvpApilodging.getIda() != null) {
                    existingRvpApilodging.setIda(rvpApilodging.getIda());
                }
                if (rvpApilodging.getName() != null) {
                    existingRvpApilodging.setName(rvpApilodging.getName());
                }

                return existingRvpApilodging;
            })
            .map(rvpApilodgingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApilodging.getId())
        );
    }

    /**
     * {@code GET  /rvp-apilodgings} : get all the rvpApilodgings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvpApilodgings in body.
     */
    @GetMapping("")
    public List<RvpApilodging> getAllRvpApilodgings() {
        log.debug("REST request to get all RvpApilodgings");
        return rvpApilodgingRepository.findAll();
    }

    /**
     * {@code GET  /rvp-apilodgings/:id} : get the "id" rvpApilodging.
     *
     * @param id the id of the rvpApilodging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvpApilodging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RvpApilodging> getRvpApilodging(@PathVariable("id") String id) {
        log.debug("REST request to get RvpApilodging : {}", id);
        Optional<RvpApilodging> rvpApilodging = rvpApilodgingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rvpApilodging);
    }

    /**
     * {@code DELETE  /rvp-apilodgings/:id} : delete the "id" rvpApilodging.
     *
     * @param id the id of the rvpApilodging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRvpApilodging(@PathVariable("id") String id) {
        log.debug("REST request to delete RvpApilodging : {}", id);
        rvpApilodgingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
