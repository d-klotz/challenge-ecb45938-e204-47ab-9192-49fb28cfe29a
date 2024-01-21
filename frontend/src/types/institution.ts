import { InstitutionType } from "./institution-type.ts";

export type Institution = {
  id: string;
  name: string;
  type: InstitutionType;
};
