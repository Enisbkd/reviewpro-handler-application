import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApiLodgingCqiService from './rvp-api-lodging-cqi.service';
import { type IRvpApiLodgingCqi } from '@/shared/model/rvp-api-lodging-cqi.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiLodgingCqi',
  setup() {
    const { t: t$ } = useI18n();
    const rvpApiLodgingCqiService = inject('rvpApiLodgingCqiService', () => new RvpApiLodgingCqiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiLodgingCqis: Ref<IRvpApiLodgingCqi[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApiLodgingCqis = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApiLodgingCqiService().retrieve();
        rvpApiLodgingCqis.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApiLodgingCqis();
    };

    onMounted(async () => {
      await retrieveRvpApiLodgingCqis();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApiLodgingCqi) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApiLodgingCqi = async () => {
      try {
        await rvpApiLodgingCqiService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApiLodgingCqi.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApiLodgingCqis();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApiLodgingCqis,
      handleSyncList,
      isFetching,
      retrieveRvpApiLodgingCqis,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApiLodgingCqi,
      t$,
    };
  },
});
