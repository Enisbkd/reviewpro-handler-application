import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApiSurveyService from './rvp-api-survey.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApiSurvey, RvpApiSurvey } from '@/shared/model/rvp-api-survey.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiSurveyUpdate',
  setup() {
    const rvpApiSurveyService = inject('rvpApiSurveyService', () => new RvpApiSurveyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiSurvey: Ref<IRvpApiSurvey> = ref(new RvpApiSurvey());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      overallScoreEnabled: {},
      languages: {},
      outOf: {},
      name: {},
      actibe: {},
      pids: {},
      primary: {},
    };
    const v$ = useVuelidate(validationRules, rvpApiSurvey as any);
    v$.value.$validate();

    return {
      rvpApiSurveyService,
      alertService,
      rvpApiSurvey,
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
      if (this.rvpApiSurvey.id) {
        this.rvpApiSurveyService()
          .update(this.rvpApiSurvey)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApiSurvey.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApiSurveyService()
          .create(this.rvpApiSurvey)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('reviewproHandlerApplicationApp.rvpApiSurvey.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
