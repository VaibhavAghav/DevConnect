import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // assuming you're using react-router

function Register() {
  const [user, setUser] = useState({
    userName: "",
    userEmail: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const handleRegisterForm = async (event) => {
    event.preventDefault();
    console.log(user);

    try {
      const resaponseSavedData = await fetch(
        "http://localhost:8080/auth/register",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(user),
        }
      );
      if (resaponseSavedData.ok) {
        const responseData = await resaponseSavedData.json();
        console.log("Data saved ", responseData);
        navigate("/login");
      } else {
        console.log("Failed to saved data");
      }
    } catch (error) {
      console.error("Error in submitting form for registration");
    }

    setUser({
      userName: "",
      userEmail: "",
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

          <h2 className="text-center mb-4">Register</h2>
          <form
            onSubmit={handleRegisterForm}
            className="border p-4 rounded shadow-sm"
          >
            <div className="mb-3">
              <label htmlFor="userName" className="form-label">
                User Name
              </label>
              <input
                type="text"
                name="userName"
                id="userName"
                className="form-control form-control-sm"
                value={user.userName}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="userEmail" className="form-label">
                Email
              </label>
              <input
                type="email"
                name="userEmail"
                id="userEmail"
                className="form-control form-control-sm"
                value={user.userEmail}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                type="password"
                name="password"
                id="password"
                className="form-control form-control-sm"
                value={user.password}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100 btn-sm">
              Register
            </button>

            <div className="text-center mt-3">
              <p className="mb-0">
                Already a user? <Link to="/login">Login</Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
