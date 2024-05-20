/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RvpApiSurvey from './rvp-api-survey.vue';
import RvpApiSurveyService from './rvp-api-survey.service';
import AlertService from '@/shared/alert/alert.service';

type RvpApiSurveyComponentType = InstanceType<typeof RvpApiSurvey>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RvpApiSurvey Management Component', () => {
    let rvpApiSurveyServiceStub: SinonStubbedInstance<RvpApiSurveyService>;
    let mountOptions: MountingOptions<RvpApiSurveyComponentType>['global'];

    beforeEach(() => {
      rvpApiSurveyServiceStub = sinon.createStubInstance<RvpApiSurveyService>(RvpApiSurveyService);
      rvpApiSurveyServiceStub.retrieve.resolves({ headers: {} });

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
          rvpApiSurveyService: () => rvpApiSurveyServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rvpApiSurveyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(RvpApiSurvey, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rvpApiSurveyServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rvpApiSurveys[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: RvpApiSurveyComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RvpApiSurvey, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rvpApiSurveyServiceStub.retrieve.reset();
        rvpApiSurveyServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rvpApiSurveyServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeRvpApiSurvey();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rvpApiSurveyServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rvpApiSurveyServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
