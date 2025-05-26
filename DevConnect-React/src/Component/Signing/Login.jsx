import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

function Login() {
  const [user, setUser] = useState({
    userName: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const handleFormSubmit = async (event) => {
    event.preventDefault();
    console.log(user);

    try {
      const logInUser = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
      });

      const result = await logInUser.json();

      if (logInUser.ok) {
        console.log("Login successful:", result);
        Cookies.set("token", result.token);
        localStorage.setItem("userName", user.userName);
        window.location.reload();
        navigate("/posts");
      } else {
        console.error("Login failed:", result);
        alert(result.message || "Login failed. Please try again.");
      }
    } catch (error) {
      console.error("Error during login:", error);
      alert("Something went wrong.");
    }

    setUser({
      userName: "",
      password: "",
    });
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-4">
          <button
            className="btn btn-outline-secondary btn-sm mb-3"
            onClick={() => navigate(-1)}
          >
            &larr; Back
          </button>

          <h2 className="text-center mb-4">Login</h2>
          <form
            onSubmit={handleFormSubmit}
            className="border p-4 rounded shadow-sm"
          >
            <div className="mb-3">
              <label htmlFor="userName" className="form-label">
                User Name
              </label>
              <input
                name="userName"
                type="text"
                id="userName"
                className="form-control form-control-sm"
                value={user.userName}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                name="password"
                type="password"
                id="password"
                className="form-control form-control-sm"
                value={user.password}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100 btn-sm">
              Login
            </button>

            <div className="text-center mt-3">
              <p className="mb-0">
                New user? <Link to="/register">Register here</Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
