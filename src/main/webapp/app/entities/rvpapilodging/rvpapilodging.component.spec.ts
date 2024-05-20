/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Rvpapilodging from './rvpapilodging.vue';
import RvpapilodgingService from './rvpapilodging.service';
import AlertService from '@/shared/alert/alert.service';

type RvpapilodgingComponentType = InstanceType<typeof Rvpapilodging>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Rvpapilodging Management Component', () => {
    let rvpapilodgingServiceStub: SinonStubbedInstance<RvpapilodgingService>;
    let mountOptions: MountingOptions<RvpapilodgingComponentType>['global'];

    beforeEach(() => {
      rvpapilodgingServiceStub = sinon.createStubInstance<RvpapilodgingService>(RvpapilodgingService);
      rvpapilodgingServiceStub.retrieve.resolves({ headers: {} });

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
          rvpapilodgingService: () => rvpapilodgingServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpapilodgingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Rvpapilodging, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rvpapilodgingServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rvpapilodgings[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: RvpapilodgingComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Rvpapilodging, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rvpapilodgingServiceStub.retrieve.reset();
        rvpapilodgingServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rvpapilodgingServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeRvpapilodging();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rvpapilodgingServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rvpapilodgingServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
