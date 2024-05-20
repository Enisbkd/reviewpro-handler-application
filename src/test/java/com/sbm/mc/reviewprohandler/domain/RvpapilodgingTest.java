package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpapilodgingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpapilodgingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rvpapilodging.class);
        Rvpapilodging rvpapilodging1 = getRvpapilodgingSample1();
        Rvpapilodging rvpapilodging2 = new Rvpapilodging();
        assertThat(rvpapilodging1).isNotEqualTo(rvpapilodging2);

        rvpapilodging2.setId(rvpapilodging1.getId());
        assertThat(rvpapilodging1).isEqualTo(rvpapilodging2);

        rvpapilodging2 = getRvpapilodgingSample2();
        assertThat(rvpapilodging1).isNotEqualTo(rvpapilodging2);
    }
}
