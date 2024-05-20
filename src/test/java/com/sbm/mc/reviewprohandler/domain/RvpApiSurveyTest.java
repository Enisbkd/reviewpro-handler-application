package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpApiSurveyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpApiSurveyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvpApiSurvey.class);
        RvpApiSurvey rvpApiSurvey1 = getRvpApiSurveySample1();
        RvpApiSurvey rvpApiSurvey2 = new RvpApiSurvey();
        assertThat(rvpApiSurvey1).isNotEqualTo(rvpApiSurvey2);

        rvpApiSurvey2.setId(rvpApiSurvey1.getId());
        assertThat(rvpApiSurvey1).isEqualTo(rvpApiSurvey2);

        rvpApiSurvey2 = getRvpApiSurveySample2();
        assertThat(rvpApiSurvey1).isNotEqualTo(rvpApiSurvey2);
    }
}
