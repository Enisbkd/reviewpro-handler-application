/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpapilodgingDetails from './rvpapilodging-details.vue';
import RvpapilodgingService from './rvpapilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpapilodgingDetailsComponentType = InstanceType<typeof RvpapilodgingDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpapilodgingSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Rvpapilodging Management Detail Component', () => {
    let rvpapilodgingServiceStub: SinonStubbedInstance<RvpapilodgingService>;
    let mountOptions: MountingOptions<RvpapilodgingDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpapilodgingServiceStub = sinon.createStubInstance<RvpapilodgingService>(RvpapilodgingService);

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
          rvpapilodgingService: () => rvpapilodgingServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpapilodgingServiceStub.find.resolves(rvpapilodgingSample);
        route = {
          params: {
            rvpapilodgingId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(RvpapilodgingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpapilodging).toMatchObject(rvpapilodgingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpapilodgingServiceStub.find.resolves(rvpapilodgingSample);
        const wrapper = shallowMount(RvpapilodgingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
