/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RvpApiLodgingScore from './rvp-api-lodging-score.vue';
import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiLodgingScoreComponentType = InstanceType<typeof RvpApiLodgingScore>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RvpApiLodgingScore Management Component', () => {
    let rvpApiLodgingScoreServiceStub: SinonStubbedInstance<RvpApiLodgingScoreService>;
    let mountOptions: MountingOptions<RvpApiLodgingScoreComponentType>['global'];

    beforeEach(() => {
      rvpApiLodgingScoreServiceStub = sinon.createStubInstance<RvpApiLodgingScoreService>(RvpApiLodgingScoreService);
      rvpApiLodgingScoreServiceStub.retrieve.resolves({ headers: {} });

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
          rvpApiLodgingScoreService: () => rvpApiLodgingScoreServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiLodgingScoreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RvpApiLodgingScore, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rvpApiLodgingScoreServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rvpApiLodgingScores[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RvpApiLodgingScoreComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RvpApiLodgingScore, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rvpApiLodgingScoreServiceStub.retrieve.reset();
        rvpApiLodgingScoreServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rvpApiLodgingScoreServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRvpApiLodgingScore();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rvpApiLodgingScoreServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rvpApiLodgingScoreServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
