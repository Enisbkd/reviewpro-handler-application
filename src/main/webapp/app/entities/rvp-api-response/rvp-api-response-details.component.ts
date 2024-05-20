import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApiResponseService from './rvp-api-response.service';
import { useDateFormat } from '@/shared/composables';
import { type IRvpApiResponse } from '@/shared/model/rvp-api-response.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiResponseDetails',
  setup() {
    const dateFormat = useDateFormat();
    const rvpApiResponseService = inject('rvpApiResponseService', () => new RvpApiResponseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApiResponse: Ref<IRvpApiResponse> = ref({});

    const retrieveRvpApiResponse = async rvpApiResponseId => {
      try {
        const res = await rvpApiResponseService().find(rvpApiResponseId);
        rvpApiResponse.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiResponseId) {
      retrieveRvpApiResponse(route.params.rvpApiResponseId);
    }

    return {
      ...dateFormat,
      alertService,
      rvpApiResponse,

      previousState,
      t$: useI18n().t,
    };
  },
});
