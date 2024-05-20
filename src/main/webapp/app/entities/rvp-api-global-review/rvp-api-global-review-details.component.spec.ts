/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiGlobalReviewDetails from './rvp-api-global-review-details.vue';
import RvpApiGlobalReviewService from './rvp-api-global-review.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiGlobalReviewDetailsComponentType = InstanceType<typeof RvpApiGlobalReviewDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiGlobalReviewSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApiGlobalReview Management Detail Component', () => {
    let rvpApiGlobalReviewServiceStub: SinonStubbedInstance<RvpApiGlobalReviewService>;
    let mountOptions: MountingOptions<RvpApiGlobalReviewDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApiGlobalReviewServiceStub = sinon.createStubInstance<RvpApiGlobalReviewService>(RvpApiGlobalReviewService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          rvpApiGlobalReviewService: () => rvpApiGlobalReviewServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiGlobalReviewServiceStub.find.resolves(rvpApiGlobalReviewSample);
        route = {
          params: {
            rvpApiGlobalReviewId: '' + 123,
          },
        };
        const wrapper = shallowMount(RvpApiGlobalReviewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiGlobalReview).toMatchObject(rvpApiGlobalReviewSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiGlobalReviewServiceStub.find.resolves(rvpApiGlobalReviewSample);
        const wrapper = shallowMount(RvpApiGlobalReviewDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
