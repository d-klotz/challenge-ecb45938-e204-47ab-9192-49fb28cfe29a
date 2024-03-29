import { Box, Center, SimpleGrid, Text } from "@chakra-ui/react";
import EventCard from "../../ui/event-card/event-card.tsx";
import useSWR from "swr";
import { Event } from "../../../types/event.ts";

const Home = () => {
  const { data: events, isLoading } = useSWR<Event[]>(
    "http://localhost:8080/events",
  );

  if (isLoading) return <div>Carregando...</div>;
  if (!events) return <div>Erro ao carregar eventos</div>;

  //todo: filter active events on the backend
  const activeEvents = filterActiveEvents(events);

  return (
    <Box>
      <Center mt={3} mb={6}>
        <Text color="white" fontSize="3xl" fontWeight="semibold">
          Eventos ativos
        </Text>
      </Center>

      <SimpleGrid minChildWidth="300px" spacing="20px">
        {activeEvents.map((event) => (
          <EventCard event={event}></EventCard>
        ))}
      </SimpleGrid>
    </Box>
  );
};

const filterActiveEvents = (events: Event[]): Event[] => {
  return events.filter((event) => event.active);
};

export default Home;
