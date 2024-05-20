import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import { type IRvpApiLodgingScore } from '@/shared/model/rvp-api-lodging-score.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingScore',
  setup() {
    const { t: t$ } = useI18n();
    const rvpApiLodgingScoreService = inject('rvpApiLodgingScoreService', () => new RvpApiLodgingScoreService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiLodgingScores: Ref<IRvpApiLodgingScore[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApiLodgingScores = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApiLodgingScoreService().retrieve();
        rvpApiLodgingScores.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApiLodgingScores();
    };

    onMounted(async () => {
      await retrieveRvpApiLodgingScores();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApiLodgingScore) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApiLodgingScore = async () => {
      try {
        await rvpApiLodgingScoreService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApiLodgingScore.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApiLodgingScores();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApiLodgingScores,
      handleSyncList,
      isFetching,
      retrieveRvpApiLodgingScores,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApiLodgingScore,
      t$,
    };
  },
});
