package com.app.covidfree.web.rest;

import com.app.covidfree.domain.EventLogging;
import com.app.covidfree.repository.EventLoggingRepository;
import com.app.covidfree.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.covidfree.domain.EventLogging}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EventLoggingResource {

    private final Logger log = LoggerFactory.getLogger(EventLoggingResource.class);

    private static final String ENTITY_NAME = "eventLogging";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventLoggingRepository eventLoggingRepository;

    public EventLoggingResource(EventLoggingRepository eventLoggingRepository) {
        this.eventLoggingRepository = eventLoggingRepository;
    }

    /**
     * {@code POST  /event-loggings} : Create a new eventLogging.
     *
     * @param eventLogging the eventLogging to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventLogging, or with status {@code 400 (Bad Request)} if the eventLogging has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-loggings")
    public ResponseEntity<EventLogging> createEventLogging(@RequestBody EventLogging eventLogging) throws URISyntaxException {
        log.debug("REST request to save EventLogging : {}", eventLogging);
        if (eventLogging.getId() != null) {
            throw new BadRequestAlertException("A new eventLogging cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventLogging result = eventLoggingRepository.save(eventLogging);
        return ResponseEntity.created(new URI("/api/event-loggings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-loggings} : Updates an existing eventLogging.
     *
     * @param eventLogging the eventLogging to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventLogging,
     * or with status {@code 400 (Bad Request)} if the eventLogging is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventLogging couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-loggings")
    public ResponseEntity<EventLogging> updateEventLogging(@RequestBody EventLogging eventLogging) throws URISyntaxException {
        log.debug("REST request to update EventLogging : {}", eventLogging);
        if (eventLogging.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventLogging result = eventLoggingRepository.save(eventLogging);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventLogging.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-loggings} : get all the eventLoggings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventLoggings in body.
     */
    @GetMapping("/event-loggings")
    public List<EventLogging> getAllEventLoggings() {
        log.debug("REST request to get all EventLoggings");
        return eventLoggingRepository.findAll();
    }

    /**
     * {@code GET  /event-loggings/:id} : get the "id" eventLogging.
     *
     * @param id the id of the eventLogging to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventLogging, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-loggings/{id}")
    public ResponseEntity<EventLogging> getEventLogging(@PathVariable Long id) {
        log.debug("REST request to get EventLogging : {}", id);
        Optional<EventLogging> eventLogging = eventLoggingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(eventLogging);
    }

    /**
     * {@code DELETE  /event-loggings/:id} : delete the "id" eventLogging.
     *
     * @param id the id of the eventLogging to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-loggings/{id}")
    public ResponseEntity<Void> deleteEventLogging(@PathVariable Long id) {
        log.debug("REST request to delete EventLogging : {}", id);
        eventLoggingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
