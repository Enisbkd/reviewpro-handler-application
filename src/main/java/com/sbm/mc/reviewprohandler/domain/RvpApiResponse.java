package com.sbm.mc.reviewprohandler.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RvpApiResponse.
 */
@Document(collection = "rvp_api_response")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Field("survey_id")
    private Integer surveyId;

    @Field("lodging_id")
    private Integer lodgingId;

    @Field("date")
    private ZonedDateTime date;

    @Field("overallsatsifaction")
    private Double overallsatsifaction;

    @Field("custom_score")
    private Double customScore;

    @Field("plantorevisit")
    private Boolean plantorevisit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RvpApiResponse id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSurveyId() {
        return this.surveyId;
    }

    public RvpApiResponse surveyId(Integer surveyId) {
        this.setSurveyId(surveyId);
        return this;
    }

    public void setSurveyId(Integer surveyId) {
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

    public ZonedDateTime getDate() {
        return this.date;
    }

    public RvpApiResponse date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvpApiResponse)) {
            return false;
        }
        return getId() != null && getId().equals(((RvpApiResponse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvpApiResponse{" +
            "id=" + getId() +
            ", surveyId=" + getSurveyId() +
            ", lodgingId=" + getLodgingId() +
            ", date='" + getDate() + "'" +
            ", overallsatsifaction=" + getOverallsatsifaction() +
            ", customScore=" + getCustomScore() +
            ", plantorevisit='" + getPlantorevisit() + "'" +
            "}";
    }
}
