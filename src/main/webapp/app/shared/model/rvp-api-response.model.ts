export interface IRvpApiResponse {
  id?: number;
  surveyId?: number | null;
  lodgingId?: number | null;
  date?: Date | null;
  overallsatsifaction?: number | null;
  customScore?: number | null;
  plantorevisit?: boolean | null;
}

export class RvpApiResponse implements IRvpApiResponse {
  constructor(
    public id?: number,
    public surveyId?: number | null,
    public lodgingId?: number | null,
    public date?: Date | null,
    public overallsatsifaction?: number | null,
    public customScore?: number | null,
    public plantorevisit?: boolean | null,
  ) {
    this.plantorevisit = this.plantorevisit ?? false;
  }
}
