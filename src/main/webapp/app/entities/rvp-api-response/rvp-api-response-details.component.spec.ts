/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiResponseDetails from './rvp-api-response-details.vue';
import RvpApiResponseService from './rvp-api-response.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiResponseDetailsComponentType = InstanceType<typeof RvpApiResponseDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiResponseSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApiResponse Management Detail Component', () => {
    let rvpApiResponseServiceStub: SinonStubbedInstance<RvpApiResponseService>;
    let mountOptions: MountingOptions<RvpApiResponseDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApiResponseServiceStub = sinon.createStubInstance<RvpApiResponseService>(RvpApiResponseService);

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
          rvpApiResponseService: () => rvpApiResponseServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiResponseServiceStub.find.resolves(rvpApiResponseSample);
        route = {
          params: {
            rvpApiResponseId: '' + 123,
          },
        };
        const wrapper = shallowMount(RvpApiResponseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiResponse).toMatchObject(rvpApiResponseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiResponseServiceStub.find.resolves(rvpApiResponseSample);
        const wrapper = shallowMount(RvpApiResponseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
