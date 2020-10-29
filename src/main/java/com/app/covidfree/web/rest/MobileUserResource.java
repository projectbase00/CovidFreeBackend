package com.app.covidfree.web.rest;

import com.app.covidfree.domain.MobileUser;
import com.app.covidfree.repository.MobileUserRepository;
import com.app.covidfree.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.covidfree.domain.MobileUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MobileUserResource {

    private final Logger log = LoggerFactory.getLogger(MobileUserResource.class);

    private static final String ENTITY_NAME = "mobileUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MobileUserRepository mobileUserRepository;

    public MobileUserResource(MobileUserRepository mobileUserRepository) {
        this.mobileUserRepository = mobileUserRepository;
    }

    /**
     * {@code POST  /mobile-users} : Create a new mobileUser.
     *
     * @param mobileUser the mobileUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobileUser, or with status {@code 400 (Bad Request)} if the mobileUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mobile-users")
    public ResponseEntity<MobileUser> createMobileUser(@RequestBody MobileUser mobileUser) throws URISyntaxException {
        log.debug("REST request to save MobileUser : {}", mobileUser);
        if (mobileUser.getId() != null) {
            throw new BadRequestAlertException("A new mobileUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MobileUser result = mobileUserRepository.save(mobileUser);
        return ResponseEntity.created(new URI("/api/mobile-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mobile-users} : Updates an existing mobileUser.
     *
     * @param mobileUser the mobileUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileUser,
     * or with status {@code 400 (Bad Request)} if the mobileUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobileUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mobile-users")
    public ResponseEntity<MobileUser> updateMobileUser(@RequestBody MobileUser mobileUser) throws URISyntaxException {
        log.debug("REST request to update MobileUser : {}", mobileUser);
        if (mobileUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MobileUser result = mobileUserRepository.save(mobileUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobileUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mobile-users} : get all the mobileUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobileUsers in body.
     */
    @GetMapping("/mobile-users")
    public List<MobileUser> getAllMobileUsers() {
        log.debug("REST request to get all MobileUsers");
        return mobileUserRepository.findAll();
    }

    /**
     * {@code GET  /mobile-users/:id} : get the "id" mobileUser.
     *
     * @param id the id of the mobileUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobileUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mobile-users/{id}")
    public ResponseEntity<MobileUser> getMobileUser(@PathVariable Long id) {
        log.debug("REST request to get MobileUser : {}", id);
        Optional<MobileUser> mobileUser = mobileUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mobileUser);
    }

    @GetMapping("/mobile-users/getphonenumberbyid/{citizenId}")
    public ResponseEntity<String> getMobileUserbyCitizenId(@PathVariable Integer citizenId) {
        log.debug("REST request to get MobileUser : {}", citizenId);
        Optional<MobileUser> mobileUser = mobileUserRepository.findByCitizenId(citizenId);
        return mobileUser.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(mobileUser.get().getPhoneNumber()) :  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * {@code DELETE  /mobile-users/:id} : delete the "id" mobileUser.
     *
     * @param id the id of the mobileUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mobile-users/{id}")
    public ResponseEntity<Void> deleteMobileUser(@PathVariable Long id) {
        log.debug("REST request to delete MobileUser : {}", id);
        mobileUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
