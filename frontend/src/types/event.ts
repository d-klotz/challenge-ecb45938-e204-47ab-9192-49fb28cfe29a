import { Institution } from "./institution.ts";

export type Event = {
  id: string;
  name: string;
  startDate: string;
  endDate: string;
  institution: Institution;
};
