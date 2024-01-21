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
import { deleteInstitution } from "../../../services/institution-service.ts";

interface CustomModalProps {
  isOpen: boolean;
  onClose: () => void;
  institutionId: string;
}

const deleteInstitutionModal: React.FC<CustomModalProps> = ({
  isOpen,
  onClose,
  institutionId,
}) => {
  const [isLoading, setIsLoading] = useState(false);

  const onDelete = async () => {
    setIsLoading(true);
    await deleteInstitution(institutionId);
    setIsLoading(false);
    onClose();
  };
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent bg="gray.800" color="white">
        <ModalHeader>Excluir Instituição</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          Tem certeza que deseja excluir esta instituição?
          <Text fontSize="sm" color="gray.400">
            Todos os eventos atrelados à esta instituição serao apagados.
          </Text>
        </ModalBody>

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

export default deleteInstitutionModal;
