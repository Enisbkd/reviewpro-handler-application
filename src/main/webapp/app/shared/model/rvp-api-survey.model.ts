export interface IRvpApiSurvey {
  id?: number;
  overallScoreEnabled?: boolean | null;
  languages?: string | null;
  outOf?: number | null;
  name?: string | null;
  active?: boolean | null;
  pids?: string | null;
  primary?: boolean | null;
}

export class RvpApiSurvey implements IRvpApiSurvey {
  constructor(
    public id?: number,
    public overallScoreEnabled?: boolean | null,
    public languages?: string | null,
    public outOf?: number | null,
    public name?: string | null,
    public active?: boolean | null,
    public pids?: string | null,
    public primary?: boolean | null,
  ) {
    this.overallScoreEnabled = this.overallScoreEnabled ?? false;
    this.active = this.active ?? false;
    this.primary = this.primary ?? false;
  }
}
