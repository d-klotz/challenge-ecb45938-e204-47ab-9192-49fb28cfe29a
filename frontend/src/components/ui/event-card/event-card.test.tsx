import { describe, it } from "vitest";
import { render, screen } from "@testing-library/react";
import { Event } from "../../../types/event";

import EventCard from "./event-card";
import { InstitutionType } from "../../../types/institution-type.ts";

describe("Event Card", () => {
  it("should render the card with all informations", async () => {
    const event: Event = {
      name: "New Event",
      startDate: new Date(),
      endDate: new Date(),
      institution: {
        name: "Instituição 1",
        type: InstitutionType.central,
      },
    };
    render(<EventCard event={event} />);

    await screen.findByRole("heading", { name: /New Event/i });
  });
});
