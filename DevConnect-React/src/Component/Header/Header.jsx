import React, { useState } from "react";
import { Link } from "react-router-dom";
import { FaUserCircle, FaSun, FaMoon } from "react-icons/fa";

function Header() {
  const [darkMode, setDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setDarkMode((prev) => !prev);
    // Optionally toggle a CSS class on body or root here to change theme globally
    document.body.classList.toggle("dark-mode", !darkMode);
  };

  return (
    <nav className={`navbar navbar-expand-md navbar-light navbar-faint-blue`}>
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
          <button className="btn btn-outline-primary me-2">Sign Up</button>

          <button className="btn btn-primary d-flex align-items-center">
            <FaUserCircle className="me-2" />
            Login
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Header;
