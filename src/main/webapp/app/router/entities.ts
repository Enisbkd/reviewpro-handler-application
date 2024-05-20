import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const RvpApilodging = () => import('@/entities/rvp-apilodging/rvp-apilodging.vue');
const RvpApilodgingUpdate = () => import('@/entities/rvp-apilodging/rvp-apilodging-update.vue');
const RvpApilodgingDetails = () => import('@/entities/rvp-apilodging/rvp-apilodging-details.vue');

const RvpApiResponse = () => import('@/entities/rvp-api-response/rvp-api-response.vue');
const RvpApiResponseUpdate = () => import('@/entities/rvp-api-response/rvp-api-response-update.vue');
const RvpApiResponseDetails = () => import('@/entities/rvp-api-response/rvp-api-response-details.vue');

const RvpApiLodgingCqi = () => import('@/entities/rvp-api-lodging-cqi/rvp-api-lodging-cqi.vue');
const RvpApiLodgingCqiUpdate = () => import('@/entities/rvp-api-lodging-cqi/rvp-api-lodging-cqi-update.vue');
const RvpApiLodgingCqiDetails = () => import('@/entities/rvp-api-lodging-cqi/rvp-api-lodging-cqi-details.vue');

const RvpApiGlobalReview = () => import('@/entities/rvp-api-global-review/rvp-api-global-review.vue');
const RvpApiGlobalReviewUpdate = () => import('@/entities/rvp-api-global-review/rvp-api-global-review-update.vue');
const RvpApiGlobalReviewDetails = () => import('@/entities/rvp-api-global-review/rvp-api-global-review-details.vue');

const RvpApiLodgingScore = () => import('@/entities/rvp-api-lodging-score/rvp-api-lodging-score.vue');
const RvpApiLodgingScoreUpdate = () => import('@/entities/rvp-api-lodging-score/rvp-api-lodging-score-update.vue');
const RvpApiLodgingScoreDetails = () => import('@/entities/rvp-api-lodging-score/rvp-api-lodging-score-details.vue');

const RvpApiSurvey = () => import('@/entities/rvp-api-survey/rvp-api-survey.vue');
const RvpApiSurveyUpdate = () => import('@/entities/rvp-api-survey/rvp-api-survey-update.vue');
const RvpApiSurveyDetails = () => import('@/entities/rvp-api-survey/rvp-api-survey-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'rvp-apilodging',
      name: 'RvpApilodging',
      component: RvpApilodging,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-apilodging/new',
      name: 'RvpApilodgingCreate',
      component: RvpApilodgingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-apilodging/:rvpApilodgingId/edit',
      name: 'RvpApilodgingEdit',
      component: RvpApilodgingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-apilodging/:rvpApilodgingId/view',
      name: 'RvpApilodgingView',
      component: RvpApilodgingDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-response',
      name: 'RvpApiResponse',
      component: RvpApiResponse,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-response/new',
      name: 'RvpApiResponseCreate',
      component: RvpApiResponseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-response/:rvpApiResponseId/edit',
      name: 'RvpApiResponseEdit',
      component: RvpApiResponseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-response/:rvpApiResponseId/view',
      name: 'RvpApiResponseView',
      component: RvpApiResponseDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-cqi',
      name: 'RvpApiLodgingCqi',
      component: RvpApiLodgingCqi,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-cqi/new',
      name: 'RvpApiLodgingCqiCreate',
      component: RvpApiLodgingCqiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-cqi/:rvpApiLodgingCqiId/edit',
      name: 'RvpApiLodgingCqiEdit',
      component: RvpApiLodgingCqiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-cqi/:rvpApiLodgingCqiId/view',
      name: 'RvpApiLodgingCqiView',
      component: RvpApiLodgingCqiDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-global-review',
      name: 'RvpApiGlobalReview',
      component: RvpApiGlobalReview,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-global-review/new',
      name: 'RvpApiGlobalReviewCreate',
      component: RvpApiGlobalReviewUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-global-review/:rvpApiGlobalReviewId/edit',
      name: 'RvpApiGlobalReviewEdit',
      component: RvpApiGlobalReviewUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-global-review/:rvpApiGlobalReviewId/view',
      name: 'RvpApiGlobalReviewView',
      component: RvpApiGlobalReviewDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-score',
      name: 'RvpApiLodgingScore',
      component: RvpApiLodgingScore,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-score/new',
      name: 'RvpApiLodgingScoreCreate',
      component: RvpApiLodgingScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-score/:rvpApiLodgingScoreId/edit',
      name: 'RvpApiLodgingScoreEdit',
      component: RvpApiLodgingScoreUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-lodging-score/:rvpApiLodgingScoreId/view',
      name: 'RvpApiLodgingScoreView',
      component: RvpApiLodgingScoreDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-survey',
      name: 'RvpApiSurvey',
      component: RvpApiSurvey,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-survey/new',
      name: 'RvpApiSurveyCreate',
      component: RvpApiSurveyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-survey/:rvpApiSurveyId/edit',
      name: 'RvpApiSurveyEdit',
      component: RvpApiSurveyUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rvp-api-survey/:rvpApiSurveyId/view',
      name: 'RvpApiSurveyView',
      component: RvpApiSurveyDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
