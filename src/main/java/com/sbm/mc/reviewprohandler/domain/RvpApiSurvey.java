package com.sbm.mc.reviewprohandler.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
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
    @JsonProperty("id")
    private String id;

    @Field("overall_score_enabled")
    private Boolean overallScoreEnabled;

    @Field("languages")
    private String languages;

    @JsonProperty("outOf")
    private Integer outOf;

    @Field("name")
    private String name;

    @Field("active")
    private Boolean active;

    @Field("pids")
    private String pids;

    @Field("primary")
    private Boolean primary;

    public String getId() {
        return this.id;
    }

    public RvpApiSurvey id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
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

    public RvpApiSurvey languages(List<String> languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages.toString();
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

    public Boolean getActive() {
        return this.active;
    }

    public RvpApiSurvey active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPids() {
        return this.pids;
    }

    public RvpApiSurvey pids(List<String> pids) {
        this.setPids(pids);
        return this;
    }

    public void setPids(List<String> pids) {
        this.pids = pids.toString();
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
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RvpApiSurvey{" +
            "id=" +
            getId() +
            ", overallScoreEnabled='" +
            getOverallScoreEnabled() +
            "'" +
            ", languages='" +
            getLanguages() +
            "'" +
            ", outOf=" +
            getOutOf() +
            ", name='" +
            getName() +
            "'" +
            ", active='" +
            getActive() +
            "'" +
            ", pids='" +
            getPids() +
            "'" +
            ", primary='" +
            getPrimary() +
            "'" +
            "}"
        );
    }
}
