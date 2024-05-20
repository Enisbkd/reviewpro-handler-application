package com.sbm.mc.reviewprohandler.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Rvpapilodging.
 */
@Document(collection = "rvpapilodging")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rvpapilodging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("ida")
    private Integer ida;

    @Field("name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Rvpapilodging id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIda() {
        return this.ida;
    }

    public Rvpapilodging ida(Integer ida) {
        this.setIda(ida);
        return this;
    }

    public void setIda(Integer ida) {
        this.ida = ida;
    }

    public String getName() {
        return this.name;
    }

    public Rvpapilodging name(String name) {
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
        if (!(o instanceof Rvpapilodging)) {
            return false;
        }
        return getId() != null && getId().equals(((Rvpapilodging) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rvpapilodging{" +
            "id=" + getId() +
            ", ida=" + getIda() +
            ", name='" + getName() + "'" +
            "}";
    }
}
