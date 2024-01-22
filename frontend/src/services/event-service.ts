import { Event } from "../types/event.ts";

export const createEvent = async (formData: Event) => {
  try {
    const response = await fetch(`http://localhost:8080/events`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: formData.name,
        startDate: formData.startDate,
        endDate: formData.endDate,
        institutionId: formData.institution.id,
      }),
    });

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to create event:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error creating event:", error.message);
    return false;
  }
};

export const updateEvent = async (eventId: string, formData: Event) => {
  try {
    const response = await fetch(`http://localhost:8080/events/${eventId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: formData.name,
        startDate: formData.startDate,
        endDate: formData.endDate,
        institutionId: formData.institution.id,
      }),
    });

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to update event:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error updating event:", error.message);
    return false;
  }
};

export const deleteEvent = async (id: string): Promise<boolean> => {
  try {
    const response = await fetch(`http://localhost:8080/events/${id}`, {
      method: "DELETE",
    });

    if (response.ok) {
      return true;
    } else {
      console.error("Failed to delete event:", response.statusText);
      return false;
    }
  } catch (error: any) {
    console.error("Error deleting event:", error.message);
    return false;
  }
};
