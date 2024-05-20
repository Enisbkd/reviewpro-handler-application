package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReviewAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReview;
import com.sbm.mc.reviewprohandler.repository.RvpApiGlobalReviewRepository;
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
 * Integration tests for the {@link RvpApiGlobalReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpApiGlobalReviewResourceIT {

    private static final Integer DEFAULT_LODGINGID = 1;
    private static final Integer UPDATED_LODGINGID = 2;

    private static final Double DEFAULT_PREV_GRI = 1D;
    private static final Double UPDATED_PREV_GRI = 2D;

    private static final String DEFAULT_DISTRIBUTION = "AAAAAAAAAA";
    private static final String UPDATED_DISTRIBUTION = "BBBBBBBBBB";

    private static final Double DEFAULT_GRI = 1D;
    private static final Double UPDATED_GRI = 2D;

    private static final LocalDate DEFAULT_FD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rvp-api-global-reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpApiGlobalReviewRepository rvpApiGlobalReviewRepository;

    @Autowired
    private MockMvc restRvpApiGlobalReviewMockMvc;

    private RvpApiGlobalReview rvpApiGlobalReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiGlobalReview createEntity() {
        RvpApiGlobalReview rvpApiGlobalReview = new RvpApiGlobalReview()
            .lodgingid(DEFAULT_LODGINGID)
            .prevGri(DEFAULT_PREV_GRI)
            .distribution(DEFAULT_DISTRIBUTION)
            .gri(DEFAULT_GRI)
            .fd(DEFAULT_FD)
            .td(DEFAULT_TD);
        return rvpApiGlobalReview;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiGlobalReview createUpdatedEntity() {
        RvpApiGlobalReview rvpApiGlobalReview = new RvpApiGlobalReview()
            .lodgingid(UPDATED_LODGINGID)
            .prevGri(UPDATED_PREV_GRI)
            .distribution(UPDATED_DISTRIBUTION)
            .gri(UPDATED_GRI)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);
        return rvpApiGlobalReview;
    }

    @BeforeEach
    public void initTest() {
        rvpApiGlobalReviewRepository.deleteAll();
        rvpApiGlobalReview = createEntity();
    }

    @Test
    void createRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RvpApiGlobalReview
        var returnedRvpApiGlobalReview = om.readValue(
            restRvpApiGlobalReviewMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiGlobalReview)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RvpApiGlobalReview.class
        );

        // Validate the RvpApiGlobalReview in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpApiGlobalReviewUpdatableFieldsEquals(
            returnedRvpApiGlobalReview,
            getPersistedRvpApiGlobalReview(returnedRvpApiGlobalReview)
        );
    }

    @Test
    void createRvpApiGlobalReviewWithExistingId() throws Exception {
        // Create the RvpApiGlobalReview with an existing ID
        rvpApiGlobalReview.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpApiGlobalReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiGlobalReview)))
            .andExpect(status().isBadRequest());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpApiGlobalReviews() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        // Get all the rvpApiGlobalReviewList
        restRvpApiGlobalReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpApiGlobalReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].lodgingid").value(hasItem(DEFAULT_LODGINGID)))
            .andExpect(jsonPath("$.[*].prevGri").value(hasItem(DEFAULT_PREV_GRI.doubleValue())))
            .andExpect(jsonPath("$.[*].distribution").value(hasItem(DEFAULT_DISTRIBUTION)))
            .andExpect(jsonPath("$.[*].gri").value(hasItem(DEFAULT_GRI.doubleValue())))
            .andExpect(jsonPath("$.[*].fd").value(hasItem(DEFAULT_FD.toString())))
            .andExpect(jsonPath("$.[*].td").value(hasItem(DEFAULT_TD.toString())));
    }

    @Test
    void getRvpApiGlobalReview() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        // Get the rvpApiGlobalReview
        restRvpApiGlobalReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpApiGlobalReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpApiGlobalReview.getId().intValue()))
            .andExpect(jsonPath("$.lodgingid").value(DEFAULT_LODGINGID))
            .andExpect(jsonPath("$.prevGri").value(DEFAULT_PREV_GRI.doubleValue()))
            .andExpect(jsonPath("$.distribution").value(DEFAULT_DISTRIBUTION))
            .andExpect(jsonPath("$.gri").value(DEFAULT_GRI.doubleValue()))
            .andExpect(jsonPath("$.fd").value(DEFAULT_FD.toString()))
            .andExpect(jsonPath("$.td").value(DEFAULT_TD.toString()));
    }

    @Test
    void getNonExistingRvpApiGlobalReview() throws Exception {
        // Get the rvpApiGlobalReview
        restRvpApiGlobalReviewMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpApiGlobalReview() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiGlobalReview
        RvpApiGlobalReview updatedRvpApiGlobalReview = rvpApiGlobalReviewRepository.findById(rvpApiGlobalReview.getId()).orElseThrow();
        updatedRvpApiGlobalReview
            .lodgingid(UPDATED_LODGINGID)
            .prevGri(UPDATED_PREV_GRI)
            .distribution(UPDATED_DISTRIBUTION)
            .gri(UPDATED_GRI)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiGlobalReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpApiGlobalReview.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpApiGlobalReview))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpApiGlobalReviewToMatchAllProperties(updatedRvpApiGlobalReview);
    }

    @Test
    void putNonExistingRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpApiGlobalReview.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiGlobalReview))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiGlobalReview))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiGlobalReview)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpApiGlobalReviewWithPatch() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiGlobalReview using partial update
        RvpApiGlobalReview partialUpdatedRvpApiGlobalReview = new RvpApiGlobalReview();
        partialUpdatedRvpApiGlobalReview.setId(rvpApiGlobalReview.getId());

        partialUpdatedRvpApiGlobalReview.gri(UPDATED_GRI).fd(UPDATED_FD).td(UPDATED_TD);

        restRvpApiGlobalReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiGlobalReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiGlobalReview))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiGlobalReview in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiGlobalReviewUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpApiGlobalReview, rvpApiGlobalReview),
            getPersistedRvpApiGlobalReview(rvpApiGlobalReview)
        );
    }

    @Test
    void fullUpdateRvpApiGlobalReviewWithPatch() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiGlobalReview using partial update
        RvpApiGlobalReview partialUpdatedRvpApiGlobalReview = new RvpApiGlobalReview();
        partialUpdatedRvpApiGlobalReview.setId(rvpApiGlobalReview.getId());

        partialUpdatedRvpApiGlobalReview
            .lodgingid(UPDATED_LODGINGID)
            .prevGri(UPDATED_PREV_GRI)
            .distribution(UPDATED_DISTRIBUTION)
            .gri(UPDATED_GRI)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiGlobalReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiGlobalReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiGlobalReview))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiGlobalReview in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiGlobalReviewUpdatableFieldsEquals(
            partialUpdatedRvpApiGlobalReview,
            getPersistedRvpApiGlobalReview(partialUpdatedRvpApiGlobalReview)
        );
    }

    @Test
    void patchNonExistingRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpApiGlobalReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiGlobalReview))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiGlobalReview))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpApiGlobalReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiGlobalReview.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiGlobalReviewMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpApiGlobalReview)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiGlobalReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpApiGlobalReview() throws Exception {
        // Initialize the database
        rvpApiGlobalReviewRepository.save(rvpApiGlobalReview);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpApiGlobalReview
        restRvpApiGlobalReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpApiGlobalReview.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpApiGlobalReviewRepository.count();
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

    protected RvpApiGlobalReview getPersistedRvpApiGlobalReview(RvpApiGlobalReview rvpApiGlobalReview) {
        return rvpApiGlobalReviewRepository.findById(rvpApiGlobalReview.getId()).orElseThrow();
    }

    protected void assertPersistedRvpApiGlobalReviewToMatchAllProperties(RvpApiGlobalReview expectedRvpApiGlobalReview) {
        assertRvpApiGlobalReviewAllPropertiesEquals(expectedRvpApiGlobalReview, getPersistedRvpApiGlobalReview(expectedRvpApiGlobalReview));
    }

    protected void assertPersistedRvpApiGlobalReviewToMatchUpdatableProperties(RvpApiGlobalReview expectedRvpApiGlobalReview) {
        assertRvpApiGlobalReviewAllUpdatablePropertiesEquals(
            expectedRvpApiGlobalReview,
            getPersistedRvpApiGlobalReview(expectedRvpApiGlobalReview)
        );
    }
}
