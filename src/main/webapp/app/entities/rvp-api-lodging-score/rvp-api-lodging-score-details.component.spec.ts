/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiLodgingScoreDetails from './rvp-api-lodging-score-details.vue';
import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiLodgingScoreDetailsComponentType = InstanceType<typeof RvpApiLodgingScoreDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiLodgingScoreSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApiLodgingScore Management Detail Component', () => {
    let rvpApiLodgingScoreServiceStub: SinonStubbedInstance<RvpApiLodgingScoreService>;
    let mountOptions: MountingOptions<RvpApiLodgingScoreDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApiLodgingScoreServiceStub = sinon.createStubInstance<RvpApiLodgingScoreService>(RvpApiLodgingScoreService);

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
          rvpApiLodgingScoreService: () => rvpApiLodgingScoreServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiLodgingScoreServiceStub.find.resolves(rvpApiLodgingScoreSample);
        route = {
          params: {
            rvpApiLodgingScoreId: '' + 123,
          },
        };
        const wrapper = shallowMount(RvpApiLodgingScoreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiLodgingScore).toMatchObject(rvpApiLodgingScoreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiLodgingScoreServiceStub.find.resolves(rvpApiLodgingScoreSample);
        const wrapper = shallowMount(RvpApiLodgingScoreDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
