import {
  Button,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Spinner,
  Text,
} from "@chakra-ui/react";
import React, { useState } from "react";
import { deleteEvent } from "../../../services/event-service.ts";

interface CustomModalProps {
  isOpen: boolean;
  onClose: () => void;
  eventId: string;
}

const deleteEventModal: React.FC<CustomModalProps> = ({
  isOpen,
  onClose,
  eventId,
}) => {
  const [isLoading, setIsLoading] = useState(false);

  const onDelete = async () => {
    setIsLoading(true);
    await deleteEvent(eventId);
    setIsLoading(false);
    onClose();
  };
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent bg="gray.800" color="white">
        <ModalHeader>Excluir Evento</ModalHeader>
        <ModalCloseButton />
        <ModalBody>Tem certeza que deseja excluir este evento?</ModalBody>

        <ModalFooter>
          <Button
            colorScheme="orange"
            variant="outline"
            mr={3}
            onClick={onClose}
          >
            Cancelar
          </Button>
          <Button colorScheme="red" onClick={onDelete}>
            <Text mr={2}>Excluir</Text>
            {isLoading && <Spinner size="sm" />}
          </Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default deleteEventModal;
