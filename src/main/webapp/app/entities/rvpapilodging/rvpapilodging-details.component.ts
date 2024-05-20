import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import RvpapilodgingService from './rvpapilodging.service';
import { type IRvpapilodging } from '@/shared/model/rvpapilodging.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpapilodgingDetails',
  setup() {
    const rvpapilodgingService = inject('rvpapilodgingService', () => new RvpapilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const rvpapilodging: Ref<IRvpapilodging> = ref({});

    const retrieveRvpapilodging = async rvpapilodgingId => {
      try {
        const res = await rvpapilodgingService().find(rvpapilodgingId);
        rvpapilodging.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpapilodgingId) {
      retrieveRvpapilodging(route.params.rvpapilodgingId);
    }

    return {
      alertService,
      rvpapilodging,

      previousState,
      t$: useI18n().t,
    };
  },
});
