package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpapilodgingAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.Rvpapilodging;
import com.sbm.mc.reviewprohandler.repository.RvpapilodgingRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RvpapilodgingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpapilodgingResourceIT {

    private static final Integer DEFAULT_IDA = 1;
    private static final Integer UPDATED_IDA = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rvpapilodgings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpapilodgingRepository rvpapilodgingRepository;

    @Autowired
    private MockMvc restRvpapilodgingMockMvc;

    private Rvpapilodging rvpapilodging;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rvpapilodging createEntity() {
        Rvpapilodging rvpapilodging = new Rvpapilodging().ida(DEFAULT_IDA).name(DEFAULT_NAME);
        return rvpapilodging;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rvpapilodging createUpdatedEntity() {
        Rvpapilodging rvpapilodging = new Rvpapilodging().ida(UPDATED_IDA).name(UPDATED_NAME);
        return rvpapilodging;
    }

    @BeforeEach
    public void initTest() {
        rvpapilodgingRepository.deleteAll();
        rvpapilodging = createEntity();
    }

    @Test
    void createRvpapilodging() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rvpapilodging
        var returnedRvpapilodging = om.readValue(
            restRvpapilodgingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpapilodging)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Rvpapilodging.class
        );

        // Validate the Rvpapilodging in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpapilodgingUpdatableFieldsEquals(returnedRvpapilodging, getPersistedRvpapilodging(returnedRvpapilodging));
    }

    @Test
    void createRvpapilodgingWithExistingId() throws Exception {
        // Create the Rvpapilodging with an existing ID
        rvpapilodging.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpapilodgingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpapilodging)))
            .andExpect(status().isBadRequest());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpapilodgings() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        // Get all the rvpapilodgingList
        restRvpapilodgingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpapilodging.getId())))
            .andExpect(jsonPath("$.[*].ida").value(hasItem(DEFAULT_IDA)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getRvpapilodging() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        // Get the rvpapilodging
        restRvpapilodgingMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpapilodging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpapilodging.getId()))
            .andExpect(jsonPath("$.ida").value(DEFAULT_IDA))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingRvpapilodging() throws Exception {
        // Get the rvpapilodging
        restRvpapilodgingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpapilodging() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpapilodging
        Rvpapilodging updatedRvpapilodging = rvpapilodgingRepository.findById(rvpapilodging.getId()).orElseThrow();
        updatedRvpapilodging.ida(UPDATED_IDA).name(UPDATED_NAME);

        restRvpapilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpapilodging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpapilodging))
            )
            .andExpect(status().isOk());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpapilodgingToMatchAllProperties(updatedRvpapilodging);
    }

    @Test
    void putNonExistingRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpapilodging.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpapilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpapilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpapilodging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpapilodgingWithPatch() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpapilodging using partial update
        Rvpapilodging partialUpdatedRvpapilodging = new Rvpapilodging();
        partialUpdatedRvpapilodging.setId(rvpapilodging.getId());

        restRvpapilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpapilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpapilodging))
            )
            .andExpect(status().isOk());

        // Validate the Rvpapilodging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpapilodgingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpapilodging, rvpapilodging),
            getPersistedRvpapilodging(rvpapilodging)
        );
    }

    @Test
    void fullUpdateRvpapilodgingWithPatch() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpapilodging using partial update
        Rvpapilodging partialUpdatedRvpapilodging = new Rvpapilodging();
        partialUpdatedRvpapilodging.setId(rvpapilodging.getId());

        partialUpdatedRvpapilodging.ida(UPDATED_IDA).name(UPDATED_NAME);

        restRvpapilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpapilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpapilodging))
            )
            .andExpect(status().isOk());

        // Validate the Rvpapilodging in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpapilodgingUpdatableFieldsEquals(partialUpdatedRvpapilodging, getPersistedRvpapilodging(partialUpdatedRvpapilodging));
    }

    @Test
    void patchNonExistingRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpapilodging.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpapilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpapilodging))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpapilodging() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpapilodging.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpapilodgingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpapilodging)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rvpapilodging in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpapilodging() throws Exception {
        // Initialize the database
        rvpapilodgingRepository.save(rvpapilodging);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpapilodging
        restRvpapilodgingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpapilodging.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpapilodgingRepository.count();
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

    protected Rvpapilodging getPersistedRvpapilodging(Rvpapilodging rvpapilodging) {
        return rvpapilodgingRepository.findById(rvpapilodging.getId()).orElseThrow();
    }

    protected void assertPersistedRvpapilodgingToMatchAllProperties(Rvpapilodging expectedRvpapilodging) {
        assertRvpapilodgingAllPropertiesEquals(expectedRvpapilodging, getPersistedRvpapilodging(expectedRvpapilodging));
    }

    protected void assertPersistedRvpapilodgingToMatchUpdatableProperties(Rvpapilodging expectedRvpapilodging) {
        assertRvpapilodgingAllUpdatablePropertiesEquals(expectedRvpapilodging, getPersistedRvpapilodging(expectedRvpapilodging));
    }
}
