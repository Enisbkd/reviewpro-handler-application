import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApilodgingService from './rvp-apilodging.service';
import { type IRvpApilodging } from '@/shared/model/rvp-apilodging.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApilodging',
  setup() {
    const { t: t$ } = useI18n();
    const rvpApilodgingService = inject('rvpApilodgingService', () => new RvpApilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApilodgings: Ref<IRvpApilodging[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApilodgings = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApilodgingService().retrieve();
        rvpApilodgings.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApilodgings();
    };

    onMounted(async () => {
      await retrieveRvpApilodgings();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApilodging) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApilodging = async () => {
      try {
        await rvpApilodgingService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApilodging.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApilodgings();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApilodgings,
      handleSyncList,
      isFetching,
      retrieveRvpApilodgings,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApilodging,
      t$,
    };
  },
});
