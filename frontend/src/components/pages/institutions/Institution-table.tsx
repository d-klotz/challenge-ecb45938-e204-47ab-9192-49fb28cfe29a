import React from "react";
import {
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
import { Institution } from "../../../types/institution.ts";

interface InstitutionTableProps {
  institutions: Institution[];
  onEdit: (institution: Institution) => void;
  onDelete: (institution: Institution) => void;
}

const InstitutionTable: React.FC<InstitutionTableProps> = ({
  institutions,
  onEdit,
  onDelete,
}) => {
  return (
    <TableContainer
      borderWidth="1px"
      borderColor="gray.600"
      borderRadius="lg"
      overflow="hidden"
      p={4}
    >
      <Table variant="unstyled" color="white" size="md">
        <Thead>
          <Tr>
            <Th>Nome</Th>
            <Th>Tipo</Th>
            <Th>Ações</Th>
          </Tr>
        </Thead>
        <Tbody>
          {institutions.map((institution) => (
            <Tr
              key={institution.id}
              borderTopWidth="1px"
              borderColor="gray.600"
            >
              <Td>{institution.name}</Td>
              <Td>{institution.type}</Td>
              <Td>
                <IconButton
                  icon={<EditIcon />}
                  colorScheme="orange"
                  aria-label="Edit"
                  onClick={() => onEdit(institution)}
                  mr={2}
                />
                <IconButton
                  icon={<DeleteIcon />}
                  colorScheme="red"
                  aria-label="Delete"
                  onClick={() => onDelete(institution)}
                />
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default InstitutionTable;
