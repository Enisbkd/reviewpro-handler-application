package com.sbm.mc.reviewprohandler.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Document(collection = "rvp_api_lodging_cqi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApiLodgingCqi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Field("lodging_id")
    private Integer lodgingId;

    @Field("average_current_period")
    private String averageCurrentPeriod;

    @Field("tendancy")
    private String tendancy;

    @Field("change")
    private String change;

    @Field("name")
    private String name;

    @Field("average_previous_period")
    private String averagePreviousPeriod;

    @Field("fd")
    private LocalDate fd;

    @Field("td")
    private LocalDate td;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public RvpApiLodgingCqi id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLodgingId() {
        return this.lodgingId;
    }

    public RvpApiLodgingCqi lodgingId(Integer lodgingId) {
        this.setLodgingId(lodgingId);
        return this;
    }

    public void setLodgingId(Integer lodgingId) {
        this.lodgingId = lodgingId;
    }

    public String getAverageCurrentPeriod() {
        return this.averageCurrentPeriod;
    }

    public RvpApiLodgingCqi averageCurrentPeriod(String averageCurrentPeriod) {
        this.setAverageCurrentPeriod(averageCurrentPeriod);
        return this;
    }

    public void setAverageCurrentPeriod(String averageCurrentPeriod) {
        this.averageCurrentPeriod = averageCurrentPeriod;
    }

    public String getTendancy() {
        return this.tendancy;
    }

    public RvpApiLodgingCqi tendancy(String tendancy) {
        this.setTendancy(tendancy);
        return this;
    }

    public void setTendancy(String tendancy) {
        this.tendancy = tendancy;
    }

    public String getChange() {
        return this.change;
    }

    public RvpApiLodgingCqi change(String change) {
        this.setChange(change);
        return this;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getName() {
        return this.name;
    }

    public RvpApiLodgingCqi name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAveragePreviousPeriod() {
        return this.averagePreviousPeriod;
    }

    public RvpApiLodgingCqi averagePreviousPeriod(String averagePreviousPeriod) {
        this.setAveragePreviousPeriod(averagePreviousPeriod);
        return this;
    }

    public void setAveragePreviousPeriod(String averagePreviousPeriod) {
        this.averagePreviousPeriod = averagePreviousPeriod;
    }

    public LocalDate getFd() {
        return this.fd;
    }

    public RvpApiLodgingCqi fd(LocalDate fd) {
        this.setFd(fd);
        return this;
    }

    public void setFd(LocalDate fd) {
        this.fd = fd;
    }

    public LocalDate getTd() {
        return this.td;
    }

    public RvpApiLodgingCqi td(LocalDate td) {
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
        if (!(o instanceof RvpApiLodgingCqi)) {
            return false;
        }
        return getId() != null && getId().equals(((RvpApiLodgingCqi) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvpApiLodgingCqi{" +
            "id=" + getId() +
            ", lodgingId=" + getLodgingId() +
            ", averageCurrentPeriod='" + getAverageCurrentPeriod() + "'" +
            ", tendancy='" + getTendancy() + "'" +
            ", change='" + getChange() + "'" +
            ", name='" + getName() + "'" +
            ", averagePreviousPeriod='" + getAveragePreviousPeriod() + "'" +
            ", fd='" + getFd() + "'" +
            ", td='" + getTd() + "'" +
            "}";
    }
}
