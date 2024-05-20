import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RvpApilodgingService from './rvp-apilodging.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IRvpApilodging, RvpApilodging } from '@/shared/model/rvp-apilodging.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApilodgingUpdate',
  setup() {
    const rvpApilodgingService = inject('rvpApilodgingService', () => new RvpApilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApilodging: Ref<IRvpApilodging> = ref(new RvpApilodging());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      ida: {},
      name: {},
    };
    const v$ = useVuelidate(validationRules, rvpApilodging as any);
    v$.value.$validate();

    return {
      rvpApilodgingService,
      alertService,
      rvpApilodging,
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
      if (this.rvpApilodging.id) {
        this.rvpApilodgingService()
          .update(this.rvpApilodging)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('reviewproHandlerApplicationApp.rvpApilodging.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.rvpApilodgingService()
          .create(this.rvpApilodging)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('reviewproHandlerApplicationApp.rvpApilodging.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
