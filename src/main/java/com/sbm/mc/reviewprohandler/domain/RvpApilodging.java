package com.sbm.mc.reviewprohandler.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RvpApilodging.
 */
@Document(collection = "rvp_apilodging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RvpApilodging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonProperty("id")
    private String id;

    @Field("name")
    @JsonProperty("name")
    private String name;

    public String getId() {
        return this.id;
    }

    public RvpApilodging id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RvpApilodging name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvpApilodging)) {
            return false;
        }
        return getId() != null && getId().equals(((RvpApilodging) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvpApilodging{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
