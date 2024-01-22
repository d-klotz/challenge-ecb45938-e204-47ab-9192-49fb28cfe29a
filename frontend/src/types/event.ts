import { z } from "zod";
import { EventSchema } from "../validations/event-schema.ts";

export type Event = z.infer<typeof EventSchema>;
