export interface IRvpapilodging {
  id?: string;
  ida?: number | null;
  name?: string | null;
}

export class Rvpapilodging implements IRvpapilodging {
  constructor(
    public id?: string,
    public ida?: number | null,
    public name?: string | null,
  ) {}
}
