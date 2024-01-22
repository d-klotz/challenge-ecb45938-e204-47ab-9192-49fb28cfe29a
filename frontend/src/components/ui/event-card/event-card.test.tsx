import { describe, it } from "vitest";
import { render, screen } from "@testing-library/react";
import { Event } from "../../../types/event";

import EventCard from "./event-card";
import { InstitutionType } from "../../../types/institution-type.ts";

describe("Event Card", () => {
  const event: Event = {
    name: "New Event",
    startDate: new Date(),
    endDate: new Date(),
    institution: {
      name: "Instituição 1",
      type: InstitutionType.central,
    },
  };

  it("should render the card with all information", async () => {
    render(<EventCard event={event} />);

    await screen.findByRole("heading", { name: /New Event/i });
    await screen.findByText(/Instituição 1/i);
    await screen.findByText(/Inicio: /i);
    await screen.findByText(/Termino: /i);
    await screen.findByText(InstitutionType.central);
  });
});
