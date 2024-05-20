import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import { type IRvpApiLodgingScore } from '@/shared/model/rvp-api-lodging-score.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingScoreDetails',
  setup() {
    const rvpApiLodgingScoreService = inject('rvpApiLodgingScoreService', () => new RvpApiLodgingScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApiLodgingScore: Ref<IRvpApiLodgingScore> = ref({});

    const retrieveRvpApiLodgingScore = async rvpApiLodgingScoreId => {
      try {
        const res = await rvpApiLodgingScoreService().find(rvpApiLodgingScoreId);
        rvpApiLodgingScore.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiLodgingScoreId) {
      retrieveRvpApiLodgingScore(route.params.rvpApiLodgingScoreId);
    }

    return {
      alertService,
      rvpApiLodgingScore,

      previousState,
      t$: useI18n().t,
    };
  },
});
