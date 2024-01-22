import { z } from "zod";
import { EventSchema } from "../schemas/event-schema.ts";

export type Event = z.infer<typeof EventSchema>;
