import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApiLodgingCqiService from './rvp-api-lodging-cqi.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApiLodgingCqi, RvpApiLodgingCqi } from '@/shared/model/rvp-api-lodging-cqi.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingCqiUpdate',
  setup() {
    const rvpApiLodgingCqiService = inject('rvpApiLodgingCqiService', () => new RvpApiLodgingCqiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiLodgingCqi: Ref<IRvpApiLodgingCqi> = ref(new RvpApiLodgingCqi());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lodgingId: {},
      averageCurrentPeriod: {},
      tendancy: {},
      change: {},
      name: {},
      averagePreviousPeriod: {},
      fd: {},
      td: {},
    };
    const v$ = useVuelidate(validationRules, rvpApiLodgingCqi as any);
    v$.value.$validate();

    return {
      rvpApiLodgingCqiService,
      alertService,
      rvpApiLodgingCqi,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rvpApiLodgingCqi.id) {
        this.rvpApiLodgingCqiService()
          .update(this.rvpApiLodgingCqi)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApiLodgingCqiService()
          .create(this.rvpApiLodgingCqi)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.created', { param: param.id }).toString(),
            );
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
