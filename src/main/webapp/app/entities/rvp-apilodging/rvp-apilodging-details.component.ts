import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpApilodgingService from './rvp-apilodging.service';
import { type IRvpApilodging } from '@/shared/model/rvp-apilodging.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApilodgingDetails',
  setup() {
    const rvpApilodgingService = inject('rvpApilodgingService', () => new RvpApilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpApilodging: Ref<IRvpApilodging> = ref({});

    const retrieveRvpApilodging = async rvpApilodgingId => {
      try {
        const res = await rvpApilodgingService().find(rvpApilodgingId);
        rvpApilodging.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApilodgingId) {
      retrieveRvpApilodging(route.params.rvpApilodgingId);
    }

    return {
      alertService,
      rvpApilodging,

      previousState,
      t$: useI18n().t,
    };
  },
});
