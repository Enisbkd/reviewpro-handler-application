/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import RvpApiResponseUpdate from './rvp-api-response-update.vue';
import RvpApiResponseService from './rvp-api-response.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type RvpApiResponseUpdateComponentType = InstanceType<typeof RvpApiResponseUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rvpApiResponseSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RvpApiResponseUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RvpApiResponse Management Update Component', () => {
    let comp: RvpApiResponseUpdateComponentType;
    let rvpApiResponseServiceStub: SinonStubbedInstance<RvpApiResponseService>;

    beforeEach(() => {
      route = {};
      rvpApiResponseServiceStub = sinon.createStubInstance<RvpApiResponseService>(RvpApiResponseService);
      rvpApiResponseServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rvpApiResponseService: () => rvpApiResponseServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(RvpApiResponseUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RvpApiResponseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiResponse = rvpApiResponseSample;
        rvpApiResponseServiceStub.update.resolves(rvpApiResponseSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiResponseServiceStub.update.calledWith(rvpApiResponseSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rvpApiResponseServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RvpApiResponseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rvpApiResponse = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rvpApiResponseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rvpApiResponseServiceStub.find.resolves(rvpApiResponseSample);
        rvpApiResponseServiceStub.retrieve.resolves([rvpApiResponseSample]);

        // WHEN
        route = {
          params: {
            rvpApiResponseId: '' + rvpApiResponseSample.id,
          },
        };
        const wrapper = shallowMount(RvpApiResponseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rvpApiResponse).toMatchObject(rvpApiResponseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rvpApiResponseServiceStub.find.resolves(rvpApiResponseSample);
        const wrapper = shallowMount(RvpApiResponseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
