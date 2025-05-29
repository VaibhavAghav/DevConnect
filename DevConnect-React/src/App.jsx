import { Route, Routes } from "react-router-dom";
import "./App.css";
import Footer from "./Component/Footer/Footer";
import Header from "./Component/Header/Header";
import HomePage from "./Component/HomePage/HomePage";
import AboutUs from "./Component/HomePage/AboutUs";
import Contact from "./Component/HomePage/Contact";
import Register from "./Component/Signing/Register";
import Login from "./Component/Signing/Login";
import Post from "./Component/Post/Post";
import PrivateRoute from "./Component/Signing/PrivateRoute";
import Profile from "./Component/Profile/Profile";
import UpdateProfile from "./Component/Profile/UpdateProfile";
import AddPost from "./Component/Post/AddPost";
import GetUserAllPost from "./Component/Post/GetUserAllPost";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/posts"
          element={
            <PrivateRoute>
              <Post />
            </PrivateRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <UpdateProfile />
            </PrivateRoute>
          }
        />
        <Route
          path="/addpost"
          element={
            <PrivateRoute>
              <AddPost />
            </PrivateRoute>
          }
        />
        <Route
          path="/getallpost"
          element={
            <PrivateRoute>
              <GetUserAllPost />
            </PrivateRoute>
          }
        />
      </Routes>

      <Footer />
    </>
  );
}

export default App;
