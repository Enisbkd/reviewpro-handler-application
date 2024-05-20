import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApiLodgingScore, RvpApiLodgingScore } from '@/shared/model/rvp-api-lodging-score.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingScoreUpdate',
  setup() {
    const rvpApiLodgingScoreService = inject('rvpApiLodgingScoreService', () => new RvpApiLodgingScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiLodgingScore: Ref<IRvpApiLodgingScore> = ref(new RvpApiLodgingScore());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lodgingId: {},
      surveyId: {},
      nps: {},
      rating: {},
      customScore: {},
      fd: {},
      td: {},
    };
    const v$ = useVuelidate(validationRules, rvpApiLodgingScore as any);
    v$.value.$validate();

    return {
      rvpApiLodgingScoreService,
      alertService,
      rvpApiLodgingScore,
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
      if (this.rvpApiLodgingScore.id) {
        this.rvpApiLodgingScoreService()
          .update(this.rvpApiLodgingScore)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApiLodgingScoreService()
          .create(this.rvpApiLodgingScore)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.created', { param: param.id }).toString(),
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
