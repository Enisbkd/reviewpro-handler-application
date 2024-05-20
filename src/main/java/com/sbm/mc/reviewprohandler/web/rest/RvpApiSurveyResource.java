package com.sbm.mc.reviewprohandler.web.rest;

import com.sbm.mc.reviewprohandler.domain.RvpApiSurvey;
import com.sbm.mc.reviewprohandler.repository.RvpApiSurveyRepository;
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
 * REST controller for managing {@link com.sbm.mc.reviewprohandler.domain.RvpApiSurvey}.
 */
@RestController
@RequestMapping("/api/rvp-api-surveys")
public class RvpApiSurveyResource {

    private final Logger log = LoggerFactory.getLogger(RvpApiSurveyResource.class);

    private static final String ENTITY_NAME = "rvpApiSurvey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvpApiSurveyRepository rvpApiSurveyRepository;

    public RvpApiSurveyResource(RvpApiSurveyRepository rvpApiSurveyRepository) {
        this.rvpApiSurveyRepository = rvpApiSurveyRepository;
    }

    /**
     * {@code POST  /rvp-api-surveys} : Create a new rvpApiSurvey.
     *
     * @param rvpApiSurvey the rvpApiSurvey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvpApiSurvey, or with status {@code 400 (Bad Request)} if the rvpApiSurvey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RvpApiSurvey> createRvpApiSurvey(@RequestBody RvpApiSurvey rvpApiSurvey) throws URISyntaxException {
        log.debug("REST request to save RvpApiSurvey : {}", rvpApiSurvey);
        if (rvpApiSurvey.getId() != null) {
            throw new BadRequestAlertException("A new rvpApiSurvey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rvpApiSurvey = rvpApiSurveyRepository.save(rvpApiSurvey);
        return ResponseEntity.created(new URI("/api/rvp-api-surveys/" + rvpApiSurvey.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rvpApiSurvey.getId().toString()))
            .body(rvpApiSurvey);
    }

    /**
     * {@code PUT  /rvp-api-surveys/:id} : Updates an existing rvpApiSurvey.
     *
     * @param id the id of the rvpApiSurvey to save.
     * @param rvpApiSurvey the rvpApiSurvey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApiSurvey,
     * or with status {@code 400 (Bad Request)} if the rvpApiSurvey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvpApiSurvey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RvpApiSurvey> updateRvpApiSurvey(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RvpApiSurvey rvpApiSurvey
    ) throws URISyntaxException {
        log.debug("REST request to update RvpApiSurvey : {}, {}", id, rvpApiSurvey);
        if (rvpApiSurvey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApiSurvey.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApiSurveyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rvpApiSurvey = rvpApiSurveyRepository.save(rvpApiSurvey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApiSurvey.getId().toString()))
            .body(rvpApiSurvey);
    }

    /**
     * {@code PATCH  /rvp-api-surveys/:id} : Partial updates given fields of an existing rvpApiSurvey, field will ignore if it is null
     *
     * @param id the id of the rvpApiSurvey to save.
     * @param rvpApiSurvey the rvpApiSurvey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApiSurvey,
     * or with status {@code 400 (Bad Request)} if the rvpApiSurvey is not valid,
     * or with status {@code 404 (Not Found)} if the rvpApiSurvey is not found,
     * or with status {@code 500 (Internal Server Error)} if the rvpApiSurvey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RvpApiSurvey> partialUpdateRvpApiSurvey(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RvpApiSurvey rvpApiSurvey
    ) throws URISyntaxException {
        log.debug("REST request to partial update RvpApiSurvey partially : {}, {}", id, rvpApiSurvey);
        if (rvpApiSurvey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApiSurvey.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApiSurveyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RvpApiSurvey> result = rvpApiSurveyRepository
            .findById(rvpApiSurvey.getId())
            .map(existingRvpApiSurvey -> {
                if (rvpApiSurvey.getOverallScoreEnabled() != null) {
                    existingRvpApiSurvey.setOverallScoreEnabled(rvpApiSurvey.getOverallScoreEnabled());
                }
                if (rvpApiSurvey.getLanguages() != null) {
                    existingRvpApiSurvey.setLanguages(rvpApiSurvey.getLanguages());
                }
                if (rvpApiSurvey.getOutOf() != null) {
                    existingRvpApiSurvey.setOutOf(rvpApiSurvey.getOutOf());
                }
                if (rvpApiSurvey.getName() != null) {
                    existingRvpApiSurvey.setName(rvpApiSurvey.getName());
                }
                if (rvpApiSurvey.getActibe() != null) {
                    existingRvpApiSurvey.setActibe(rvpApiSurvey.getActibe());
                }
                if (rvpApiSurvey.getPids() != null) {
                    existingRvpApiSurvey.setPids(rvpApiSurvey.getPids());
                }
                if (rvpApiSurvey.getPrimary() != null) {
                    existingRvpApiSurvey.setPrimary(rvpApiSurvey.getPrimary());
                }

                return existingRvpApiSurvey;
            })
            .map(rvpApiSurveyRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApiSurvey.getId().toString())
        );
    }

    /**
     * {@code GET  /rvp-api-surveys} : get all the rvpApiSurveys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvpApiSurveys in body.
     */
    @GetMapping("")
    public List<RvpApiSurvey> getAllRvpApiSurveys() {
        log.debug("REST request to get all RvpApiSurveys");
        return rvpApiSurveyRepository.findAll();
    }

    /**
     * {@code GET  /rvp-api-surveys/:id} : get the "id" rvpApiSurvey.
     *
     * @param id the id of the rvpApiSurvey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvpApiSurvey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RvpApiSurvey> getRvpApiSurvey(@PathVariable("id") Integer id) {
        log.debug("REST request to get RvpApiSurvey : {}", id);
        Optional<RvpApiSurvey> rvpApiSurvey = rvpApiSurveyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rvpApiSurvey);
    }

    /**
     * {@code DELETE  /rvp-api-surveys/:id} : delete the "id" rvpApiSurvey.
     *
     * @param id the id of the rvpApiSurvey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRvpApiSurvey(@PathVariable("id") Integer id) {
        log.debug("REST request to delete RvpApiSurvey : {}", id);
        rvpApiSurveyRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
