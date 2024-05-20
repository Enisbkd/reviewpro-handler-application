import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApiResponseService from './rvp-api-response.service';
import { type IRvpApiResponse } from '@/shared/model/rvp-api-response.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiResponse',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const rvpApiResponseService = inject('rvpApiResponseService', () => new RvpApiResponseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiResponses: Ref<IRvpApiResponse[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApiResponses = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApiResponseService().retrieve();
        rvpApiResponses.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApiResponses();
    };

    onMounted(async () => {
      await retrieveRvpApiResponses();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApiResponse) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApiResponse = async () => {
      try {
        await rvpApiResponseService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApiResponse.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApiResponses();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApiResponses,
      handleSyncList,
      isFetching,
      retrieveRvpApiResponses,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApiResponse,
      t$,
    };
  },
});
