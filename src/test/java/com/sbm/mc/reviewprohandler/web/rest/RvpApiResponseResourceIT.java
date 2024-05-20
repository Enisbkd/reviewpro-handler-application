package com.sbm.mc.reviewprohandler.web.rest;

import static com.sbm.mc.reviewprohandler.domain.RvpApiResponseAsserts.*;
import static com.sbm.mc.reviewprohandler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbm.mc.reviewprohandler.IntegrationTest;
import com.sbm.mc.reviewprohandler.domain.RvpApiResponse;
import com.sbm.mc.reviewprohandler.repository.RvpApiResponseRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link RvpApiResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RvpApiResponseResourceIT {

    private static final Integer DEFAULT_SURVEY_ID = 1;
    private static final Integer UPDATED_SURVEY_ID = 2;

    private static final Integer DEFAULT_LODGING_ID = 1;
    private static final Integer UPDATED_LODGING_ID = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_OVERALLSATSIFACTION = 1D;
    private static final Double UPDATED_OVERALLSATSIFACTION = 2D;

    private static final Double DEFAULT_CUSTOM_SCORE = 1D;
    private static final Double UPDATED_CUSTOM_SCORE = 2D;

    private static final Boolean DEFAULT_PLANTOREVISIT = false;
    private static final Boolean UPDATED_PLANTOREVISIT = true;

    private static final String ENTITY_API_URL = "/api/rvp-api-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RvpApiResponseRepository rvpApiResponseRepository;

    @Autowired
    private MockMvc restRvpApiResponseMockMvc;

    private RvpApiResponse rvpApiResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiResponse createEntity() {
        RvpApiResponse rvpApiResponse = new RvpApiResponse()
            .surveyId(DEFAULT_SURVEY_ID)
            .lodgingId(DEFAULT_LODGING_ID)
            .date(DEFAULT_DATE)
            .overallsatsifaction(DEFAULT_OVERALLSATSIFACTION)
            .customScore(DEFAULT_CUSTOM_SCORE)
            .plantorevisit(DEFAULT_PLANTOREVISIT);
        return rvpApiResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvpApiResponse createUpdatedEntity() {
        RvpApiResponse rvpApiResponse = new RvpApiResponse()
            .surveyId(UPDATED_SURVEY_ID)
            .lodgingId(UPDATED_LODGING_ID)
            .date(UPDATED_DATE)
            .overallsatsifaction(UPDATED_OVERALLSATSIFACTION)
            .customScore(UPDATED_CUSTOM_SCORE)
            .plantorevisit(UPDATED_PLANTOREVISIT);
        return rvpApiResponse;
    }

    @BeforeEach
    public void initTest() {
        rvpApiResponseRepository.deleteAll();
        rvpApiResponse = createEntity();
    }

    @Test
    void createRvpApiResponse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RvpApiResponse
        var returnedRvpApiResponse = om.readValue(
            restRvpApiResponseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiResponse)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RvpApiResponse.class
        );

        // Validate the RvpApiResponse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRvpApiResponseUpdatableFieldsEquals(returnedRvpApiResponse, getPersistedRvpApiResponse(returnedRvpApiResponse));
    }

    @Test
    void createRvpApiResponseWithExistingId() throws Exception {
        // Create the RvpApiResponse with an existing ID
        rvpApiResponse.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvpApiResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiResponse)))
            .andExpect(status().isBadRequest());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRvpApiResponses() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        // Get all the rvpApiResponseList
        restRvpApiResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvpApiResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].surveyId").value(hasItem(DEFAULT_SURVEY_ID)))
            .andExpect(jsonPath("$.[*].lodgingId").value(hasItem(DEFAULT_LODGING_ID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].overallsatsifaction").value(hasItem(DEFAULT_OVERALLSATSIFACTION.doubleValue())))
            .andExpect(jsonPath("$.[*].customScore").value(hasItem(DEFAULT_CUSTOM_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].plantorevisit").value(hasItem(DEFAULT_PLANTOREVISIT.booleanValue())));
    }

    @Test
    void getRvpApiResponse() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        // Get the rvpApiResponse
        restRvpApiResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, rvpApiResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvpApiResponse.getId().intValue()))
            .andExpect(jsonPath("$.surveyId").value(DEFAULT_SURVEY_ID))
            .andExpect(jsonPath("$.lodgingId").value(DEFAULT_LODGING_ID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.overallsatsifaction").value(DEFAULT_OVERALLSATSIFACTION.doubleValue()))
            .andExpect(jsonPath("$.customScore").value(DEFAULT_CUSTOM_SCORE.doubleValue()))
            .andExpect(jsonPath("$.plantorevisit").value(DEFAULT_PLANTOREVISIT.booleanValue()));
    }

    @Test
    void getNonExistingRvpApiResponse() throws Exception {
        // Get the rvpApiResponse
        restRvpApiResponseMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRvpApiResponse() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiResponse
        RvpApiResponse updatedRvpApiResponse = rvpApiResponseRepository.findById(rvpApiResponse.getId()).orElseThrow();
        updatedRvpApiResponse
            .surveyId(UPDATED_SURVEY_ID)
            .lodgingId(UPDATED_LODGING_ID)
            .date(UPDATED_DATE)
            .overallsatsifaction(UPDATED_OVERALLSATSIFACTION)
            .customScore(UPDATED_CUSTOM_SCORE)
            .plantorevisit(UPDATED_PLANTOREVISIT);

        restRvpApiResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRvpApiResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRvpApiResponse))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRvpApiResponseToMatchAllProperties(updatedRvpApiResponse);
    }

    @Test
    void putNonExistingRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rvpApiResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rvpApiResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rvpApiResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRvpApiResponseWithPatch() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiResponse using partial update
        RvpApiResponse partialUpdatedRvpApiResponse = new RvpApiResponse();
        partialUpdatedRvpApiResponse.setId(rvpApiResponse.getId());

        partialUpdatedRvpApiResponse.lodgingId(UPDATED_LODGING_ID).overallsatsifaction(UPDATED_OVERALLSATSIFACTION);

        restRvpApiResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiResponse))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiResponseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRvpApiResponse, rvpApiResponse),
            getPersistedRvpApiResponse(rvpApiResponse)
        );
    }

    @Test
    void fullUpdateRvpApiResponseWithPatch() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rvpApiResponse using partial update
        RvpApiResponse partialUpdatedRvpApiResponse = new RvpApiResponse();
        partialUpdatedRvpApiResponse.setId(rvpApiResponse.getId());

        partialUpdatedRvpApiResponse
            .surveyId(UPDATED_SURVEY_ID)
            .lodgingId(UPDATED_LODGING_ID)
            .date(UPDATED_DATE)
            .overallsatsifaction(UPDATED_OVERALLSATSIFACTION)
            .customScore(UPDATED_CUSTOM_SCORE)
            .plantorevisit(UPDATED_PLANTOREVISIT);

        restRvpApiResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRvpApiResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRvpApiResponse))
            )
            .andExpect(status().isOk());

        // Validate the RvpApiResponse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRvpApiResponseUpdatableFieldsEquals(partialUpdatedRvpApiResponse, getPersistedRvpApiResponse(partialUpdatedRvpApiResponse));
    }

    @Test
    void patchNonExistingRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rvpApiResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rvpApiResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRvpApiResponse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rvpApiResponse.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRvpApiResponseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rvpApiResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RvpApiResponse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRvpApiResponse() throws Exception {
        // Initialize the database
        rvpApiResponseRepository.save(rvpApiResponse);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rvpApiResponse
        restRvpApiResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, rvpApiResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rvpApiResponseRepository.count();
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

    protected RvpApiResponse getPersistedRvpApiResponse(RvpApiResponse rvpApiResponse) {
        return rvpApiResponseRepository.findById(rvpApiResponse.getId()).orElseThrow();
    }

    protected void assertPersistedRvpApiResponseToMatchAllProperties(RvpApiResponse expectedRvpApiResponse) {
        assertRvpApiResponseAllPropertiesEquals(expectedRvpApiResponse, getPersistedRvpApiResponse(expectedRvpApiResponse));
    }

    protected void assertPersistedRvpApiResponseToMatchUpdatableProperties(RvpApiResponse expectedRvpApiResponse) {
        assertRvpApiResponseAllUpdatablePropertiesEquals(expectedRvpApiResponse, getPersistedRvpApiResponse(expectedRvpApiResponse));
    }
}
