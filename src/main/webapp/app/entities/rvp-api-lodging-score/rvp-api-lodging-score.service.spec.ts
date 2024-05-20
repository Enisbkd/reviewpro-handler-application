/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import RvpApiLodgingScoreService from './rvp-api-lodging-score.service';
import { DATE_FORMAT } from '@/shared/composables/date-format';
import { RvpApiLodgingScore } from '@/shared/model/rvp-api-lodging-score.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('RvpApiLodgingScore Service', () => {
    let service: RvpApiLodgingScoreService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new RvpApiLodgingScoreService();
      currentDate = new Date();
      elemDefault = new RvpApiLodgingScore(123, 0, 0, 0, 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fd: dayjs(currentDate).format(DATE_FORMAT),
            td: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a RvpApiLodgingScore', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            fd: dayjs(currentDate).format(DATE_FORMAT),
            td: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            fd: currentDate,
            td: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a RvpApiLodgingScore', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a RvpApiLodgingScore', async () => {
        const returnedFromService = Object.assign(
          {
            lodgingId: 1,
            surveyId: 1,
            nps: 1,
            rating: 1,
            customScore: 1,
            fd: dayjs(currentDate).format(DATE_FORMAT),
            td: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            fd: currentDate,
            td: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a RvpApiLodgingScore', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a RvpApiLodgingScore', async () => {
        const patchObject = Object.assign(
          {
            lodgingId: 1,
            nps: 1,
            rating: 1,
            fd: dayjs(currentDate).format(DATE_FORMAT),
            td: dayjs(currentDate).format(DATE_FORMAT),
          },
          new RvpApiLodgingScore(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            fd: currentDate,
            td: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a RvpApiLodgingScore', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of RvpApiLodgingScore', async () => {
        const returnedFromService = Object.assign(
          {
            lodgingId: 1,
            surveyId: 1,
            nps: 1,
            rating: 1,
            customScore: 1,
            fd: dayjs(currentDate).format(DATE_FORMAT),
            td: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            fd: currentDate,
            td: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of RvpApiLodgingScore', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a RvpApiLodgingScore', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a RvpApiLodgingScore', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
