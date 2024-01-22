import React from "react";
import {
  Avatar,
  AvatarGroup,
  Badge,
  Box,
  Heading,
  Text,
} from "@chakra-ui/react";
import { Event } from "../../../types/event.ts";

const EventCard: React.FC<{
  event: Event;
}> = ({ event }) => {
  return (
    <Box
      maxW="sm"
      borderWidth="1px"
      borderRadius="lg"
      overflow="hidden"
      boxShadow="lg"
      transition="transform 0.3s ease-in-out"
      _hover={{ transform: "scale(1.02)" }}
    >
      <Box px="6" py="3" cursor="pointer">
        <Box display="flex" alignItems="baseline">
          <Badge borderRadius="full" px="2" colorScheme="orange">
            {event.institution.type}
          </Badge>
          <Text ml="2" color="gray.500" fontWeight="semibold">
            {event.institution.name}
          </Text>
        </Box>

        <Heading
          as="h1"
          mt="2"
          fontSize="xl"
          fontWeight="semibold"
          lineHeight="short"
          color="gray.300"
        >
          {event.name}
        </Heading>

        <Text mt="1" color="gray.500">
          Inicio: {event.startDate.toString()}
        </Text>

        <Text mt="1" color="gray.500">
          Termino: {event.endDate.toString()}
        </Text>

        <Box mt="3">
          <AvatarGroup size="sm" max={2}>
            <Avatar name="Ryan Florence" src="https://bit.ly/ryan-florence" />
            <Avatar name="Segun Adebayo" src="https://bit.ly/sage-adebayo" />
            <Avatar name="Kent Dodds" src="https://bit.ly/kent-c-dodds" />
            <Avatar
              name="Prosper Otemuyiwa"
              src="https://bit.ly/prosper-baba"
            />
            <Avatar name="Christian Nwamba" src="https://bit.ly/code-beast" />
          </AvatarGroup>
        </Box>
      </Box>
    </Box>
  );
};

export default EventCard;
