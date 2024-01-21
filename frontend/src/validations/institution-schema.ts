import { z } from "zod";
import { InstitutionType } from "../types/institution-type.ts";

export const InstitutionTypeSchema = z.nativeEnum(InstitutionType, {
  errorMap: (issue, _ctx) => {
    switch (issue.code) {
      case "invalid_type":
        return { message: "Tipo invalido" };
      case "invalid_enum_value":
        return { message: "O tipo escolhido é invalido" };
      default:
        return { message: "Tipo invalido" };
    }
  },
});

export const InstitutionSchema = z.object({
  name: z.string().min(1, { message: "Nome é obrigatório" }),
  type: InstitutionTypeSchema,
});
