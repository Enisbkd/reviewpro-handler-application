package com.sbm.mc.reviewprohandler.web.rest;

import com.sbm.mc.reviewprohandler.domain.Rvpapilodging;
import com.sbm.mc.reviewprohandler.repository.RvpapilodgingRepository;
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
 * REST controller for managing {@link com.sbm.mc.reviewprohandler.domain.Rvpapilodging}.
 */
@RestController
@RequestMapping("/api/rvpapilodgings")
public class RvpapilodgingResource {

    private final Logger log = LoggerFactory.getLogger(RvpapilodgingResource.class);

    private static final String ENTITY_NAME = "rvpapilodging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvpapilodgingRepository rvpapilodgingRepository;

    public RvpapilodgingResource(RvpapilodgingRepository rvpapilodgingRepository) {
        this.rvpapilodgingRepository = rvpapilodgingRepository;
    }

    /**
     * {@code POST  /rvpapilodgings} : Create a new rvpapilodging.
     *
     * @param rvpapilodging the rvpapilodging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvpapilodging, or with status {@code 400 (Bad Request)} if the rvpapilodging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Rvpapilodging> createRvpapilodging(@RequestBody Rvpapilodging rvpapilodging) throws URISyntaxException {
        log.debug("REST request to save Rvpapilodging : {}", rvpapilodging);
        if (rvpapilodging.getId() != null) {
            throw new BadRequestAlertException("A new rvpapilodging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rvpapilodging = rvpapilodgingRepository.save(rvpapilodging);
        return ResponseEntity.created(new URI("/api/rvpapilodgings/" + rvpapilodging.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rvpapilodging.getId()))
            .body(rvpapilodging);
    }

    /**
     * {@code PUT  /rvpapilodgings/:id} : Updates an existing rvpapilodging.
     *
     * @param id the id of the rvpapilodging to save.
     * @param rvpapilodging the rvpapilodging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpapilodging,
     * or with status {@code 400 (Bad Request)} if the rvpapilodging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvpapilodging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rvpapilodging> updateRvpapilodging(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Rvpapilodging rvpapilodging
    ) throws URISyntaxException {
        log.debug("REST request to update Rvpapilodging : {}, {}", id, rvpapilodging);
        if (rvpapilodging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpapilodging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpapilodgingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rvpapilodging = rvpapilodgingRepository.save(rvpapilodging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpapilodging.getId()))
            .body(rvpapilodging);
    }

    /**
     * {@code PATCH  /rvpapilodgings/:id} : Partial updates given fields of an existing rvpapilodging, field will ignore if it is null
     *
     * @param id the id of the rvpapilodging to save.
     * @param rvpapilodging the rvpapilodging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpapilodging,
     * or with status {@code 400 (Bad Request)} if the rvpapilodging is not valid,
     * or with status {@code 404 (Not Found)} if the rvpapilodging is not found,
     * or with status {@code 500 (Internal Server Error)} if the rvpapilodging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rvpapilodging> partialUpdateRvpapilodging(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Rvpapilodging rvpapilodging
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rvpapilodging partially : {}, {}", id, rvpapilodging);
        if (rvpapilodging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpapilodging.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpapilodgingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rvpapilodging> result = rvpapilodgingRepository
            .findById(rvpapilodging.getId())
            .map(existingRvpapilodging -> {
                if (rvpapilodging.getIda() != null) {
                    existingRvpapilodging.setIda(rvpapilodging.getIda());
                }
                if (rvpapilodging.getName() != null) {
                    existingRvpapilodging.setName(rvpapilodging.getName());
                }

                return existingRvpapilodging;
            })
            .map(rvpapilodgingRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpapilodging.getId())
        );
    }

    /**
     * {@code GET  /rvpapilodgings} : get all the rvpapilodgings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvpapilodgings in body.
     */
    @GetMapping("")
    public List<Rvpapilodging> getAllRvpapilodgings() {
        log.debug("REST request to get all Rvpapilodgings");
        return rvpapilodgingRepository.findAll();
    }

    /**
     * {@code GET  /rvpapilodgings/:id} : get the "id" rvpapilodging.
     *
     * @param id the id of the rvpapilodging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvpapilodging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rvpapilodging> getRvpapilodging(@PathVariable("id") String id) {
        log.debug("REST request to get Rvpapilodging : {}", id);
        Optional<Rvpapilodging> rvpapilodging = rvpapilodgingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rvpapilodging);
    }

    /**
     * {@code DELETE  /rvpapilodgings/:id} : delete the "id" rvpapilodging.
     *
     * @param id the id of the rvpapilodging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRvpapilodging(@PathVariable("id") String id) {
        log.debug("REST request to delete Rvpapilodging : {}", id);
        rvpapilodgingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
