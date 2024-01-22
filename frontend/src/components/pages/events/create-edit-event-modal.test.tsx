import { describe, expect, it, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { Event } from "../../../types/event";
import CreateEditEventModal from "./create-edit-event-modal.tsx";
import { InstitutionType } from "../../../types/institution-type.ts";
import { Institution } from "../../../types/institution.ts";

vi.mock("../../../services/event-service.ts", () => ({
  createEvent: vi.fn(() => Promise.resolve(true)),
}));

describe("Event Table", () => {
  const today = new Date();
  const tomorrow = new Date(Date.now() + 3600 * 1000 * 24);
  const event: Event = {
    id: "1",
    name: "New Event",
    startDate: today,
    endDate: tomorrow,
    institution: {
      name: "Instituição 1",
      type: InstitutionType.central,
    },
  };

  const institutions: Institution[] = [
    {
      id: "1",
      name: "Instituição 1",
      type: InstitutionType.central,
    },
  ];

  it("Save a new event with no errors", async () => {
    const onSave = vi.fn();

    render(
      <CreateEditEventModal
        isOpen={true}
        onClose={onSave}
        institutions={institutions}
        selectedEvent={undefined}
      />,
    );

    await userEvent.click(screen.getByRole("button", { name: /Salvar/i }));
    expect(onSave).toHaveBeenCalledTimes(0);
    await screen.findByLabelText(/Nome é obrigatório/i);
    await screen.findByLabelText(/Required/i);

    await userEvent.type(screen.getByLabelText(/Nome/i), event.name);
    await userEvent.type(
      screen.getByLabelText(/Início/i),
      event.startDate.toString(),
    );
    await userEvent.type(
      screen.getByLabelText(/Término/i),
      event.endDate.toString(),
    );
    await userEvent.selectOptions(screen.getByLabelText(/Instituição/i), "1");
    await userEvent.click(screen.getByRole("button", { name: /Salvar/i }));
    screen.debug();
    expect(onSave).toHaveBeenCalledTimes(1);
  });
});
