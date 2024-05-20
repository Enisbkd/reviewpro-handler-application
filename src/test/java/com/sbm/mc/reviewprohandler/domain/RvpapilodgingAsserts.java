package com.sbm.mc.reviewprohandler.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RvpapilodgingAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRvpapilodgingAllPropertiesEquals(Rvpapilodging expected, Rvpapilodging actual) {
        assertRvpapilodgingAutoGeneratedPropertiesEquals(expected, actual);
        assertRvpapilodgingAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRvpapilodgingAllUpdatablePropertiesEquals(Rvpapilodging expected, Rvpapilodging actual) {
        assertRvpapilodgingUpdatableFieldsEquals(expected, actual);
        assertRvpapilodgingUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRvpapilodgingAutoGeneratedPropertiesEquals(Rvpapilodging expected, Rvpapilodging actual) {
        assertThat(expected)
            .as("Verify Rvpapilodging auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRvpapilodgingUpdatableFieldsEquals(Rvpapilodging expected, Rvpapilodging actual) {
        assertThat(expected)
            .as("Verify Rvpapilodging relevant properties")
            .satisfies(e -> assertThat(e.getIda()).as("check ida").isEqualTo(actual.getIda()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRvpapilodgingUpdatableRelationshipsEquals(Rvpapilodging expected, Rvpapilodging actual) {}
}
