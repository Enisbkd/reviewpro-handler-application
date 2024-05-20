import { defineComponent, provide } from 'vue';

import RvpApilodgingService from './rvp-apilodging/rvp-apilodging.service';
import RvpApiResponseService from './rvp-api-response/rvp-api-response.service';
import RvpApiLodgingCqiService from './rvp-api-lodging-cqi/rvp-api-lodging-cqi.service';
import RvpApiGlobalReviewService from './rvp-api-global-review/rvp-api-global-review.service';
import RvpApiLodgingScoreService from './rvp-api-lodging-score/rvp-api-lodging-score.service';
import RvpApiSurveyService from './rvp-api-survey/rvp-api-survey.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('rvpApilodgingService', () => new RvpApilodgingService());
    provide('rvpApiResponseService', () => new RvpApiResponseService());
    provide('rvpApiLodgingCqiService', () => new RvpApiLodgingCqiService());
    provide('rvpApiGlobalReviewService', () => new RvpApiGlobalReviewService());
    provide('rvpApiLodgingScoreService', () => new RvpApiLodgingScoreService());
    provide('rvpApiSurveyService', () => new RvpApiSurveyService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
