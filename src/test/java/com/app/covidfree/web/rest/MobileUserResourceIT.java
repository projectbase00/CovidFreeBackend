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

    private static final Integer DEFAULT_RODNE_CISLO = 1;
    private static final Integer UPDATED_RODNE_CISLO = 2;

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IDCARD_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IDCARD_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IDCARD_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IDCARD_IMAGE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_VALID = false;
    private static final Boolean UPDATED_VALID = true;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

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
            .rodneCislo(DEFAULT_RODNE_CISLO)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .idcardImage(DEFAULT_IDCARD_IMAGE)
            .idcardImageContentType(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(DEFAULT_VALID)
            .status(DEFAULT_STATUS)
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
            .rodneCislo(UPDATED_RODNE_CISLO)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .idcardImage(UPDATED_IDCARD_IMAGE)
            .idcardImageContentType(UPDATED_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(UPDATED_VALID)
            .status(UPDATED_STATUS)
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
        assertThat(testMobileUser.getRodneCislo()).isEqualTo(DEFAULT_RODNE_CISLO);
        assertThat(testMobileUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testMobileUser.getIdcardImage()).isEqualTo(DEFAULT_IDCARD_IMAGE);
        assertThat(testMobileUser.getIdcardImageContentType()).isEqualTo(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE);
        assertThat(testMobileUser.isValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testMobileUser.isStatus()).isEqualTo(DEFAULT_STATUS);
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
            .andExpect(jsonPath("$.[*].rodneCislo").value(hasItem(DEFAULT_RODNE_CISLO)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].idcardImageContentType").value(hasItem(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].idcardImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_IDCARD_IMAGE))))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
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
            .andExpect(jsonPath("$.rodneCislo").value(DEFAULT_RODNE_CISLO))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.idcardImageContentType").value(DEFAULT_IDCARD_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.idcardImage").value(Base64Utils.encodeToString(DEFAULT_IDCARD_IMAGE)))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
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
            .rodneCislo(UPDATED_RODNE_CISLO)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .idcardImage(UPDATED_IDCARD_IMAGE)
            .idcardImageContentType(UPDATED_IDCARD_IMAGE_CONTENT_TYPE)
            .valid(UPDATED_VALID)
            .status(UPDATED_STATUS)
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
        assertThat(testMobileUser.getRodneCislo()).isEqualTo(UPDATED_RODNE_CISLO);
        assertThat(testMobileUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testMobileUser.getIdcardImage()).isEqualTo(UPDATED_IDCARD_IMAGE);
        assertThat(testMobileUser.getIdcardImageContentType()).isEqualTo(UPDATED_IDCARD_IMAGE_CONTENT_TYPE);
        assertThat(testMobileUser.isValid()).isEqualTo(UPDATED_VALID);
        assertThat(testMobileUser.isStatus()).isEqualTo(UPDATED_STATUS);
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
