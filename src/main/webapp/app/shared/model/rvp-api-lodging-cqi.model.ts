export interface IRvpApiLodgingCqi {
  id?: number;
  lodgingId?: number | null;
  averageCurrentPeriod?: string | null;
  tendancy?: string | null;
  change?: string | null;
  name?: string | null;
  averagePreviousPeriod?: string | null;
  fd?: Date | null;
  td?: Date | null;
}

export class RvpApiLodgingCqi implements IRvpApiLodgingCqi {
  constructor(
    public id?: number,
    public lodgingId?: number | null,
    public averageCurrentPeriod?: string | null,
    public tendancy?: string | null,
    public change?: string | null,
    public name?: string | null,
    public averagePreviousPeriod?: string | null,
    public fd?: Date | null,
    public td?: Date | null,
  ) {}
}
