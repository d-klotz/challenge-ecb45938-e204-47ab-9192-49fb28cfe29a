import React from "react";
import {
  Badge,
  IconButton,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { DeleteIcon, EditIcon } from "@chakra-ui/icons";
import { Event } from "../../../types/event.ts";

interface EventTableProps {
  events: Event[];
  onEdit: (event: Event) => void;
  onDelete: (event: Event) => void;
}

const EventTable: React.FC<EventTableProps> = ({
  events,
  onEdit,
  onDelete,
}) => {
  return (
    <TableContainer
      borderWidth="1px"
      borderColor="gray.600"
      borderRadius="md"
      overflow="hidden"
      p={4}
    >
      <Table variant="unstyled" color="white" size="sm">
        <Thead>
          <Tr>
            <Th>Nome</Th>
            <Th>Status</Th>
            <Th>Início</Th>
            <Th>Término</Th>
            <Th>Instituição</Th>
            <Th>Ações</Th>
          </Tr>
        </Thead>
        <Tbody>
          {sortedEventsByActive(events).map((event) => (
            <Tr key={event.id} borderTopWidth="1px" borderColor="gray.600">
              <Td>{event.name}</Td>
              <Td>
                {event.active ? (
                  <Badge colorScheme="green">Ativo</Badge>
                ) : (
                  <Badge colorScheme="gray">Inativo</Badge>
                )}
              </Td>
              <Td>{event.startDate.toString()}</Td>
              <Td>{event.endDate.toString()}</Td>
              <Td>{event.institution.name}</Td>
              <Td>
                <IconButton
                  icon={<EditIcon />}
                  colorScheme="orange"
                  aria-label="Edit"
                  onClick={() => onEdit(event)}
                  mr={2}
                />
                <IconButton
                  icon={<DeleteIcon />}
                  colorScheme="red"
                  aria-label="Delete"
                  onClick={() => onDelete(event)}
                />
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default EventTable;

const sortedEventsByActive = (events: Event[]) => {
  return events.sort((a: Event, b: Event) => {
    // todo: fix type inconsistency
    // @ts-ignore
    return b.active - a.active;
  });
};
