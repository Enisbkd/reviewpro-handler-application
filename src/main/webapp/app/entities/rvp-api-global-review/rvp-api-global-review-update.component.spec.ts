/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiGlobalReviewUpdate from './rvp-api-global-review-update.vue';
import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiGlobalReviewUpdateComponentType = InstanceType<typeof RvpApiGlobalReviewUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiGlobalReviewSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpApiGlobalReviewUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RvpApiGlobalReview Management Update Component', () => {
    let comp: RvpApiGlobalReviewUpdateComponentType;
    let rvpApiGlobalReviewServiceStub: SinonStubbedInstance<RvpApiGlobalReviewService>;

    beforeEach(() => {
      route = {};
      rvpApiGlobalReviewServiceStub = sinon.createStubInstance<RvpApiGlobalReviewService>(RvpApiGlobalReviewService);
      rvpApiGlobalReviewServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          rvpApiGlobalReviewService: () => rvpApiGlobalReviewServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpApiGlobalReviewUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiGlobalReview = rvpApiGlobalReviewSample;
        rvpApiGlobalReviewServiceStub.update.resolves(rvpApiGlobalReviewSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiGlobalReviewServiceStub.update.calledWith(rvpApiGlobalReviewSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpApiGlobalReviewServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpApiGlobalReviewUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiGlobalReview = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiGlobalReviewServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpApiGlobalReviewServiceStub.find.resolves(rvpApiGlobalReviewSample);
        rvpApiGlobalReviewServiceStub.retrieve.resolves([rvpApiGlobalReviewSample]);

        // WHEN
        route = {
          params: {
            rvpApiGlobalReviewId: '' + rvpApiGlobalReviewSample.id,
          },
        };
        const wrapper = shallowMount(RvpApiGlobalReviewUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiGlobalReview).toMatchObject(rvpApiGlobalReviewSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiGlobalReviewServiceStub.find.resolves(rvpApiGlobalReviewSample);
        const wrapper = shallowMount(RvpApiGlobalReviewUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
