import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApiLodgingCqiService from './rvp-api-lodging-cqi.service';
import { type IRvpApiLodgingCqi } from '@/shared/model/rvp-api-lodging-cqi.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingCqiDetails',
  setup() {
    const rvpApiLodgingCqiService = inject('rvpApiLodgingCqiService', () => new RvpApiLodgingCqiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApiLodgingCqi: Ref<IRvpApiLodgingCqi> = ref({});

    const retrieveRvpApiLodgingCqi = async rvpApiLodgingCqiId => {
      try {
        const res = await rvpApiLodgingCqiService().find(rvpApiLodgingCqiId);
        rvpApiLodgingCqi.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiLodgingCqiId) {
      retrieveRvpApiLodgingCqi(route.params.rvpApiLodgingCqiId);
    }

    return {
      alertService,
      rvpApiLodgingCqi,

      previousState,
      t$: useI18n().t,
    };
  },
});
