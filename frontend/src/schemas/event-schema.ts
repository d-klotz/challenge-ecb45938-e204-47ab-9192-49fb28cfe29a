import { z } from "zod";
import { InstitutionSchema } from "./institution-schema.ts";

export const EventSchema = z
  .object({
    id: z.string().optional(),
    name: z.string().min(1, { message: "Nome é obrigatório" }),
    startDate: z.date({
      required_error: "Selecione uma data",
    }),
    endDate: z.date({
      required_error: "Selecione uma data",
    }),
    institution: InstitutionSchema,
    active: z.boolean().optional(),
  })
  .refine((data) => data.endDate > data.startDate, {
    message: "Data de término nao pode ser menor do que a data de início.",
    path: ["endDate"],
  });
