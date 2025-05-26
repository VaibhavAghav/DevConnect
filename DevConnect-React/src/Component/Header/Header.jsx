import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUserCircle, FaSun, FaMoon } from "react-icons/fa";
import Cookies from "js-cookie";

function Header() {
  const [darkMode, setDarkMode] = useState(false);
  const [userName, setUserName] = useState(null);
  const navigate = useNavigate();

  const toggleDarkMode = () => {
    setDarkMode((prev) => {
      document.body.classList.toggle("dark-mode", !prev);
      return !prev;
    });
  };

  useEffect(() => {
    const token = Cookies.get("token");
    const user = localStorage.getItem("userName");
    if (token && user) {
      setUserName(user);
    }
  }, []);

  const handleLogout = () => {
    Cookies.remove("token");
    localStorage.removeItem("userName");
    setUserName(null);
    navigate("/");
    window.location.reload();
  };

  return (
    <nav className="navbar navbar-expand-md navbar-light navbar-faint-blue">
      <div className="container">
        <Link className="navbar-brand" to="/">
          DevConnect
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>

        <div
          className="collapse navbar-collapse justify-content-center"
          id="navbarNav"
        >
          <ul className="navbar-nav">
            <li className="nav-item mx-3">
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item mx-3">
              <Link className="nav-link" to="/posts">
                Posts
              </Link>
            </li>
            <li className="nav-item mx-3">
              <Link className="nav-link" to="/about">
                About
              </Link>
            </li>
            <li className="nav-item mx-3">
              <Link className="nav-link" to="/contact">
                Contact
              </Link>
            </li>
          </ul>
        </div>

        <button
          className="btn btn-outline-secondary me-2 d-flex align-items-center"
          onClick={toggleDarkMode}
          aria-label="Toggle dark/light mode"
          title="Toggle dark/light mode"
        >
          {darkMode ? <FaSun /> : <FaMoon />}
        </button>

        <div className="d-flex align-items-center">
          {userName ? (
            <>
              <span className="btn btn-outline-primary btn-sm me-2 d-flex align-items-center">
                <FaUserCircle className="me-1" /> {userName}
              </span>
              <button
                onClick={handleLogout}
                className="btn btn-outline-danger btn-sm"
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link
                to="/login"
                className="btn btn-primary d-flex align-items-center me-2"
              >
                <FaUserCircle className="me-2" /> Login
              </Link>
              <Link to="/register" className="btn btn-outline-primary">
                Sign Up
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Header;
