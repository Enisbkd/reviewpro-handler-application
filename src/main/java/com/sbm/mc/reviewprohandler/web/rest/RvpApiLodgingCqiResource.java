package com.sbm.mc.reviewprohandler.web.rest;

import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
import com.sbm.mc.reviewprohandler.repository.RvpApiLodgingCqiRepository;
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
 * REST controller for managing {@link com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi}.
 */
@RestController
@RequestMapping("/api/rvp-api-lodging-cqis")
public class RvpApiLodgingCqiResource {

    private final Logger log = LoggerFactory.getLogger(RvpApiLodgingCqiResource.class);

    private static final String ENTITY_NAME = "rvpApiLodgingCqi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvpApiLodgingCqiRepository rvpApiLodgingCqiRepository;

    public RvpApiLodgingCqiResource(RvpApiLodgingCqiRepository rvpApiLodgingCqiRepository) {
        this.rvpApiLodgingCqiRepository = rvpApiLodgingCqiRepository;
    }

    /**
     * {@code POST  /rvp-api-lodging-cqis} : Create a new rvpApiLodgingCqi.
     *
     * @param rvpApiLodgingCqi the rvpApiLodgingCqi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvpApiLodgingCqi, or with status {@code 400 (Bad Request)} if the rvpApiLodgingCqi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RvpApiLodgingCqi> createRvpApiLodgingCqi(@RequestBody RvpApiLodgingCqi rvpApiLodgingCqi)
        throws URISyntaxException {
        log.debug("REST request to save RvpApiLodgingCqi : {}", rvpApiLodgingCqi);
        if (rvpApiLodgingCqi.getId() != null) {
            throw new BadRequestAlertException("A new rvpApiLodgingCqi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rvpApiLodgingCqi = rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);
        return ResponseEntity.created(new URI("/api/rvp-api-lodging-cqis/" + rvpApiLodgingCqi.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rvpApiLodgingCqi.getId().toString()))
            .body(rvpApiLodgingCqi);
    }

    /**
     * {@code PUT  /rvp-api-lodging-cqis/:id} : Updates an existing rvpApiLodgingCqi.
     *
     * @param id the id of the rvpApiLodgingCqi to save.
     * @param rvpApiLodgingCqi the rvpApiLodgingCqi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApiLodgingCqi,
     * or with status {@code 400 (Bad Request)} if the rvpApiLodgingCqi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvpApiLodgingCqi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RvpApiLodgingCqi> updateRvpApiLodgingCqi(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RvpApiLodgingCqi rvpApiLodgingCqi
    ) throws URISyntaxException {
        log.debug("REST request to update RvpApiLodgingCqi : {}, {}", id, rvpApiLodgingCqi);
        if (rvpApiLodgingCqi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApiLodgingCqi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApiLodgingCqiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rvpApiLodgingCqi = rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApiLodgingCqi.getId().toString()))
            .body(rvpApiLodgingCqi);
    }

    /**
     * {@code PATCH  /rvp-api-lodging-cqis/:id} : Partial updates given fields of an existing rvpApiLodgingCqi, field will ignore if it is null
     *
     * @param id the id of the rvpApiLodgingCqi to save.
     * @param rvpApiLodgingCqi the rvpApiLodgingCqi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvpApiLodgingCqi,
     * or with status {@code 400 (Bad Request)} if the rvpApiLodgingCqi is not valid,
     * or with status {@code 404 (Not Found)} if the rvpApiLodgingCqi is not found,
     * or with status {@code 500 (Internal Server Error)} if the rvpApiLodgingCqi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RvpApiLodgingCqi> partialUpdateRvpApiLodgingCqi(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody RvpApiLodgingCqi rvpApiLodgingCqi
    ) throws URISyntaxException {
        log.debug("REST request to partial update RvpApiLodgingCqi partially : {}, {}", id, rvpApiLodgingCqi);
        if (rvpApiLodgingCqi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rvpApiLodgingCqi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rvpApiLodgingCqiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RvpApiLodgingCqi> result = rvpApiLodgingCqiRepository
            .findById(rvpApiLodgingCqi.getId())
            .map(existingRvpApiLodgingCqi -> {
                if (rvpApiLodgingCqi.getLodgingId() != null) {
                    existingRvpApiLodgingCqi.setLodgingId(rvpApiLodgingCqi.getLodgingId());
                }
                if (rvpApiLodgingCqi.getAverageCurrentPeriod() != null) {
                    existingRvpApiLodgingCqi.setAverageCurrentPeriod(rvpApiLodgingCqi.getAverageCurrentPeriod());
                }
                if (rvpApiLodgingCqi.getTendancy() != null) {
                    existingRvpApiLodgingCqi.setTendancy(rvpApiLodgingCqi.getTendancy());
                }
                if (rvpApiLodgingCqi.getChange() != null) {
                    existingRvpApiLodgingCqi.setChange(rvpApiLodgingCqi.getChange());
                }
                if (rvpApiLodgingCqi.getName() != null) {
                    existingRvpApiLodgingCqi.setName(rvpApiLodgingCqi.getName());
                }
                if (rvpApiLodgingCqi.getAveragePreviousPeriod() != null) {
                    existingRvpApiLodgingCqi.setAveragePreviousPeriod(rvpApiLodgingCqi.getAveragePreviousPeriod());
                }
                if (rvpApiLodgingCqi.getFd() != null) {
                    existingRvpApiLodgingCqi.setFd(rvpApiLodgingCqi.getFd());
                }
                if (rvpApiLodgingCqi.getTd() != null) {
                    existingRvpApiLodgingCqi.setTd(rvpApiLodgingCqi.getTd());
                }

                return existingRvpApiLodgingCqi;
            })
            .map(rvpApiLodgingCqiRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvpApiLodgingCqi.getId().toString())
        );
    }

    /**
     * {@code GET  /rvp-api-lodging-cqis} : get all the rvpApiLodgingCqis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvpApiLodgingCqis in body.
     */
    @GetMapping("")
    public List<RvpApiLodgingCqi> getAllRvpApiLodgingCqis() {
        log.debug("REST request to get all RvpApiLodgingCqis");
        return rvpApiLodgingCqiRepository.findAll();
    }

    /**
     * {@code GET  /rvp-api-lodging-cqis/:id} : get the "id" rvpApiLodgingCqi.
     *
     * @param id the id of the rvpApiLodgingCqi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvpApiLodgingCqi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RvpApiLodgingCqi> getRvpApiLodgingCqi(@PathVariable("id") Integer id) {
        log.debug("REST request to get RvpApiLodgingCqi : {}", id);
        Optional<RvpApiLodgingCqi> rvpApiLodgingCqi = rvpApiLodgingCqiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rvpApiLodgingCqi);
    }

    /**
     * {@code DELETE  /rvp-api-lodging-cqis/:id} : delete the "id" rvpApiLodgingCqi.
     *
     * @param id the id of the rvpApiLodgingCqi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRvpApiLodgingCqi(@PathVariable("id") Integer id) {
        log.debug("REST request to delete RvpApiLodgingCqi : {}", id);
        rvpApiLodgingCqiRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
