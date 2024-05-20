/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RvpApiGlobalReview from './rvp-api-global-review.vue';
import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiGlobalReviewComponentType = InstanceType<typeof RvpApiGlobalReview>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RvpApiGlobalReview Management Component', () => {
    let rvpApiGlobalReviewServiceStub: SinonStubbedInstance<RvpApiGlobalReviewService>;
    let mountOptions: MountingOptions<RvpApiGlobalReviewComponentType>['global'];

    beforeEach(() => {
      rvpApiGlobalReviewServiceStub = sinon.createStubInstance<RvpApiGlobalReviewService>(RvpApiGlobalReviewService);
      rvpApiGlobalReviewServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          rvpApiGlobalReviewService: () => rvpApiGlobalReviewServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiGlobalReviewServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RvpApiGlobalReview, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rvpApiGlobalReviewServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rvpApiGlobalReviews[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RvpApiGlobalReviewComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RvpApiGlobalReview, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rvpApiGlobalReviewServiceStub.retrieve.reset();
        rvpApiGlobalReviewServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rvpApiGlobalReviewServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRvpApiGlobalReview();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rvpApiGlobalReviewServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rvpApiGlobalReviewServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
