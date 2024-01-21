import { Box, BoxProps, forwardRef } from "@chakra-ui/react";

const MainContainer = forwardRef<BoxProps, "div">((props, ref) => {
  return <Box bg="gray.800" p={5} ref={ref} {...props}></Box>;
});

export default MainContainer;
