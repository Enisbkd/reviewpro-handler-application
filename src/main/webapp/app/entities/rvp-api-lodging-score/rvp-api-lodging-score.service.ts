import axios from 'axios';

import { type IRvpApiLodgingScore } from '@/shared/model/rvp-api-lodging-score.model';

const baseApiUrl = 'api/rvp-api-lodging-scores';

export default class RvpApiLodgingScoreService {
  public find(id: number): Promise<IRvpApiLodgingScore> {
    return new Promise<IRvpApiLodgingScore>((resolve, reject) => {
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

  public create(entity: IRvpApiLodgingScore): Promise<IRvpApiLodgingScore> {
    return new Promise<IRvpApiLodgingScore>((resolve, reject) => {
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

  public update(entity: IRvpApiLodgingScore): Promise<IRvpApiLodgingScore> {
    return new Promise<IRvpApiLodgingScore>((resolve, reject) => {
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

  public partialUpdate(entity: IRvpApiLodgingScore): Promise<IRvpApiLodgingScore> {
    return new Promise<IRvpApiLodgingScore>((resolve, reject) => {
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
