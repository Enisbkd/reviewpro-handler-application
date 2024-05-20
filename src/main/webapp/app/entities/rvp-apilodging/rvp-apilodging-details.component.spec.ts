/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApilodgingDetails from './rvp-apilodging-details.vue';
import RvpApilodgingService from './rvp-apilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApilodgingDetailsComponentType = InstanceType<typeof RvpApilodgingDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApilodgingSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApilodging Management Detail Component', () => {
    let rvpApilodgingServiceStub: SinonStubbedInstance<RvpApilodgingService>;
    let mountOptions: MountingOptions<RvpApilodgingDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApilodgingServiceStub = sinon.createStubInstance<RvpApilodgingService>(RvpApilodgingService);

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
          rvpApilodgingService: () => rvpApilodgingServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApilodgingServiceStub.find.resolves(rvpApilodgingSample);
        route = {
          params: {
            rvpApilodgingId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RvpApilodgingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApilodging).toMatchObject(rvpApilodgingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApilodgingServiceStub.find.resolves(rvpApilodgingSample);
        const wrapper = shallowMount(RvpApilodgingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
