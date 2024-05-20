/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpapilodgingUpdate from './rvpapilodging-update.vue';
import RvpapilodgingService from './rvpapilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpapilodgingUpdateComponentType = InstanceType<typeof RvpapilodgingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpapilodgingSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpapilodgingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Rvpapilodging Management Update Component', () => {
    let comp: RvpapilodgingUpdateComponentType;
    let rvpapilodgingServiceStub: SinonStubbedInstance<RvpapilodgingService>;

    beforeEach(() => {
      route = {};
      rvpapilodgingServiceStub = sinon.createStubInstance<RvpapilodgingService>(RvpapilodgingService);
      rvpapilodgingServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rvpapilodgingService: () => rvpapilodgingServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpapilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpapilodging = rvpapilodgingSample;
        rvpapilodgingServiceStub.update.resolves(rvpapilodgingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpapilodgingServiceStub.update.calledWith(rvpapilodgingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpapilodgingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpapilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpapilodging = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpapilodgingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpapilodgingServiceStub.find.resolves(rvpapilodgingSample);
        rvpapilodgingServiceStub.retrieve.resolves([rvpapilodgingSample]);

        // WHEN
        route = {
          params: {
            rvpapilodgingId: '' + rvpapilodgingSample.id,
          },
        };
        const wrapper = shallowMount(RvpapilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpapilodging).toMatchObject(rvpapilodgingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpapilodgingServiceStub.find.resolves(rvpapilodgingSample);
        const wrapper = shallowMount(RvpapilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
