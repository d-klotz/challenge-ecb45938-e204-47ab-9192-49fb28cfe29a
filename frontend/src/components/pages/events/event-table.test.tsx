import { describe, expect, it, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { Event } from "../../../types/event";
import EventTable from "./event-table";
import { InstitutionType } from "../../../types/institution-type.ts";

describe("Event Table", () => {
  const event: Event = {
    id: "1",
    name: "New Event",
    startDate: new Date(),
    endDate: new Date(),
    institution: {
      name: "Instituição 1",
      type: InstitutionType.central,
    },
  };

  it("should render an event table", async () => {
    const onEdit = vi.fn();
    const onDelete = vi.fn();

    render(<EventTable events={[event]} onEdit={onEdit} onDelete={onDelete} />);
    await screen.findByRole("table");
    await screen.findByText(/New Event/i);
    await userEvent.click(screen.getByRole("button", { name: /Edit/i }));
    expect(onEdit).toHaveBeenCalledTimes(1);
    await userEvent.click(screen.getByRole("button", { name: /Delete/i }));
    expect(onDelete).toHaveBeenCalledTimes(1);
  });
});
