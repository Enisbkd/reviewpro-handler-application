import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApiGlobalReview, RvpApiGlobalReview } from '@/shared/model/rvp-api-global-review.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiGlobalReviewUpdate',
  setup() {
    const rvpApiGlobalReviewService = inject('rvpApiGlobalReviewService', () => new RvpApiGlobalReviewService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiGlobalReview: Ref<IRvpApiGlobalReview> = ref(new RvpApiGlobalReview());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      lodgingid: {},
      prevGri: {},
      distribution: {},
      gri: {},
      fd: {},
      td: {},
    };
    const v$ = useVuelidate(validationRules, rvpApiGlobalReview as any);
    v$.value.$validate();

    return {
      rvpApiGlobalReviewService,
      alertService,
      rvpApiGlobalReview,
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
      if (this.rvpApiGlobalReview.id) {
        this.rvpApiGlobalReviewService()
          .update(this.rvpApiGlobalReview)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApiGlobalReviewService()
          .create(this.rvpApiGlobalReview)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(
              this.t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.created', { param: param.id }).toString(),
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
