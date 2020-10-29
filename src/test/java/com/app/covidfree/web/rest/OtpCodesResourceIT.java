package com.app.covidfree.web.rest;

import com.app.covidfree.CovidFreeBackendApp;
import com.app.covidfree.domain.OtpCodes;
import com.app.covidfree.repository.OtpCodesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.app.covidfree.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OtpCodesResource} REST controller.
 */
@SpringBootTest(classes = CovidFreeBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OtpCodesResourceIT {

    private static final Integer DEFAULT_CITIZEN_ID = 1;
    private static final Integer UPDATED_CITIZEN_ID = 2;

    private static final String DEFAULT_OTP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OTP_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OtpCodesRepository otpCodesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtpCodesMockMvc;

    private OtpCodes otpCodes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtpCodes createEntity(EntityManager em) {
        OtpCodes otpCodes = new OtpCodes()
            .otpCode(DEFAULT_OTP_CODE)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return otpCodes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtpCodes createUpdatedEntity(EntityManager em) {
        OtpCodes otpCodes = new OtpCodes()
            .otpCode(UPDATED_OTP_CODE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return otpCodes;
    }

    @BeforeEach
    public void initTest() {
        otpCodes = createEntity(em);
    }

    @Test
    @Transactional
    public void createOtpCodes() throws Exception {
        int databaseSizeBeforeCreate = otpCodesRepository.findAll().size();
        // Create the OtpCodes
        restOtpCodesMockMvc.perform(post("/api/otp-codes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otpCodes)))
            .andExpect(status().isCreated());

        // Validate the OtpCodes in the database
        List<OtpCodes> otpCodesList = otpCodesRepository.findAll();
        assertThat(otpCodesList).hasSize(databaseSizeBeforeCreate + 1);
        OtpCodes testOtpCodes = otpCodesList.get(otpCodesList.size() - 1);
        assertThat(testOtpCodes.getOtpCode()).isEqualTo(DEFAULT_OTP_CODE);
        assertThat(testOtpCodes.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testOtpCodes.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createOtpCodesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = otpCodesRepository.findAll().size();

        // Create the OtpCodes with an existing ID
        otpCodes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtpCodesMockMvc.perform(post("/api/otp-codes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otpCodes)))
            .andExpect(status().isBadRequest());

        // Validate the OtpCodes in the database
        List<OtpCodes> otpCodesList = otpCodesRepository.findAll();
        assertThat(otpCodesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOtpCodes() throws Exception {
        // Initialize the database
        otpCodesRepository.saveAndFlush(otpCodes);

        // Get all the otpCodesList
        restOtpCodesMockMvc.perform(get("/api/otp-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otpCodes.getId().intValue())))
            .andExpect(jsonPath("$.[*].citizenId").value(hasItem(DEFAULT_CITIZEN_ID)))
            .andExpect(jsonPath("$.[*].otpCode").value(hasItem(DEFAULT_OTP_CODE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    
    @Test
    @Transactional
    public void getOtpCodes() throws Exception {
        // Initialize the database
        otpCodesRepository.saveAndFlush(otpCodes);

        // Get the otpCodes
        restOtpCodesMockMvc.perform(get("/api/otp-codes/{id}", otpCodes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otpCodes.getId().intValue()))
            .andExpect(jsonPath("$.citizenId").value(DEFAULT_CITIZEN_ID))
            .andExpect(jsonPath("$.otpCode").value(DEFAULT_OTP_CODE))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingOtpCodes() throws Exception {
        // Get the otpCodes
        restOtpCodesMockMvc.perform(get("/api/otp-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOtpCodes() throws Exception {
        // Initialize the database
        otpCodesRepository.saveAndFlush(otpCodes);

        int databaseSizeBeforeUpdate = otpCodesRepository.findAll().size();

        // Update the otpCodes
        OtpCodes updatedOtpCodes = otpCodesRepository.findById(otpCodes.getId()).get();
        // Disconnect from session so that the updates on updatedOtpCodes are not directly saved in db
        em.detach(updatedOtpCodes);
        updatedOtpCodes
            .otpCode(UPDATED_OTP_CODE)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restOtpCodesMockMvc.perform(put("/api/otp-codes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOtpCodes)))
            .andExpect(status().isOk());

        // Validate the OtpCodes in the database
        List<OtpCodes> otpCodesList = otpCodesRepository.findAll();
        assertThat(otpCodesList).hasSize(databaseSizeBeforeUpdate);
        OtpCodes testOtpCodes = otpCodesList.get(otpCodesList.size() - 1);
        assertThat(testOtpCodes.getOtpCode()).isEqualTo(UPDATED_OTP_CODE);
        assertThat(testOtpCodes.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testOtpCodes.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOtpCodes() throws Exception {
        int databaseSizeBeforeUpdate = otpCodesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtpCodesMockMvc.perform(put("/api/otp-codes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(otpCodes)))
            .andExpect(status().isBadRequest());

        // Validate the OtpCodes in the database
        List<OtpCodes> otpCodesList = otpCodesRepository.findAll();
        assertThat(otpCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOtpCodes() throws Exception {
        // Initialize the database
        otpCodesRepository.saveAndFlush(otpCodes);

        int databaseSizeBeforeDelete = otpCodesRepository.findAll().size();

        // Delete the otpCodes
        restOtpCodesMockMvc.perform(delete("/api/otp-codes/{id}", otpCodes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtpCodes> otpCodesList = otpCodesRepository.findAll();
        assertThat(otpCodesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
