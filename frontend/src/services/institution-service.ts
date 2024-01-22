import { InstitutionSchema } from "../schemas/institution-schema.ts";
import { z } from "zod";
import { Institution } from "../types/institution.ts";

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

export const updateInstitution = async (
  institutionId: string,
  formData: Institution,
): Promise<boolean> => {
  try {
    const response = await fetch(
      `http://localhost:8080/institutions/${institutionId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      },
    );

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to update institution:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error updating institution:", error.message);
    return false;
  }
};

export const deleteInstitution = async (id: string): Promise<boolean> => {
  try {
    const response = await fetch(`http://localhost:8080/institutions/${id}`, {
      method: "DELETE",
    });

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to delete institution:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error deleting institution:", error.message);
    return false;
  }
};
