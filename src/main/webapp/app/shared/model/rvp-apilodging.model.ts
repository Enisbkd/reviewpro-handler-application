export interface IRvpApilodging {
  id?: string;
  ida?: number | null;
  name?: string | null;
}

export class RvpApilodging implements IRvpApilodging {
  constructor(
    public id?: string,
    public ida?: number | null,
    public name?: string | null,
  ) {}
}
