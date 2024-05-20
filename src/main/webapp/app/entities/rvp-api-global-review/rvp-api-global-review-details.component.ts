import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import { type IRvpApiGlobalReview } from '@/shared/model/rvp-api-global-review.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiGlobalReviewDetails',
  setup() {
    const rvpApiGlobalReviewService = inject('rvpApiGlobalReviewService', () => new RvpApiGlobalReviewService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApiGlobalReview: Ref<IRvpApiGlobalReview> = ref({});

    const retrieveRvpApiGlobalReview = async rvpApiGlobalReviewId => {
      try {
        const res = await rvpApiGlobalReviewService().find(rvpApiGlobalReviewId);
        rvpApiGlobalReview.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiGlobalReviewId) {
      retrieveRvpApiGlobalReview(route.params.rvpApiGlobalReviewId);
    }

    return {
      alertService,
      rvpApiGlobalReview,

      previousState,
      t$: useI18n().t,
    };
  },
});
