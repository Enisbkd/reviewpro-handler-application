/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RvpApilodgingUpdate from './rvp-apilodging-update.vue';
import RvpApilodgingService from './rvp-apilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApilodgingUpdateComponentType = InstanceType<typeof RvpApilodgingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApilodgingSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpApilodgingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RvpApilodging Management Update Component', () => {
    let comp: RvpApilodgingUpdateComponentType;
    let rvpApilodgingServiceStub: SinonStubbedInstance<RvpApilodgingService>;

    beforeEach(() => {
      route = {};
      rvpApilodgingServiceStub = sinon.createStubInstance<RvpApilodgingService>(RvpApilodgingService);
      rvpApilodgingServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rvpApilodgingService: () => rvpApilodgingServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpApilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApilodging = rvpApilodgingSample;
        rvpApilodgingServiceStub.update.resolves(rvpApilodgingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApilodgingServiceStub.update.calledWith(rvpApilodgingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpApilodgingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpApilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApilodging = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApilodgingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpApilodgingServiceStub.find.resolves(rvpApilodgingSample);
        rvpApilodgingServiceStub.retrieve.resolves([rvpApilodgingSample]);

        // WHEN
        route = {
          params: {
            rvpApilodgingId: '' + rvpApilodgingSample.id,
          },
        };
        const wrapper = shallowMount(RvpApilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApilodging).toMatchObject(rvpApilodgingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApilodgingServiceStub.find.resolves(rvpApilodgingSample);
        const wrapper = shallowMount(RvpApilodgingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
