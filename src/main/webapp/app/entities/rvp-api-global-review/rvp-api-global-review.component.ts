import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import { type IRvpApiGlobalReview } from '@/shared/model/rvp-api-global-review.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RvpApiGlobalReview',
  setup() {
    const { t: t$ } = useI18n();
    const rvpApiGlobalReviewService = inject('rvpApiGlobalReviewService', () => new RvpApiGlobalReviewService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const rvpApiGlobalReviews: Ref<IRvpApiGlobalReview[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRvpApiGlobalReviews = async () => {
      isFetching.value = true;
      try {
        const res = await rvpApiGlobalReviewService().retrieve();
        rvpApiGlobalReviews.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRvpApiGlobalReviews();
    };

    onMounted(async () => {
      await retrieveRvpApiGlobalReviews();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRvpApiGlobalReview) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRvpApiGlobalReview = async () => {
      try {
        await rvpApiGlobalReviewService().delete(removeId.value);
        const message = t$('reviewproHandlerApplicationApp.rvpApiGlobalReview.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRvpApiGlobalReviews();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      rvpApiGlobalReviews,
      handleSyncList,
      isFetching,
      retrieveRvpApiGlobalReviews,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRvpApiGlobalReview,
      t$,
    };
  },
});
