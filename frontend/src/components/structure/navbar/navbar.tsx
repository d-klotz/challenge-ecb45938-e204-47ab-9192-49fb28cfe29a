import { Box, ContainerProps, Flex, forwardRef, Text } from "@chakra-ui/react";

const Navbar = forwardRef<ContainerProps, "div">((props, ref) => {
  return (
    <Box minH="100vh" bg="gray.700" ref={ref} {...props}>
      <Flex direction="column" p={4}>
        <Text fontSize="xl" fontWeight="bold" mb={4} color="white">
          Index Bank
        </Text>
        <a href="/">
          {" "}
          <Text
            color="white"
            fontSize="md"
            mb={2}
            px={2}
            py={2}
            _hover={{
              background: "orange.600",
              borderRadius: "md",
              color: "white",
            }}
          >
            Meus Eventos
          </Text>
        </a>
        <a href="/events">
          <Text
            color="white"
            fontSize="md"
            mb={2}
            px={2}
            py={2}
            _hover={{
              background: "orange.600",
              borderRadius: "md",
              color: "white",
            }}
          >
            Cadastro de Eventos
          </Text>
        </a>
        <a href="/institutions">
          <Text
            color="white"
            fontSize="md"
            px={2}
            py={2}
            _hover={{
              background: "orange.600",
              borderRadius: "md",
              color: "white",
            }}
          >
            Cadastro de Instituições
          </Text>
        </a>
      </Flex>
    </Box>
  );
});

export default Navbar;
