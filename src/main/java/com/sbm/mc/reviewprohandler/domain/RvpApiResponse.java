package com.sbm.mc.reviewprohandler.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RvpApiResponse.
 */
@Document(collection = "rvp_api_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    @Field("survey_id")
    private String surveyId;

    @Field("lodging_id")
    private Integer lodgingId;

    @Field("date")
    private Instant date;

    @Field("overallsatsifaction")
    private Double overallsatsifaction;

    @Field("custom_score")
    private Double customScore;

    @Field("plantorevisit")
    private Boolean plantorevisit;

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyId() {
        return this.surveyId;
    }

    public RvpApiResponse surveyId(String surveyId) {
        this.setSurveyId(surveyId);
        return this;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getLodgingId() {
        return this.lodgingId;
    }

    public RvpApiResponse lodgingId(Integer lodgingId) {
        this.setLodgingId(lodgingId);
        return this;
    }

    public void setLodgingId(Integer lodgingId) {
        this.lodgingId = lodgingId;
    }

    public Instant getDate() {
        return this.date;
    }

    public RvpApiResponse date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getOverallsatsifaction() {
        return this.overallsatsifaction;
    }

    public RvpApiResponse overallsatsifaction(Double overallsatsifaction) {
        this.setOverallsatsifaction(overallsatsifaction);
        return this;
    }

    public void setOverallsatsifaction(Double overallsatsifaction) {
        this.overallsatsifaction = overallsatsifaction;
    }

    public Double getCustomScore() {
        return this.customScore;
    }

    public RvpApiResponse customScore(Double customScore) {
        this.setCustomScore(customScore);
        return this;
    }

    public void setCustomScore(Double customScore) {
        this.customScore = customScore;
    }

    public Boolean getPlantorevisit() {
        return this.plantorevisit;
    }

    public RvpApiResponse plantorevisit(Boolean plantorevisit) {
        this.setPlantorevisit(plantorevisit);
        return this;
    }

    public void setPlantorevisit(Boolean plantorevisit) {
        this.plantorevisit = plantorevisit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RvpApiResponse{" +
            "id=" +
            id +
            ", surveyId='" +
            surveyId +
            '\'' +
            ", lodgingId=" +
            lodgingId +
            ", date=" +
            date +
            ", overallsatsifaction=" +
            overallsatsifaction +
            ", customScore=" +
            customScore +
            ", plantorevisit=" +
            plantorevisit +
            ", label='" +
            label +
            '\'' +
            '}'
        );
    }
}
