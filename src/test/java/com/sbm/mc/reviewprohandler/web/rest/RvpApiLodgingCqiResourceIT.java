package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqiAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqi;
import com.sbm.mc.reviewprohandler.repository.RvpApiLodgingCqiRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RvpApiLodgingCqiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpApiLodgingCqiResourceIT {

    private static final Integer DEFAULT_LODGING_ID = 1;
    private static final Integer UPDATED_LODGING_ID = 2;

    private static final String DEFAULT_AVERAGE_CURRENT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_AVERAGE_CURRENT_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_TENDANCY = "AAAAAAAAAA";
    private static final String UPDATED_TENDANCY = "BBBBBBBBBB";

    private static final String DEFAULT_CHANGE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVERAGE_PREVIOUS_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_AVERAGE_PREVIOUS_PERIOD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rvp-api-lodging-cqis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpApiLodgingCqiRepository rvpApiLodgingCqiRepository;

    @Autowired
    private MockMvc restRvpApiLodgingCqiMockMvc;

    private RvpApiLodgingCqi rvpApiLodgingCqi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiLodgingCqi createEntity() {
        RvpApiLodgingCqi rvpApiLodgingCqi = new RvpApiLodgingCqi()
            .lodgingId(DEFAULT_LODGING_ID)
            .averageCurrentPeriod(DEFAULT_AVERAGE_CURRENT_PERIOD)
            .tendancy(DEFAULT_TENDANCY)
            .change(DEFAULT_CHANGE)
            .name(DEFAULT_NAME)
            .averagePreviousPeriod(DEFAULT_AVERAGE_PREVIOUS_PERIOD)
            .fd(DEFAULT_FD)
            .td(DEFAULT_TD);
        return rvpApiLodgingCqi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiLodgingCqi createUpdatedEntity() {
        RvpApiLodgingCqi rvpApiLodgingCqi = new RvpApiLodgingCqi()
            .lodgingId(UPDATED_LODGING_ID)
            .averageCurrentPeriod(UPDATED_AVERAGE_CURRENT_PERIOD)
            .tendancy(UPDATED_TENDANCY)
            .change(UPDATED_CHANGE)
            .name(UPDATED_NAME)
            .averagePreviousPeriod(UPDATED_AVERAGE_PREVIOUS_PERIOD)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);
        return rvpApiLodgingCqi;
    }

    @BeforeEach
    public void initTest() {
        rvpApiLodgingCqiRepository.deleteAll();
        rvpApiLodgingCqi = createEntity();
    }

    @Test
    void createRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RvpApiLodgingCqi
        var returnedRvpApiLodgingCqi = om.readValue(
            restRvpApiLodgingCqiMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingCqi)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RvpApiLodgingCqi.class
        );

        // Validate the RvpApiLodgingCqi in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpApiLodgingCqiUpdatableFieldsEquals(returnedRvpApiLodgingCqi, getPersistedRvpApiLodgingCqi(returnedRvpApiLodgingCqi));
    }

    @Test
    void createRvpApiLodgingCqiWithExistingId() throws Exception {
        // Create the RvpApiLodgingCqi with an existing ID
        rvpApiLodgingCqi.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpApiLodgingCqiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingCqi)))
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpApiLodgingCqis() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        // Get all the rvpApiLodgingCqiList
        restRvpApiLodgingCqiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpApiLodgingCqi.getId().intValue())))
            .andExpect(jsonPath("$.[*].lodgingId").value(hasItem(DEFAULT_LODGING_ID)))
            .andExpect(jsonPath("$.[*].averageCurrentPeriod").value(hasItem(DEFAULT_AVERAGE_CURRENT_PERIOD)))
            .andExpect(jsonPath("$.[*].tendancy").value(hasItem(DEFAULT_TENDANCY)))
            .andExpect(jsonPath("$.[*].change").value(hasItem(DEFAULT_CHANGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].averagePreviousPeriod").value(hasItem(DEFAULT_AVERAGE_PREVIOUS_PERIOD)))
            .andExpect(jsonPath("$.[*].fd").value(hasItem(DEFAULT_FD.toString())))
            .andExpect(jsonPath("$.[*].td").value(hasItem(DEFAULT_TD.toString())));
    }

    @Test
    void getRvpApiLodgingCqi() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        // Get the rvpApiLodgingCqi
        restRvpApiLodgingCqiMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpApiLodgingCqi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpApiLodgingCqi.getId().intValue()))
            .andExpect(jsonPath("$.lodgingId").value(DEFAULT_LODGING_ID))
            .andExpect(jsonPath("$.averageCurrentPeriod").value(DEFAULT_AVERAGE_CURRENT_PERIOD))
            .andExpect(jsonPath("$.tendancy").value(DEFAULT_TENDANCY))
            .andExpect(jsonPath("$.change").value(DEFAULT_CHANGE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.averagePreviousPeriod").value(DEFAULT_AVERAGE_PREVIOUS_PERIOD))
            .andExpect(jsonPath("$.fd").value(DEFAULT_FD.toString()))
            .andExpect(jsonPath("$.td").value(DEFAULT_TD.toString()));
    }

    @Test
    void getNonExistingRvpApiLodgingCqi() throws Exception {
        // Get the rvpApiLodgingCqi
        restRvpApiLodgingCqiMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpApiLodgingCqi() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingCqi
        RvpApiLodgingCqi updatedRvpApiLodgingCqi = rvpApiLodgingCqiRepository.findById(rvpApiLodgingCqi.getId()).orElseThrow();
        updatedRvpApiLodgingCqi
            .lodgingId(UPDATED_LODGING_ID)
            .averageCurrentPeriod(UPDATED_AVERAGE_CURRENT_PERIOD)
            .tendancy(UPDATED_TENDANCY)
            .change(UPDATED_CHANGE)
            .name(UPDATED_NAME)
            .averagePreviousPeriod(UPDATED_AVERAGE_PREVIOUS_PERIOD)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiLodgingCqiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpApiLodgingCqi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpApiLodgingCqi))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpApiLodgingCqiToMatchAllProperties(updatedRvpApiLodgingCqi);
    }

    @Test
    void putNonExistingRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpApiLodgingCqi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiLodgingCqi))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiLodgingCqi))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingCqi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpApiLodgingCqiWithPatch() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingCqi using partial update
        RvpApiLodgingCqi partialUpdatedRvpApiLodgingCqi = new RvpApiLodgingCqi();
        partialUpdatedRvpApiLodgingCqi.setId(rvpApiLodgingCqi.getId());

        partialUpdatedRvpApiLodgingCqi
            .change(UPDATED_CHANGE)
            .averagePreviousPeriod(UPDATED_AVERAGE_PREVIOUS_PERIOD)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiLodgingCqiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiLodgingCqi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiLodgingCqi))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingCqi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiLodgingCqiUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpApiLodgingCqi, rvpApiLodgingCqi),
            getPersistedRvpApiLodgingCqi(rvpApiLodgingCqi)
        );
    }

    @Test
    void fullUpdateRvpApiLodgingCqiWithPatch() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingCqi using partial update
        RvpApiLodgingCqi partialUpdatedRvpApiLodgingCqi = new RvpApiLodgingCqi();
        partialUpdatedRvpApiLodgingCqi.setId(rvpApiLodgingCqi.getId());

        partialUpdatedRvpApiLodgingCqi
            .lodgingId(UPDATED_LODGING_ID)
            .averageCurrentPeriod(UPDATED_AVERAGE_CURRENT_PERIOD)
            .tendancy(UPDATED_TENDANCY)
            .change(UPDATED_CHANGE)
            .name(UPDATED_NAME)
            .averagePreviousPeriod(UPDATED_AVERAGE_PREVIOUS_PERIOD)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiLodgingCqiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiLodgingCqi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiLodgingCqi))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingCqi in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiLodgingCqiUpdatableFieldsEquals(
            partialUpdatedRvpApiLodgingCqi,
            getPersistedRvpApiLodgingCqi(partialUpdatedRvpApiLodgingCqi)
        );
    }

    @Test
    void patchNonExistingRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpApiLodgingCqi.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiLodgingCqi))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiLodgingCqi))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpApiLodgingCqi() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingCqi.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingCqiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpApiLodgingCqi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiLodgingCqi in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpApiLodgingCqi() throws Exception {
        // Initialize the database
        rvpApiLodgingCqiRepository.save(rvpApiLodgingCqi);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpApiLodgingCqi
        restRvpApiLodgingCqiMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpApiLodgingCqi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpApiLodgingCqiRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected RvpApiLodgingCqi getPersistedRvpApiLodgingCqi(RvpApiLodgingCqi rvpApiLodgingCqi) {
        return rvpApiLodgingCqiRepository.findById(rvpApiLodgingCqi.getId()).orElseThrow();
    }

    protected void assertPersistedRvpApiLodgingCqiToMatchAllProperties(RvpApiLodgingCqi expectedRvpApiLodgingCqi) {
        assertRvpApiLodgingCqiAllPropertiesEquals(expectedRvpApiLodgingCqi, getPersistedRvpApiLodgingCqi(expectedRvpApiLodgingCqi));
    }

    protected void assertPersistedRvpApiLodgingCqiToMatchUpdatableProperties(RvpApiLodgingCqi expectedRvpApiLodgingCqi) {
        assertRvpApiLodgingCqiAllUpdatablePropertiesEquals(
            expectedRvpApiLodgingCqi,
            getPersistedRvpApiLodgingCqi(expectedRvpApiLodgingCqi)
        );
    }
}
