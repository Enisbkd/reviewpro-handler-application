import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpapilodgingService from './rvpapilodging.service';
import { type IRvpapilodging } from '@/shared/model/rvpapilodging.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Rvpapilodging',
  setup() {
    const { t: t$ } = useI18n();
    const rvpapilodgingService = inject('rvpapilodgingService', () => new RvpapilodgingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpapilodgings: Ref<IRvpapilodging[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpapilodgings = async () => {
      isFetching.value = true;
      try {
        const res = await rvpapilodgingService().retrieve();
        rvpapilodgings.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpapilodgings();
    };

    onMounted(async () => {
      await retrieveRvpapilodgings();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpapilodging) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpapilodging = async () => {
      try {
        await rvpapilodgingService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpapilodging.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpapilodgings();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpapilodgings,
      handleSyncList,
      isFetching,
      retrieveRvpapilodgings,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpapilodging,
      t$,
    };
  },
});
