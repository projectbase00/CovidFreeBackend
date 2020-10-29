package com.app.covidfree.web.rest;

import com.app.covidfree.domain.OtpCodes;
import com.app.covidfree.repository.OtpCodesRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.app.covidfree.domain.OtpCodes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OtpCodesResource {

    private final Logger log = LoggerFactory.getLogger(OtpCodesResource.class);

    private static final String ENTITY_NAME = "otpCodes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtpCodesRepository otpCodesRepository;

    public OtpCodesResource(OtpCodesRepository otpCodesRepository) {
        this.otpCodesRepository = otpCodesRepository;
    }

    /**
     * {@code POST  /otp-codes} : Create a new otpCodes.
     *
     * @param otpCodes the otpCodes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otpCodes, or with status {@code 400 (Bad Request)} if the otpCodes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otp-codes")
    public ResponseEntity<OtpCodes> createOtpCodes(@RequestBody OtpCodes otpCodes) throws URISyntaxException {
        log.debug("REST request to save OtpCodes : {}", otpCodes);
        if (otpCodes.getId() != null) {
            throw new BadRequestAlertException("A new otpCodes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtpCodes result = otpCodesRepository.save(otpCodes);
        return ResponseEntity.created(new URI("/api/otp-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /otp-codes} : Updates an existing otpCodes.
     *
     * @param otpCodes the otpCodes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otpCodes,
     * or with status {@code 400 (Bad Request)} if the otpCodes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otpCodes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otp-codes")
    public ResponseEntity<OtpCodes> updateOtpCodes(@RequestBody OtpCodes otpCodes) throws URISyntaxException {
        log.debug("REST request to update OtpCodes : {}", otpCodes);
        if (otpCodes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OtpCodes result = otpCodesRepository.save(otpCodes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otpCodes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /otp-codes} : get all the otpCodes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otpCodes in body.
     */
    @GetMapping("/otp-codes")
    public List<OtpCodes> getAllOtpCodes(@RequestParam(required = false) String filter) {
        if ("mobileuser-is-null".equals(filter)) {
            log.debug("REST request to get all OtpCodess where mobileUser is null");
            return StreamSupport
                .stream(otpCodesRepository.findAll().spliterator(), false)
                .filter(otpCodes -> otpCodes.getMobileUser() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all OtpCodes");
        return otpCodesRepository.findAll();
    }

    /**
     * {@code GET  /otp-codes/:id} : get the "id" otpCodes.
     *
     * @param id the id of the otpCodes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otpCodes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otp-codes/{id}")
    public ResponseEntity<OtpCodes> getOtpCodes(@PathVariable Long id) {
        log.debug("REST request to get OtpCodes : {}", id);
        Optional<OtpCodes> otpCodes = otpCodesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(otpCodes);
    }

    /**
     * {@code DELETE  /otp-codes/:id} : delete the "id" otpCodes.
     *
     * @param id the id of the otpCodes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otp-codes/{id}")
    public ResponseEntity<Void> deleteOtpCodes(@PathVariable Long id) {
        log.debug("REST request to delete OtpCodes : {}", id);
        otpCodesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
