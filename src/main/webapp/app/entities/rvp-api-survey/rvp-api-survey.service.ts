import axios from 'axios';

import { type IRvpApiSurvey } from '@/shared/model/rvp-api-survey.model';

const baseApiUrl = 'api/rvp-api-surveys';

export default class RvpApiSurveyService {
  public find(id: number): Promise<IRvpApiSurvey> {
    return new Promise<IRvpApiSurvey>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IRvpApiSurvey): Promise<IRvpApiSurvey> {
    return new Promise<IRvpApiSurvey>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IRvpApiSurvey): Promise<IRvpApiSurvey> {
    return new Promise<IRvpApiSurvey>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IRvpApiSurvey): Promise<IRvpApiSurvey> {
    return new Promise<IRvpApiSurvey>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
