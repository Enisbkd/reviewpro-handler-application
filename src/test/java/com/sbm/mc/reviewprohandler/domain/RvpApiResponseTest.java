package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpApiResponseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpApiResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvpApiResponse.class);
        RvpApiResponse rvpApiResponse1 = getRvpApiResponseSample1();
        RvpApiResponse rvpApiResponse2 = new RvpApiResponse();
        assertThat(rvpApiResponse1).isNotEqualTo(rvpApiResponse2);

        rvpApiResponse2.setId(rvpApiResponse1.getId());
        assertThat(rvpApiResponse1).isEqualTo(rvpApiResponse2);

        rvpApiResponse2 = getRvpApiResponseSample2();
        assertThat(rvpApiResponse1).isNotEqualTo(rvpApiResponse2);
    }
}
