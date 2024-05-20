import axios from 'axios';

import { type IRvpApiLodgingCqi } from '@/shared/model/rvp-api-lodging-cqi.model';

const baseApiUrl = 'api/rvp-api-lodging-cqis';

export default class RvpApiLodgingCqiService {
  public find(id: number): Promise<IRvpApiLodgingCqi> {
    return new Promise<IRvpApiLodgingCqi>((resolve, reject) => {
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

  public create(entity: IRvpApiLodgingCqi): Promise<IRvpApiLodgingCqi> {
    return new Promise<IRvpApiLodgingCqi>((resolve, reject) => {
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

  public update(entity: IRvpApiLodgingCqi): Promise<IRvpApiLodgingCqi> {
    return new Promise<IRvpApiLodgingCqi>((resolve, reject) => {
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

  public partialUpdate(entity: IRvpApiLodgingCqi): Promise<IRvpApiLodgingCqi> {
    return new Promise<IRvpApiLodgingCqi>((resolve, reject) => {
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
