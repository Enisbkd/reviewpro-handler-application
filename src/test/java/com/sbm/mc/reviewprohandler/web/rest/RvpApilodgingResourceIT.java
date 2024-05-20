package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpApilodgingAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.RvpApilodging;
import com.sbm.mc.reviewprohandler.repository.RvpApilodgingRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RvpApilodgingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpApilodgingResourceIT {

    private static final Integer DEFAULT_IDA = 1;
    private static final Integer UPDATED_IDA = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rvp-apilodgings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpApilodgingRepository rvpApilodgingRepository;

    @Autowired
    private MockMvc restRvpApilodgingMockMvc;

    private RvpApilodging rvpApilodging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApilodging createEntity() {
        RvpApilodging rvpApilodging = new RvpApilodging().ida(DEFAULT_IDA).name(DEFAULT_NAME);
        return rvpApilodging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApilodging createUpdatedEntity() {
        RvpApilodging rvpApilodging = new RvpApilodging().ida(UPDATED_IDA).name(UPDATED_NAME);
        return rvpApilodging;
    }

    @BeforeEach
    public void initTest() {
        rvpApilodgingRepository.deleteAll();
        rvpApilodging = createEntity();
    }

    @Test
    void createRvpApilodging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RvpApilodging
        var returnedRvpApilodging = om.readValue(
            restRvpApilodgingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApilodging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RvpApilodging.class
        );

        // Validate the RvpApilodging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpApilodgingUpdatableFieldsEquals(returnedRvpApilodging, getPersistedRvpApilodging(returnedRvpApilodging));
    }

    @Test
    void createRvpApilodgingWithExistingId() throws Exception {
        // Create the RvpApilodging with an existing ID
        rvpApilodging.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpApilodgingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApilodging)))
            .andExpect(status().isBadRequest());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpApilodgings() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        // Get all the rvpApilodgingList
        restRvpApilodgingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpApilodging.getId())))
            .andExpect(jsonPath("$.[*].ida").value(hasItem(DEFAULT_IDA)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getRvpApilodging() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        // Get the rvpApilodging
        restRvpApilodgingMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpApilodging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpApilodging.getId()))
            .andExpect(jsonPath("$.ida").value(DEFAULT_IDA))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingRvpApilodging() throws Exception {
        // Get the rvpApilodging
        restRvpApilodgingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpApilodging() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApilodging
        RvpApilodging updatedRvpApilodging = rvpApilodgingRepository.findById(rvpApilodging.getId()).orElseThrow();
        updatedRvpApilodging.ida(UPDATED_IDA).name(UPDATED_NAME);

        restRvpApilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpApilodging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpApilodging))
            )
            .andExpect(status().isOk());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpApilodgingToMatchAllProperties(updatedRvpApilodging);
    }

    @Test
    void putNonExistingRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpApilodging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApilodging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpApilodgingWithPatch() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApilodging using partial update
        RvpApilodging partialUpdatedRvpApilodging = new RvpApilodging();
        partialUpdatedRvpApilodging.setId(rvpApilodging.getId());

        restRvpApilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApilodging))
            )
            .andExpect(status().isOk());

        // Validate the RvpApilodging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApilodgingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpApilodging, rvpApilodging),
            getPersistedRvpApilodging(rvpApilodging)
        );
    }

    @Test
    void fullUpdateRvpApilodgingWithPatch() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApilodging using partial update
        RvpApilodging partialUpdatedRvpApilodging = new RvpApilodging();
        partialUpdatedRvpApilodging.setId(rvpApilodging.getId());

        partialUpdatedRvpApilodging.ida(UPDATED_IDA).name(UPDATED_NAME);

        restRvpApilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApilodging))
            )
            .andExpect(status().isOk());

        // Validate the RvpApilodging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApilodgingUpdatableFieldsEquals(partialUpdatedRvpApilodging, getPersistedRvpApilodging(partialUpdatedRvpApilodging));
    }

    @Test
    void patchNonExistingRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpApilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpApilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApilodgingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpApilodging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpApilodging() throws Exception {
        // Initialize the database
        rvpApilodgingRepository.save(rvpApilodging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpApilodging
        restRvpApilodgingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpApilodging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpApilodgingRepository.count();
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

    protected RvpApilodging getPersistedRvpApilodging(RvpApilodging rvpApilodging) {
        return rvpApilodgingRepository.findById(rvpApilodging.getId()).orElseThrow();
    }

    protected void assertPersistedRvpApilodgingToMatchAllProperties(RvpApilodging expectedRvpApilodging) {
        assertRvpApilodgingAllPropertiesEquals(expectedRvpApilodging, getPersistedRvpApilodging(expectedRvpApilodging));
    }

    protected void assertPersistedRvpApilodgingToMatchUpdatableProperties(RvpApilodging expectedRvpApilodging) {
        assertRvpApilodgingAllUpdatablePropertiesEquals(expectedRvpApilodging, getPersistedRvpApilodging(expectedRvpApilodging));
    }
}
