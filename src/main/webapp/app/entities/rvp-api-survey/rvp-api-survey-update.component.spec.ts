/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiSurveyUpdate from './rvp-api-survey-update.vue';
import RvpApiSurveyService from './rvp-api-survey.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiSurveyUpdateComponentType = InstanceType<typeof RvpApiSurveyUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiSurveySample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpApiSurveyUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RvpApiSurvey Management Update Component', () => {
    let comp: RvpApiSurveyUpdateComponentType;
    let rvpApiSurveyServiceStub: SinonStubbedInstance<RvpApiSurveyService>;

    beforeEach(() => {
      route = {};
      rvpApiSurveyServiceStub = sinon.createStubInstance<RvpApiSurveyService>(RvpApiSurveyService);
      rvpApiSurveyServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rvpApiSurveyService: () => rvpApiSurveyServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpApiSurveyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiSurvey = rvpApiSurveySample;
        rvpApiSurveyServiceStub.update.resolves(rvpApiSurveySample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiSurveyServiceStub.update.calledWith(rvpApiSurveySample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpApiSurveyServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpApiSurveyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiSurvey = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiSurveyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpApiSurveyServiceStub.find.resolves(rvpApiSurveySample);
        rvpApiSurveyServiceStub.retrieve.resolves([rvpApiSurveySample]);

        // WHEN
        route = {
          params: {
            rvpApiSurveyId: '' + rvpApiSurveySample.id,
          },
        };
        const wrapper = shallowMount(RvpApiSurveyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiSurvey).toMatchObject(rvpApiSurveySample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiSurveyServiceStub.find.resolves(rvpApiSurveySample);
        const wrapper = shallowMount(RvpApiSurveyUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
