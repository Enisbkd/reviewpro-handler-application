package com.sbm.mc.reviewprohandler.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RvpApiSurvey.
 */
@Document(collection = "rvp_api_survey")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApiSurvey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Field("overall_score_enabled")
    private Boolean overallScoreEnabled;

    @Field("languages")
    private String languages;

    @Field("out_of")
    private Integer outOf;

    @Field("name")
    private String name;

    @Field("actibe")
    private Boolean actibe;

    @Field("pids")
    private String pids;

    @Field("primary")
    private Boolean primary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RvpApiSurvey id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getOverallScoreEnabled() {
        return this.overallScoreEnabled;
    }

    public RvpApiSurvey overallScoreEnabled(Boolean overallScoreEnabled) {
        this.setOverallScoreEnabled(overallScoreEnabled);
        return this;
    }

    public void setOverallScoreEnabled(Boolean overallScoreEnabled) {
        this.overallScoreEnabled = overallScoreEnabled;
    }

    public String getLanguages() {
        return this.languages;
    }

    public RvpApiSurvey languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getOutOf() {
        return this.outOf;
    }

    public RvpApiSurvey outOf(Integer outOf) {
        this.setOutOf(outOf);
        return this;
    }

    public void setOutOf(Integer outOf) {
        this.outOf = outOf;
    }

    public String getName() {
        return this.name;
    }

    public RvpApiSurvey name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActibe() {
        return this.actibe;
    }

    public RvpApiSurvey actibe(Boolean actibe) {
        this.setActibe(actibe);
        return this;
    }

    public void setActibe(Boolean actibe) {
        this.actibe = actibe;
    }

    public String getPids() {
        return this.pids;
    }

    public RvpApiSurvey pids(String pids) {
        this.setPids(pids);
        return this;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public Boolean getPrimary() {
        return this.primary;
    }

    public RvpApiSurvey primary(Boolean primary) {
        this.setPrimary(primary);
        return this;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvpApiSurvey)) {
            return false;
        }
        return getId() != null && getId().equals(((RvpApiSurvey) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvpApiSurvey{" +
            "id=" + getId() +
            ", overallScoreEnabled='" + getOverallScoreEnabled() + "'" +
            ", languages='" + getLanguages() + "'" +
            ", outOf=" + getOutOf() +
            ", name='" + getName() + "'" +
            ", actibe='" + getActibe() + "'" +
            ", pids='" + getPids() + "'" +
            ", primary='" + getPrimary() + "'" +
            "}";
    }
}
