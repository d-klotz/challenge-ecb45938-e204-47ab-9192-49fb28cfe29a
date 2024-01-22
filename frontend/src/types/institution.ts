import { InstitutionSchema } from "../validations/institution-schema.ts";
import { z } from "zod";

export type Institution = z.infer<typeof InstitutionSchema>;
