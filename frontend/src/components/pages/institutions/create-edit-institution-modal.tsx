import React, { useEffect, useState } from "react";
import {
  Button,
  ButtonGroup,
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalHeader,
  ModalOverlay,
  Select,
  Spinner,
  Text,
  VStack,
} from "@chakra-ui/react";
import { ZodError } from "zod";
import { InstitutionSchema } from "../../../schemas/institution-schema.ts";
import { InstitutionType } from "../../../types/institution-type.ts";
import {
  createInstitution,
  updateInstitution,
} from "../../../services/institution-service.ts";
import { Institution } from "../../../types/institution.ts";

interface CustomModalProps {
  isOpen: boolean;
  onClose: () => void;
  selectedInstitution?: Institution;
}

const CreateEditInstitutionModal: React.FC<CustomModalProps> = ({
  isOpen,
  onClose,
  selectedInstitution,
}) => {
  const [formData, setFormData] = useState({
    name: "",
    type: "",
  });
  const [formErrors, setFormErrors] = useState<{ [key: string]: string }>({});
  const [isLoading, setIsLoading] = useState(false);
  const isEditMode = !!selectedInstitution;

  useEffect(() => {
    if (selectedInstitution) {
      setFormData({
        name: selectedInstitution.name,
        type: selectedInstitution.type,
      });
    }
  }, [selectedInstitution]);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    setFormData((prevData) => ({
      ...prevData,
      [e.target.name]: e.target.value,
    }));
    setFormErrors({});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const validatedFormData = InstitutionSchema.parse(formData);
      setIsLoading(true);
      const result = isEditMode
        ? await updateInstitution(selectedInstitution.id!, validatedFormData)
        : await createInstitution(validatedFormData);
      setIsLoading(false);

      if (result) {
        onModalClose();
      }
    } catch (error) {
      if (error instanceof ZodError) {
        const errors: { [key: string]: string } = {};
        error.errors.forEach((validationError) => {
          if (validationError.path) {
            errors[validationError.path.join(".")] = validationError.message;
          }
        });
        setFormErrors(errors);
      }
    }
  };

  const onModalClose = () => {
    setFormData({ name: "", type: "" });
    setFormErrors({});
    onClose();
  };

  return (
    <Modal isOpen={isOpen} onClose={onModalClose}>
      <ModalOverlay />
      <ModalContent bg="gray.800" color="white">
        <ModalHeader>
          {isEditMode ? "Alteração de Instituição" : "Cadastro de Instituição"}
        </ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <form onSubmit={handleSubmit}>
            <VStack spacing={4}>
              <FormControl>
                <FormLabel htmlFor="name">Nome</FormLabel>
                <Input
                  type="text"
                  id="name"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  placeholder="Insira um nome"
                />
                {formErrors["name"] && (
                  <div style={{ color: "red" }}>{formErrors["name"]}</div>
                )}
              </FormControl>

              <FormControl>
                <FormLabel htmlFor="type">Tipo</FormLabel>
                <Select
                  id="type"
                  name="type"
                  value={formData.type}
                  onChange={handleInputChange}
                  placeholder="Selecione o tipo"
                >
                  {Object.entries(institutionTypeMap).map(([key, value]) => (
                    <option
                      key={key}
                      value={value}
                      style={{ background: "#30303b", color: "white" }}
                    >
                      {key}
                    </option>
                  ))}
                </Select>
                {formErrors["type"] && (
                  <div style={{ color: "red" }}>{formErrors["type"]}</div>
                )}
              </FormControl>
              <ButtonGroup>
                <Button
                  colorScheme="orange"
                  variant="outline"
                  mr={3}
                  onClick={onModalClose}
                >
                  Cancelar
                </Button>
                <Button colorScheme="orange" mr={3} type="submit">
                  <Text mr={2}>Salvar</Text>
                  {isLoading && <Spinner size="sm" />}
                </Button>
              </ButtonGroup>
            </VStack>
          </form>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default CreateEditInstitutionModal;

const institutionTypeMap: Record<string, InstitutionType> = {
  Confederacao: InstitutionType.confederacao,
  Singular: InstitutionType.singular,
  Central: InstitutionType.central,
  Cooperativa: InstitutionType.cooperativa,
};
