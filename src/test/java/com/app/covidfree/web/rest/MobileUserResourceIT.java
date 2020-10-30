package com.app.covidfree.web.rest;

import com.app.covidfree.CovidFreeBackendApp;
import com.app.covidfree.domain.MobileUser;
import com.app.covidfree.repository.MobileUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link MobileUserResource} REST controller.
 */
@SpringBootTest(classes = CovidFreeBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MobileUserResourceIT {

    private static final Integer DEFAULT_CITIZEN_ID = 1;
    private static final Integer UPDATED_CITIZEN_ID = 2;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IDCARD_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IDCARD_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IDCARD_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IDCARD_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final Integer DEFAULT_STATUS = 0;
    private static final Integer UPDATED_STATUS = 1;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MobileUserRepository mobileUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobileUserMockMvc;

    private MobileUser mobileUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileUser createEntity(EntityManager em) {
        MobileUser mobileUser = new MobileUser()
            .citizenId(DEFAULT_CITIZEN_ID)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hash(DEFAULT_HASH)
            .idcardImage(DEFAULT_IDCARD_IMAGE)
            .idcardImageContentType(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(DEFAULT_VALID)
            .statusType(DEFAULT_STATUS)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return mobileUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobileUser createUpdatedEntity(EntityManager em) {
        MobileUser mobileUser = new MobileUser()
            .citizenId(UPDATED_CITIZEN_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hash(UPDATED_HASH)
            .idcardImage(UPDATED_IDCARD_IMAGE)
            .idcardImageContentType(UPDATED_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(UPDATED_VALID)
            .statusType(UPDATED_STATUS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return mobileUser;
    }

    @BeforeEach
    public void initTest() {
        mobileUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMobileUser() throws Exception {
        int databaseSizeBeforeCreate = mobileUserRepository.findAll().size();
        // Create the MobileUser
        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isCreated());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeCreate + 1);
        MobileUser testMobileUser = mobileUserList.get(mobileUserList.size() - 1);
        assertThat(testMobileUser.getCitizenId()).isEqualTo(DEFAULT_CITIZEN_ID);
        assertThat(testMobileUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testMobileUser.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testMobileUser.getIdcardImage()).isEqualTo(DEFAULT_IDCARD_IMAGE);
        assertThat(testMobileUser.getIdcardImageContentType()).isEqualTo(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE);
        assertThat(testMobileUser.isValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testMobileUser.getStatusType()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMobileUser.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testMobileUser.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createMobileUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mobileUserRepository.findAll().size();

        // Create the MobileUser with an existing ID
        mobileUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobileUserMockMvc.perform(post("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMobileUsers() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get all the mobileUserList
        restMobileUserMockMvc.perform(get("/api/mobile-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].citizenId").value(hasItem(DEFAULT_CITIZEN_ID)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].idcardImageContentType").value(hasItem(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].idcardImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_IDCARD_IMAGE))))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))));
    }
    
    @Test
    @Transactional
    public void getMobileUser() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        // Get the mobileUser
        restMobileUserMockMvc.perform(get("/api/mobile-users/{id}", mobileUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobileUser.getId().intValue()))
            .andExpect(jsonPath("$.citizenId").value(DEFAULT_CITIZEN_ID))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.idcardImageContentType").value(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.idcardImage").value(Base64Utils.encodeToString(DEFAULT_IDCARD_IMAGE)))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingMobileUser() throws Exception {
        // Get the mobileUser
        restMobileUserMockMvc.perform(get("/api/mobile-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMobileUser() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        int databaseSizeBeforeUpdate = mobileUserRepository.findAll().size();

        // Update the mobileUser
        MobileUser updatedMobileUser = mobileUserRepository.findById(mobileUser.getId()).get();
        // Disconnect from session so that the updates on updatedMobileUser are not directly saved in db
        em.detach(updatedMobileUser);
        updatedMobileUser
            .citizenId(UPDATED_CITIZEN_ID)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hash(UPDATED_HASH)
            .idcardImage(UPDATED_IDCARD_IMAGE)
            .idcardImageContentType(UPDATED_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(UPDATED_VALID)
            .statusType(UPDATED_STATUS)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restMobileUserMockMvc.perform(put("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMobileUser)))
            .andExpect(status().isOk());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeUpdate);
        MobileUser testMobileUser = mobileUserList.get(mobileUserList.size() - 1);
        assertThat(testMobileUser.getCitizenId()).isEqualTo(UPDATED_CITIZEN_ID);
        assertThat(testMobileUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testMobileUser.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testMobileUser.getIdcardImage()).isEqualTo(UPDATED_IDCARD_IMAGE);
        assertThat(testMobileUser.getIdcardImageContentType()).isEqualTo(UPDATED_IDCARD_IMAGE_CONTENT_TYPE);
        assertThat(testMobileUser.isValid()).isEqualTo(UPDATED_VALID);
        assertThat(testMobileUser.getStatusType()).isEqualTo(UPDATED_STATUS);
        assertThat(testMobileUser.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testMobileUser.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMobileUser() throws Exception {
        int databaseSizeBeforeUpdate = mobileUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobileUserMockMvc.perform(put("/api/mobile-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobileUser)))
            .andExpect(status().isBadRequest());

        // Validate the MobileUser in the database
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMobileUser() throws Exception {
        // Initialize the database
        mobileUserRepository.saveAndFlush(mobileUser);

        int databaseSizeBeforeDelete = mobileUserRepository.findAll().size();

        // Delete the mobileUser
        restMobileUserMockMvc.perform(delete("/api/mobile-users/{id}", mobileUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MobileUser> mobileUserList = mobileUserRepository.findAll();
        assertThat(mobileUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
