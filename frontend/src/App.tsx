import "./App.css";
import { Flex } from "@chakra-ui/react";
import Navbar from "./components/structure/navbar/navbar.tsx";
import MainContainer from "./components/structure/main-container/main-container.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./components/pages/home/home.tsx";
import Events from "./components/pages/events/events.tsx";
import Institutions from "./components/pages/institutions/institution.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/events",
    element: <Events />,
  },
  {
    path: "/institutions",
    element: <Institutions />,
  },
]);

function App() {
  return (
    <Flex flex={1}>
      <Navbar
        minW={{ base: "150px", md: "20%" }}
        maxW={{ base: "15%", md: "15%" }}
      ></Navbar>
      <MainContainer flex={1}>
        <RouterProvider router={router} />
      </MainContainer>
    </Flex>
  );
}

export default App;
