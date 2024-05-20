import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpapilodgingService from './rvpapilodging.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpapilodging, Rvpapilodging } from '@/shared/model/rvpapilodging.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpapilodgingUpdate',
  setup() {
    const rvpapilodgingService = inject('rvpapilodgingService', () => new RvpapilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpapilodging: Ref<IRvpapilodging> = ref(new Rvpapilodging());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      ida: {},
      name: {},
    };
    const v$ = useVuelidate(validationRules, rvpapilodging as any);
    v$.value.$validate();

    return {
      rvpapilodgingService,
      alertService,
      rvpapilodging,
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
      if (this.rvpapilodging.id) {
        this.rvpapilodgingService()
          .update(this.rvpapilodging)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpapilodging.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpapilodgingService()
          .create(this.rvpapilodging)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('reviewproHandlerApplicationApp.rvpapilodging.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
