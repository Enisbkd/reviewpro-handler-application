/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiLodgingCqiDetails from './rvp-api-lodging-cqi-details.vue';
import RvpApiLodgingCqiService from './rvp-api-lodging-cqi.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiLodgingCqiDetailsComponentType = InstanceType<typeof RvpApiLodgingCqiDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiLodgingCqiSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApiLodgingCqi Management Detail Component', () => {
    let rvpApiLodgingCqiServiceStub: SinonStubbedInstance<RvpApiLodgingCqiService>;
    let mountOptions: MountingOptions<RvpApiLodgingCqiDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApiLodgingCqiServiceStub = sinon.createStubInstance<RvpApiLodgingCqiService>(RvpApiLodgingCqiService);

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
          rvpApiLodgingCqiService: () => rvpApiLodgingCqiServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiLodgingCqiServiceStub.find.resolves(rvpApiLodgingCqiSample);
        route = {
          params: {
            rvpApiLodgingCqiId: '' + 123,
          },
        };
        const wrapper = shallowMount(RvpApiLodgingCqiDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiLodgingCqi).toMatchObject(rvpApiLodgingCqiSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiLodgingCqiServiceStub.find.resolves(rvpApiLodgingCqiSample);
        const wrapper = shallowMount(RvpApiLodgingCqiDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
