package com.sbm.mc.reviewprohandler.domain;

import static com.sbm.mc.reviewprohandler.domain.RvpApiLodgingScoreTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sbm.mc.reviewprohandler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RvpApiLodgingScoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvpApiLodgingScore.class);
        RvpApiLodgingScore rvpApiLodgingScore1 = getRvpApiLodgingScoreSample1();
        RvpApiLodgingScore rvpApiLodgingScore2 = new RvpApiLodgingScore();
        assertThat(rvpApiLodgingScore1).isNotEqualTo(rvpApiLodgingScore2);

        rvpApiLodgingScore2.setId(rvpApiLodgingScore1.getId());
        assertThat(rvpApiLodgingScore1).isEqualTo(rvpApiLodgingScore2);

        rvpApiLodgingScore2 = getRvpApiLodgingScoreSample2();
        assertThat(rvpApiLodgingScore1).isNotEqualTo(rvpApiLodgingScore2);
    }
}
