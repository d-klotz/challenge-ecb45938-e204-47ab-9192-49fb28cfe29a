import { z } from "zod";
import { InstitutionSchema } from "./institution-schema.ts";

export const EventSchema = z
  .object({
    id: z.string().optional(),
    name: z.string().min(1, { message: "Nome é obrigatório" }),
    startDate: z.date({
      required_error: "Selecione uma data",
      invalid_type_error: "Formato incorreto",
    }),
    endDate: z.date({
      required_error: "Selecione uma data",
      invalid_type_error: "Formato incorreto",
    }),
    institution: InstitutionSchema,
  })
  .refine((data) => data.endDate > data.startDate, {
    message: "Data de término nao pode ser menor do que a data de início.",
    path: ["endDate"],
  });
