package com.app.covidfree.web.rest;

import com.app.covidfree.CovidFreeBackendApp;
import com.app.covidfree.domain.EventLogging;
import com.app.covidfree.repository.EventLoggingRepository;

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
 * Integration tests for the {@link EventLoggingResource} REST controller.
 */
@SpringBootTest(classes = CovidFreeBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EventLoggingResourceIT {

    private static final Integer DEFAULT_CITIZEN_ID = 1;
    private static final Integer UPDATED_CITIZEN_ID = 2;

    private static final Integer DEFAULT_LOG_TYPE = 1;
    private static final Integer UPDATED_LOG_TYPE = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EventLoggingRepository eventLoggingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventLoggingMockMvc;

    private EventLogging eventLogging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventLogging createEntity(EntityManager em) {
        EventLogging eventLogging = new EventLogging()
            .citizenId(DEFAULT_CITIZEN_ID)
            .logType(DEFAULT_LOG_TYPE)
            .message(DEFAULT_MESSAGE)
            .createDate(DEFAULT_CREATE_DATE);
        return eventLogging;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventLogging createUpdatedEntity(EntityManager em) {
        EventLogging eventLogging = new EventLogging()
            .citizenId(UPDATED_CITIZEN_ID)
            .logType(UPDATED_LOG_TYPE)
            .message(UPDATED_MESSAGE)
            .createDate(UPDATED_CREATE_DATE);
        return eventLogging;
    }

    @BeforeEach
    public void initTest() {
        eventLogging = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventLogging() throws Exception {
        int databaseSizeBeforeCreate = eventLoggingRepository.findAll().size();
        // Create the EventLogging
        restEventLoggingMockMvc.perform(post("/api/event-loggings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLogging)))
            .andExpect(status().isCreated());

        // Validate the EventLogging in the database
        List<EventLogging> eventLoggingList = eventLoggingRepository.findAll();
        assertThat(eventLoggingList).hasSize(databaseSizeBeforeCreate + 1);
        EventLogging testEventLogging = eventLoggingList.get(eventLoggingList.size() - 1);
        assertThat(testEventLogging.getCitizenId()).isEqualTo(DEFAULT_CITIZEN_ID);
        assertThat(testEventLogging.getLogType()).isEqualTo(DEFAULT_LOG_TYPE);
        assertThat(testEventLogging.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEventLogging.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createEventLoggingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventLoggingRepository.findAll().size();

        // Create the EventLogging with an existing ID
        eventLogging.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventLoggingMockMvc.perform(post("/api/event-loggings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLogging)))
            .andExpect(status().isBadRequest());

        // Validate the EventLogging in the database
        List<EventLogging> eventLoggingList = eventLoggingRepository.findAll();
        assertThat(eventLoggingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEventLoggings() throws Exception {
        // Initialize the database
        eventLoggingRepository.saveAndFlush(eventLogging);

        // Get all the eventLoggingList
        restEventLoggingMockMvc.perform(get("/api/event-loggings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventLogging.getId().intValue())))
            .andExpect(jsonPath("$.[*].citizenId").value(hasItem(DEFAULT_CITIZEN_ID)))
            .andExpect(jsonPath("$.[*].logType").value(hasItem(DEFAULT_LOG_TYPE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))));
    }
    
    @Test
    @Transactional
    public void getEventLogging() throws Exception {
        // Initialize the database
        eventLoggingRepository.saveAndFlush(eventLogging);

        // Get the eventLogging
        restEventLoggingMockMvc.perform(get("/api/event-loggings/{id}", eventLogging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventLogging.getId().intValue()))
            .andExpect(jsonPath("$.citizenId").value(DEFAULT_CITIZEN_ID))
            .andExpect(jsonPath("$.logType").value(DEFAULT_LOG_TYPE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingEventLogging() throws Exception {
        // Get the eventLogging
        restEventLoggingMockMvc.perform(get("/api/event-loggings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventLogging() throws Exception {
        // Initialize the database
        eventLoggingRepository.saveAndFlush(eventLogging);

        int databaseSizeBeforeUpdate = eventLoggingRepository.findAll().size();

        // Update the eventLogging
        EventLogging updatedEventLogging = eventLoggingRepository.findById(eventLogging.getId()).get();
        // Disconnect from session so that the updates on updatedEventLogging are not directly saved in db
        em.detach(updatedEventLogging);
        updatedEventLogging
            .citizenId(UPDATED_CITIZEN_ID)
            .logType(UPDATED_LOG_TYPE)
            .message(UPDATED_MESSAGE)
            .createDate(UPDATED_CREATE_DATE);

        restEventLoggingMockMvc.perform(put("/api/event-loggings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventLogging)))
            .andExpect(status().isOk());

        // Validate the EventLogging in the database
        List<EventLogging> eventLoggingList = eventLoggingRepository.findAll();
        assertThat(eventLoggingList).hasSize(databaseSizeBeforeUpdate);
        EventLogging testEventLogging = eventLoggingList.get(eventLoggingList.size() - 1);
        assertThat(testEventLogging.getCitizenId()).isEqualTo(UPDATED_CITIZEN_ID);
        assertThat(testEventLogging.getLogType()).isEqualTo(UPDATED_LOG_TYPE);
        assertThat(testEventLogging.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEventLogging.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEventLogging() throws Exception {
        int databaseSizeBeforeUpdate = eventLoggingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventLoggingMockMvc.perform(put("/api/event-loggings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventLogging)))
            .andExpect(status().isBadRequest());

        // Validate the EventLogging in the database
        List<EventLogging> eventLoggingList = eventLoggingRepository.findAll();
        assertThat(eventLoggingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventLogging() throws Exception {
        // Initialize the database
        eventLoggingRepository.saveAndFlush(eventLogging);

        int databaseSizeBeforeDelete = eventLoggingRepository.findAll().size();

        // Delete the eventLogging
        restEventLoggingMockMvc.perform(delete("/api/event-loggings/{id}", eventLogging.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventLogging> eventLoggingList = eventLoggingRepository.findAll();
        assertThat(eventLoggingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
