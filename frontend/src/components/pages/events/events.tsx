import {
  Box,
  Button,
  ButtonGroup,
  Center,
  Flex,
  Spacer,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import EventTable from "./event-table.tsx";
import { useState } from "react";
import useSWR from "swr";
import { Event } from "../../../types/event.ts";
import CreateEditEventModal from "./create-edit-event-modal.tsx";
import { Institution } from "../../../types/institution.ts";
import DeleteEventModal from "./delete-event-modal.tsx";

const Events = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isDeleteOpen,
    onOpen: onDeleteOpen,
    onClose: onDeleteClose,
  } = useDisclosure();

  const [selectedEvent, setSelectedEvent] = useState<Event>();

  const { data: events, isLoading } = useSWR<Event[]>(
    "http://localhost:8080/events",
  );

  const { data: institutions } = useSWR<Institution[]>(
    "http://localhost:8080/institutions",
  );

  const openDeleteModal = (event: Event) => {
    setSelectedEvent(event);
    onDeleteOpen();
  };
  const openEditModal = (event: Event) => {
    setSelectedEvent(event);
    onOpen();
  };

  if (!events) return <div>Erro ao carregar eventos</div>;
  if (isLoading) return <div>Carregando...</div>;

  return (
    <Box>
      <Center mt={3} mb={6}>
        <Text color="white" fontSize="3xl" fontWeight="semibold">
          Eventos
        </Text>
      </Center>

      <Flex dir="row" my={5}>
        <Spacer />
        <ButtonGroup>
          <Button colorScheme="orange" onClick={onOpen}>
            Novo Evento
          </Button>
        </ButtonGroup>
      </Flex>

      <EventTable
        events={events}
        onDelete={openDeleteModal}
        onEdit={openEditModal}
      />

      {institutions && (
        <CreateEditEventModal
          isOpen={isOpen}
          onClose={onClose}
          selectedEvent={selectedEvent}
          institutions={institutions}
        />
      )}

      {selectedEvent && (
        <DeleteEventModal
          isOpen={isDeleteOpen}
          onClose={onDeleteClose}
          eventId={selectedEvent.id!}
        />
      )}
    </Box>
  );
};

export default Events;
