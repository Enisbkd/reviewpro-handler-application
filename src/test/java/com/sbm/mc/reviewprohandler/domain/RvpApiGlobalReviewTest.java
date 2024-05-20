package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpApiGlobalReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpApiGlobalReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvpApiGlobalReview.class);
        RvpApiGlobalReview rvpApiGlobalReview1 = getRvpApiGlobalReviewSample1();
        RvpApiGlobalReview rvpApiGlobalReview2 = new RvpApiGlobalReview();
        assertThat(rvpApiGlobalReview1).isNotEqualTo(rvpApiGlobalReview2);

        rvpApiGlobalReview2.setId(rvpApiGlobalReview1.getId());
        assertThat(rvpApiGlobalReview1).isEqualTo(rvpApiGlobalReview2);

        rvpApiGlobalReview2 = getRvpApiGlobalReviewSample2();
        assertThat(rvpApiGlobalReview1).isNotEqualTo(rvpApiGlobalReview2);
    }
}
