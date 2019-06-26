package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Rapport;
import io.github.jhipster.application.repository.RapportRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Rapport}.
 */
@RestController
@RequestMapping("/api")
public class RapportResource {

    private final Logger log = LoggerFactory.getLogger(RapportResource.class);

    private static final String ENTITY_NAME = "rapport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RapportRepository rapportRepository;

    public RapportResource(RapportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    /**
     * {@code POST  /rapports} : Create a new rapport.
     *
     * @param rapport the rapport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rapport, or with status {@code 400 (Bad Request)} if the rapport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rapports")
    public ResponseEntity<Rapport> createRapport(@RequestBody Rapport rapport) throws URISyntaxException {
        log.debug("REST request to save Rapport : {}", rapport);
        if (rapport.getId() != null) {
            throw new BadRequestAlertException("A new rapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rapport result = rapportRepository.save(rapport);
        return ResponseEntity.created(new URI("/api/rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rapports} : Updates an existing rapport.
     *
     * @param rapport the rapport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rapport,
     * or with status {@code 400 (Bad Request)} if the rapport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rapport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rapports")
    public ResponseEntity<Rapport> updateRapport(@RequestBody Rapport rapport) throws URISyntaxException {
        log.debug("REST request to update Rapport : {}", rapport);
        if (rapport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rapport result = rapportRepository.save(rapport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rapport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rapports} : get all the rapports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rapports in body.
     */
    @GetMapping("/rapports")
    public List<Rapport> getAllRapports() {
        log.debug("REST request to get all Rapports");
        return rapportRepository.findAll();
    }

    /**
     * {@code GET  /rapports/:id} : get the "id" rapport.
     *
     * @param id the id of the rapport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rapport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rapports/{id}")
    public ResponseEntity<Rapport> getRapport(@PathVariable Long id) {
        log.debug("REST request to get Rapport : {}", id);
        Optional<Rapport> rapport = rapportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rapport);
    }

    /**
     * {@code DELETE  /rapports/:id} : delete the "id" rapport.
     *
     * @param id the id of the rapport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rapports/{id}")
    public ResponseEntity<Void> deleteRapport(@PathVariable Long id) {
        log.debug("REST request to delete Rapport : {}", id);
        rapportRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
