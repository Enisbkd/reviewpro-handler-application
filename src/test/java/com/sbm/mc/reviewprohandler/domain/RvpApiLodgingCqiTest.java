package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpApiLodgingCqiTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpApiLodgingCqiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvpApiLodgingCqi.class);
        RvpApiLodgingCqi rvpApiLodgingCqi1 = getRvpApiLodgingCqiSample1();
        RvpApiLodgingCqi rvpApiLodgingCqi2 = new RvpApiLodgingCqi();
        assertThat(rvpApiLodgingCqi1).isNotEqualTo(rvpApiLodgingCqi2);

        rvpApiLodgingCqi2.setId(rvpApiLodgingCqi1.getId());
        assertThat(rvpApiLodgingCqi1).isEqualTo(rvpApiLodgingCqi2);

        rvpApiLodgingCqi2 = getRvpApiLodgingCqiSample2();
        assertThat(rvpApiLodgingCqi1).isNotEqualTo(rvpApiLodgingCqi2);
    }
}
