import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApiResponseService from './rvp-api-response.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApiResponse, RvpApiResponse } from '@/shared/model/rvp-api-response.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiResponseUpdate',
  setup() {
    const rvpApiResponseService = inject('rvpApiResponseService', () => new RvpApiResponseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiResponse: Ref<IRvpApiResponse> = ref(new RvpApiResponse());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRvpApiResponse = async rvpApiResponseId => {
      try {
        const res = await rvpApiResponseService().find(rvpApiResponseId);
        res.date = new Date(res.date);
        rvpApiResponse.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.rvpApiResponseId) {
      retrieveRvpApiResponse(route.params.rvpApiResponseId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      surveyId: {},
      lodgingId: {},
      date: {},
      overallsatsifaction: {},
      customScore: {},
      plantorevisit: {},
    };
    const v$ = useVuelidate(validationRules, rvpApiResponse as any);
    v$.value.$validate();

    return {
      rvpApiResponseService,
      alertService,
      rvpApiResponse,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: rvpApiResponse }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.rvpApiResponse.id) {
        this.rvpApiResponseService()
          .update(this.rvpApiResponse)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApiResponse.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApiResponseService()
          .create(this.rvpApiResponse)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('reviewproHandlerApplicationApp.rvpApiResponse.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
