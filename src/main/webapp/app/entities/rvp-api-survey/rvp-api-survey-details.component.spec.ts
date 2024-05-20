/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiSurveyDetails from './rvp-api-survey-details.vue';
import RvpApiSurveyService from './rvp-api-survey.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiSurveyDetailsComponentType = InstanceType<typeof RvpApiSurveyDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiSurveySample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RvpApiSurvey Management Detail Component', () => {
    let rvpApiSurveyServiceStub: SinonStubbedInstance<RvpApiSurveyService>;
    let mountOptions: MountingOptions<RvpApiSurveyDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rvpApiSurveyServiceStub = sinon.createStubInstance<RvpApiSurveyService>(RvpApiSurveyService);

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
          rvpApiSurveyService: () => rvpApiSurveyServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiSurveyServiceStub.find.resolves(rvpApiSurveySample);
        route = {
          params: {
            rvpApiSurveyId: '' + 123,
          },
        };
        const wrapper = shallowMount(RvpApiSurveyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiSurvey).toMatchObject(rvpApiSurveySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiSurveyServiceStub.find.resolves(rvpApiSurveySample);
        const wrapper = shallowMount(RvpApiSurveyDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
