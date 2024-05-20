package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScoreAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScore;
import com.sbm.mc.reviewprohandler.repository.RvpApiLodgingScoreRepository;
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
 * Integration tests for the {@link RvpApiLodgingScoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpApiLodgingScoreResourceIT {

    private static final Integer DEFAULT_LODGING_ID = 1;
    private static final Integer UPDATED_LODGING_ID = 2;

    private static final Integer DEFAULT_SURVEY_ID = 1;
    private static final Integer UPDATED_SURVEY_ID = 2;

    private static final Double DEFAULT_NPS = 1D;
    private static final Double UPDATED_NPS = 2D;

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;

    private static final Double DEFAULT_CUSTOM_SCORE = 1D;
    private static final Double UPDATED_CUSTOM_SCORE = 2D;

    private static final LocalDate DEFAULT_FD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TD = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rvp-api-lodging-scores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpApiLodgingScoreRepository rvpApiLodgingScoreRepository;

    @Autowired
    private MockMvc restRvpApiLodgingScoreMockMvc;

    private RvpApiLodgingScore rvpApiLodgingScore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiLodgingScore createEntity() {
        RvpApiLodgingScore rvpApiLodgingScore = new RvpApiLodgingScore()
            .lodgingId(DEFAULT_LODGING_ID)
            .surveyId(DEFAULT_SURVEY_ID)
            .nps(DEFAULT_NPS)
            .rating(DEFAULT_RATING)
            .customScore(DEFAULT_CUSTOM_SCORE)
            .fd(DEFAULT_FD)
            .td(DEFAULT_TD);
        return rvpApiLodgingScore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiLodgingScore createUpdatedEntity() {
        RvpApiLodgingScore rvpApiLodgingScore = new RvpApiLodgingScore()
            .lodgingId(UPDATED_LODGING_ID)
            .surveyId(UPDATED_SURVEY_ID)
            .nps(UPDATED_NPS)
            .rating(UPDATED_RATING)
            .customScore(UPDATED_CUSTOM_SCORE)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);
        return rvpApiLodgingScore;
    }

    @BeforeEach
    public void initTest() {
        rvpApiLodgingScoreRepository.deleteAll();
        rvpApiLodgingScore = createEntity();
    }

    @Test
    void createRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RvpApiLodgingScore
        var returnedRvpApiLodgingScore = om.readValue(
            restRvpApiLodgingScoreMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingScore)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RvpApiLodgingScore.class
        );

        // Validate the RvpApiLodgingScore in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpApiLodgingScoreUpdatableFieldsEquals(
            returnedRvpApiLodgingScore,
            getPersistedRvpApiLodgingScore(returnedRvpApiLodgingScore)
        );
    }

    @Test
    void createRvpApiLodgingScoreWithExistingId() throws Exception {
        // Create the RvpApiLodgingScore with an existing ID
        rvpApiLodgingScore.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpApiLodgingScoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingScore)))
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpApiLodgingScores() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        // Get all the rvpApiLodgingScoreList
        restRvpApiLodgingScoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpApiLodgingScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].lodgingId").value(hasItem(DEFAULT_LODGING_ID)))
            .andExpect(jsonPath("$.[*].surveyId").value(hasItem(DEFAULT_SURVEY_ID)))
            .andExpect(jsonPath("$.[*].nps").value(hasItem(DEFAULT_NPS.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].customScore").value(hasItem(DEFAULT_CUSTOM_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].fd").value(hasItem(DEFAULT_FD.toString())))
            .andExpect(jsonPath("$.[*].td").value(hasItem(DEFAULT_TD.toString())));
    }

    @Test
    void getRvpApiLodgingScore() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        // Get the rvpApiLodgingScore
        restRvpApiLodgingScoreMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpApiLodgingScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpApiLodgingScore.getId().intValue()))
            .andExpect(jsonPath("$.lodgingId").value(DEFAULT_LODGING_ID))
            .andExpect(jsonPath("$.surveyId").value(DEFAULT_SURVEY_ID))
            .andExpect(jsonPath("$.nps").value(DEFAULT_NPS.doubleValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.customScore").value(DEFAULT_CUSTOM_SCORE.doubleValue()))
            .andExpect(jsonPath("$.fd").value(DEFAULT_FD.toString()))
            .andExpect(jsonPath("$.td").value(DEFAULT_TD.toString()));
    }

    @Test
    void getNonExistingRvpApiLodgingScore() throws Exception {
        // Get the rvpApiLodgingScore
        restRvpApiLodgingScoreMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpApiLodgingScore() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingScore
        RvpApiLodgingScore updatedRvpApiLodgingScore = rvpApiLodgingScoreRepository.findById(rvpApiLodgingScore.getId()).orElseThrow();
        updatedRvpApiLodgingScore
            .lodgingId(UPDATED_LODGING_ID)
            .surveyId(UPDATED_SURVEY_ID)
            .nps(UPDATED_NPS)
            .rating(UPDATED_RATING)
            .customScore(UPDATED_CUSTOM_SCORE)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiLodgingScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpApiLodgingScore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpApiLodgingScore))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpApiLodgingScoreToMatchAllProperties(updatedRvpApiLodgingScore);
    }

    @Test
    void putNonExistingRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpApiLodgingScore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiLodgingScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiLodgingScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiLodgingScore)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpApiLodgingScoreWithPatch() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingScore using partial update
        RvpApiLodgingScore partialUpdatedRvpApiLodgingScore = new RvpApiLodgingScore();
        partialUpdatedRvpApiLodgingScore.setId(rvpApiLodgingScore.getId());

        partialUpdatedRvpApiLodgingScore.rating(UPDATED_RATING).td(UPDATED_TD);

        restRvpApiLodgingScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiLodgingScore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiLodgingScore))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingScore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiLodgingScoreUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpApiLodgingScore, rvpApiLodgingScore),
            getPersistedRvpApiLodgingScore(rvpApiLodgingScore)
        );
    }

    @Test
    void fullUpdateRvpApiLodgingScoreWithPatch() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiLodgingScore using partial update
        RvpApiLodgingScore partialUpdatedRvpApiLodgingScore = new RvpApiLodgingScore();
        partialUpdatedRvpApiLodgingScore.setId(rvpApiLodgingScore.getId());

        partialUpdatedRvpApiLodgingScore
            .lodgingId(UPDATED_LODGING_ID)
            .surveyId(UPDATED_SURVEY_ID)
            .nps(UPDATED_NPS)
            .rating(UPDATED_RATING)
            .customScore(UPDATED_CUSTOM_SCORE)
            .fd(UPDATED_FD)
            .td(UPDATED_TD);

        restRvpApiLodgingScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiLodgingScore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiLodgingScore))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiLodgingScore in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiLodgingScoreUpdatableFieldsEquals(
            partialUpdatedRvpApiLodgingScore,
            getPersistedRvpApiLodgingScore(partialUpdatedRvpApiLodgingScore)
        );
    }

    @Test
    void patchNonExistingRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpApiLodgingScore.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiLodgingScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiLodgingScore))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpApiLodgingScore() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiLodgingScore.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiLodgingScoreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpApiLodgingScore)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiLodgingScore in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpApiLodgingScore() throws Exception {
        // Initialize the database
        rvpApiLodgingScoreRepository.save(rvpApiLodgingScore);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpApiLodgingScore
        restRvpApiLodgingScoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpApiLodgingScore.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpApiLodgingScoreRepository.count();
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

    protected RvpApiLodgingScore getPersistedRvpApiLodgingScore(RvpApiLodgingScore rvpApiLodgingScore) {
        return rvpApiLodgingScoreRepository.findById(rvpApiLodgingScore.getId()).orElseThrow();
    }

    protected void assertPersistedRvpApiLodgingScoreToMatchAllProperties(RvpApiLodgingScore expectedRvpApiLodgingScore) {
        assertRvpApiLodgingScoreAllPropertiesEquals(expectedRvpApiLodgingScore, getPersistedRvpApiLodgingScore(expectedRvpApiLodgingScore));
    }

    protected void assertPersistedRvpApiLodgingScoreToMatchUpdatableProperties(RvpApiLodgingScore expectedRvpApiLodgingScore) {
        assertRvpApiLodgingScoreAllUpdatablePropertiesEquals(
            expectedRvpApiLodgingScore,
            getPersistedRvpApiLodgingScore(expectedRvpApiLodgingScore)
        );
    }
}
