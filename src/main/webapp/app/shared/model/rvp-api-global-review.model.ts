export interface IRvpApiGlobalReview {
  id?: number;
  lodgingid?: number | null;
  prevGri?: number | null;
  distribution?: string | null;
  gri?: number | null;
  fd?: Date | null;
  td?: Date | null;
}

export class RvpApiGlobalReview implements IRvpApiGlobalReview {
  constructor(
    public id?: number,
    public lodgingid?: number | null,
    public prevGri?: number | null,
    public distribution?: string | null,
    public gri?: number | null,
    public fd?: Date | null,
    public td?: Date | null,
  ) {}
}
