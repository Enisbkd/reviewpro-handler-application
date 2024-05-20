/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RvpApilodging from './rvp-apilodging.vue';
import RvpApilodgingService from './rvp-apilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApilodgingComponentType = InstanceType<typeof RvpApilodging>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RvpApilodging Management Component', () => {
    let rvpApilodgingServiceStub: SinonStubbedInstance<RvpApilodgingService>;
    let mountOptions: MountingOptions<RvpApilodgingComponentType>['global'];

    beforeEach(() => {
      rvpApilodgingServiceStub = sinon.createStubInstance<RvpApilodgingService>(RvpApilodgingService);
      rvpApilodgingServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          rvpApilodgingService: () => rvpApilodgingServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApilodgingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(RvpApilodging, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rvpApilodgingServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rvpApilodgings[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: RvpApilodgingComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RvpApilodging, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rvpApilodgingServiceStub.retrieve.reset();
        rvpApilodgingServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rvpApilodgingServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeRvpApilodging();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rvpApilodgingServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rvpApilodgingServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
