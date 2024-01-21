import { InstitutionSchema } from "../validations/institution-schema.ts";
import { z } from "zod";

export const createInstitution = async (
  formData: z.infer<typeof InstitutionSchema>,
): Promise<boolean> => {
  try {
    const response = await fetch(`http://localhost:8080/institutions`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to create institution:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error creating institution:", error.message);
    return false;
  }
};
