import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApiSurveyService from './rvp-api-survey.service';
import { type IRvpApiSurvey } from '@/shared/model/rvp-api-survey.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiSurvey',
  setup() {
    const { t: t$ } = useI18n();
    const rvpApiSurveyService = inject('rvpApiSurveyService', () => new RvpApiSurveyService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiSurveys: Ref<IRvpApiSurvey[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApiSurveys = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApiSurveyService().retrieve();
        rvpApiSurveys.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApiSurveys();
    };

    onMounted(async () => {
      await retrieveRvpApiSurveys();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApiSurvey) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApiSurvey = async () => {
      try {
        await rvpApiSurveyService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApiSurvey.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApiSurveys();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApiSurveys,
      handleSyncList,
      isFetching,
      retrieveRvpApiSurveys,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApiSurvey,
      t$,
    };
  },
});
