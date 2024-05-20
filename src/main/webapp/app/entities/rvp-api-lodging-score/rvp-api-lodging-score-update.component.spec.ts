/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApiLodgingScoreUpdate from './rvp-api-lodging-score-update.vue';
import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiLodgingScoreUpdateComponentType = InstanceType<typeof RvpApiLodgingScoreUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiLodgingScoreSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpApiLodgingScoreUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RvpApiLodgingScore Management Update Component', () => {
    let comp: RvpApiLodgingScoreUpdateComponentType;
    let rvpApiLodgingScoreServiceStub: SinonStubbedInstance<RvpApiLodgingScoreService>;

    beforeEach(() => {
      route = {};
      rvpApiLodgingScoreServiceStub = sinon.createStubInstance<RvpApiLodgingScoreService>(RvpApiLodgingScoreService);
      rvpApiLodgingScoreServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rvpApiLodgingScoreService: () => rvpApiLodgingScoreServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpApiLodgingScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiLodgingScore = rvpApiLodgingScoreSample;
        rvpApiLodgingScoreServiceStub.update.resolves(rvpApiLodgingScoreSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiLodgingScoreServiceStub.update.calledWith(rvpApiLodgingScoreSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpApiLodgingScoreServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpApiLodgingScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiLodgingScore = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiLodgingScoreServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpApiLodgingScoreServiceStub.find.resolves(rvpApiLodgingScoreSample);
        rvpApiLodgingScoreServiceStub.retrieve.resolves([rvpApiLodgingScoreSample]);

        // WHEN
        route = {
          params: {
            rvpApiLodgingScoreId: '' + rvpApiLodgingScoreSample.id,
          },
        };
        const wrapper = shallowMount(RvpApiLodgingScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiLodgingScore).toMatchObject(rvpApiLodgingScoreSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiLodgingScoreServiceStub.find.resolves(rvpApiLodgingScoreSample);
        const wrapper = shallowMount(RvpApiLodgingScoreUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
