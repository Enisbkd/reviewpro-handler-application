package com.sbm.mc.reviewprohandler.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RvpApiLodgingScore.
 */
@Document(collection = "rvp_api_lodging_score")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApiLodgingScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Field("lodging_id")
    private Integer lodgingId;

    @Field("survey_id")
    private Integer surveyId;

    @Field("nps")
    private Double nps;

    @Field("rating")
    private Double rating;

    @Field("custom_score")
    private Double customScore;

    @Field("fd")
    private LocalDate fd;

    @Field("td")
    private LocalDate td;

    public RvpApiLodgingScore(int pid, double prevGri, int[] distribution, double gri) {}

    public RvpApiLodgingScore() {}

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RvpApiLodgingScore id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLodgingId() {
        return this.lodgingId;
    }

    public RvpApiLodgingScore lodgingId(Integer lodgingId) {
        this.setLodgingId(lodgingId);
        return this;
    }

    public void setLodgingId(Integer lodgingId) {
        this.lodgingId = lodgingId;
    }

    public Integer getSurveyId() {
        return this.surveyId;
    }

    public RvpApiLodgingScore surveyId(Integer surveyId) {
        this.setSurveyId(surveyId);
        return this;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Double getNps() {
        return this.nps;
    }

    public RvpApiLodgingScore nps(Double nps) {
        this.setNps(nps);
        return this;
    }

    public void setNps(Double nps) {
        this.nps = nps;
    }

    public Double getRating() {
        return this.rating;
    }

    public RvpApiLodgingScore rating(Double rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getCustomScore() {
        return this.customScore;
    }

    public RvpApiLodgingScore customScore(Double customScore) {
        this.setCustomScore(customScore);
        return this;
    }

    public void setCustomScore(Double customScore) {
        this.customScore = customScore;
    }

    public LocalDate getFd() {
        return this.fd;
    }

    public RvpApiLodgingScore fd(LocalDate fd) {
        this.setFd(fd);
        return this;
    }

    public void setFd(LocalDate fd) {
        this.fd = fd;
    }

    public LocalDate getTd() {
        return this.td;
    }

    public RvpApiLodgingScore td(LocalDate td) {
        this.setTd(td);
        return this;
    }

    public void setTd(LocalDate td) {
        this.td = td;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvpApiLodgingScore)) {
            return false;
        }
        return getId() != null && getId().equals(((RvpApiLodgingScore) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvpApiLodgingScore{" +
            "id=" + getId() +
            ", lodgingId=" + getLodgingId() +
            ", surveyId=" + getSurveyId() +
            ", nps=" + getNps() +
            ", rating=" + getRating() +
            ", customScore=" + getCustomScore() +
            ", fd='" + getFd() + "'" +
            ", td='" + getTd() + "'" +
            "}";
    }
}
