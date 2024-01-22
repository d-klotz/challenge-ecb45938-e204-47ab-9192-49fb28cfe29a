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
import { Event } from "../../../types/event.ts";
import { createEvent, updateEvent } from "../../../services/event-service.ts";
import { EventSchema } from "../../../validations/event-schema.ts";
import { Institution } from "../../../types/institution.ts";
import { css } from "@emotion/react";

interface CustomModalProps {
  isOpen: boolean;
  onClose: () => void;
  selectedEvent?: Event;
  institutions: Institution[];
}

const CreateEditEventModal: React.FC<CustomModalProps> = ({
  isOpen,
  onClose,
  selectedEvent,
  institutions,
}) => {
  const [formData, setFormData] = useState({
    name: "",
    startDate: new Date(),
    endDate: new Date(),
    institution: undefined as unknown as Institution,
  });
  const [formErrors, setFormErrors] = useState<{ [key: string]: string }>({});
  const [isLoading, setIsLoading] = useState(false);
  const isEditMode = !!selectedEvent;

  useEffect(() => {
    if (selectedEvent) {
      setFormData({
        name: selectedEvent.name,
        startDate: selectedEvent.startDate,
        endDate: selectedEvent.endDate,
        institution: selectedEvent.institution,
      });
    }
  }, [selectedEvent]);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    setFormData((prevData) => ({
      ...prevData,
      [e.target.name]:
        e.target.name === "institution"
          ? institutions.find((inst) => inst.id === e.target.value) || undefined
          : e.target.value,
    }));
    setFormErrors({});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      console.log(formData);
      const validatedFormData = EventSchema.parse({
        ...formData,
        startDate: new Date(formData.startDate),
        endDate: new Date(formData.endDate),
      });
      setIsLoading(true);
      const result = isEditMode
        ? await updateEvent(selectedEvent.id!, validatedFormData)
        : await createEvent(validatedFormData);
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
    setFormData({
      name: "",
      startDate: new Date(),
      endDate: new Date(),
      institution: undefined as unknown as Institution,
    });
    setFormErrors({});
    onClose();
  };

  return (
    <Modal isOpen={isOpen} onClose={onModalClose}>
      <ModalOverlay />
      <ModalContent bg="gray.800" color="white">
        <ModalHeader>
          {isEditMode ? "Alteração de Evento" : "Cadastro de Evento"}
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
                <FormLabel htmlFor="startDate">Data de Início</FormLabel>
                <Input
                  css={css`
                    ::-webkit-calendar-picker-indicator {
                      background: none;
                    }
                  `}
                  type="date"
                  id="startDate"
                  name="startDate"
                  value={formData.startDate.toString()}
                  onChange={handleInputChange}
                  placeholder="Selecione uma data de início"
                />
                {formErrors["startDate"] && (
                  <div style={{ color: "red" }}>{formErrors["startDate"]}</div>
                )}
              </FormControl>

              <FormControl>
                <FormLabel htmlFor="endDateDate">Data de Término</FormLabel>
                <Input
                  css={css`
                    ::-webkit-calendar-picker-indicator {
                      background: none;
                    }
                  `}
                  type="date"
                  id="endDate"
                  name="endDate"
                  value={formData.endDate.toString()}
                  onChange={handleInputChange}
                  placeholder="Selecione uma data de término"
                />
                {formErrors["endDate"] && (
                  <div style={{ color: "red" }}>{formErrors["endDate"]}</div>
                )}
              </FormControl>

              <FormControl>
                <FormLabel htmlFor="institution">Instituição</FormLabel>
                <Select
                  id="institution"
                  name="institution"
                  value={formData.institution?.id || undefined}
                  onChange={handleInputChange}
                  placeholder="Selecione a instituição"
                >
                  {institutions.map((institution) => (
                    <option
                      key={institution.id}
                      value={institution.id}
                      style={{ background: "#30303b", color: "white" }}
                    >
                      {institution.name}
                    </option>
                  ))}
                </Select>
                {formErrors["institution"] && (
                  <div style={{ color: "red" }}>
                    {formErrors["institution"]}
                  </div>
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

export default CreateEditEventModal;
