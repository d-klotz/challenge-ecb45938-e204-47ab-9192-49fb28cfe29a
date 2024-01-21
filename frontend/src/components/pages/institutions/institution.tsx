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
import InstitutionTable from "./Institution-table.tsx";
import { Institution } from "../../../types/institution.ts";
import useSWR from "swr";
import CreateEditModal from "./create-edit-modal.tsx";
import DeleteInstitutionModal from "./delete-institution-modal.tsx";
import { useState } from "react";

const Institutions = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const {
    isOpen: isDeleteOpen,
    onOpen: onDeleteOpen,
    onClose: onDeleteClose,
  } = useDisclosure();
  const [selectedInstitution, setSelectedInstitution] = useState<Institution>();
  const { data: institutions, isLoading } = useSWR<Institution[]>(
    "http://localhost:8080/institutions",
  );

  const openDeleteModal = (institution: Institution) => {
    setSelectedInstitution(institution);
    onDeleteOpen();
  };

  if (isLoading) return <div>Carregando...</div>;
  if (!institutions) return <div>Erro ao carregar instituições</div>;

  return (
    <Box>
      <Center mt={3} mb={6}>
        <Text color="white" fontSize="3xl" fontWeight="semibold">
          Instituições
        </Text>
      </Center>

      <Flex dir="row" my={5}>
        <Spacer />
        <ButtonGroup>
          <Button colorScheme="orange" onClick={onOpen}>
            Nova Instituição
          </Button>
        </ButtonGroup>
      </Flex>

      <InstitutionTable
        institutions={institutions}
        onDelete={openDeleteModal}
        onEdit={() => onOpen()}
      />

      <CreateEditModal isOpen={isOpen} onClose={onClose} />
      {selectedInstitution && (
        <DeleteInstitutionModal
          isOpen={isDeleteOpen}
          onClose={onDeleteClose}
          institutionId={selectedInstitution.id}
        />
      )}
    </Box>
  );
};

export default Institutions;
