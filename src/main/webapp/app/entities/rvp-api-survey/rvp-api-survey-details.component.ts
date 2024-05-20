import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApiSurveyService from './rvp-api-survey.service';
import { type IRvpApiSurvey } from '@/shared/model/rvp-api-survey.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiSurveyDetails',
  setup() {
    const rvpApiSurveyService = inject('rvpApiSurveyService', () => new RvpApiSurveyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApiSurvey: Ref<IRvpApiSurvey> = ref({});

    const retrieveRvpApiSurvey = async rvpApiSurveyId => {
      try {
        const res = await rvpApiSurveyService().find(rvpApiSurveyId);
        rvpApiSurvey.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiSurveyId) {
      retrieveRvpApiSurvey(route.params.rvpApiSurveyId);
    }

    return {
      alertService,
      rvpApiSurvey,

      previousState,
      t$: useI18n().t,
    };
  },
});
