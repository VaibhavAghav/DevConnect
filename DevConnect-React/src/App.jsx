import { Route, Routes } from "react-router-dom";
import "./App.css";
import Footer from "./Component/Footer/Footer";
import Header from "./Component/Header/Header";
import HomePage from "./Component/HomePage/HomePage";
import AboutUs from "./Component/HomePage/AboutUs";
import Contact from "./Component/HomePage/Contact";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
