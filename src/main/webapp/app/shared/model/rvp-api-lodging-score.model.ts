export interface IRvpApiLodgingScore {
  id?: number;
  lodgingId?: number | null;
  surveyId?: number | null;
  nps?: number | null;
  rating?: number | null;
  customScore?: number | null;
  fd?: Date | null;
  td?: Date | null;
}

export class RvpApiLodgingScore implements IRvpApiLodgingScore {
  constructor(
    public id?: number,
    public lodgingId?: number | null,
    public surveyId?: number | null,
    public nps?: number | null,
    public rating?: number | null,
    public customScore?: number | null,
    public fd?: Date | null,
    public td?: Date | null,
  ) {}
}
