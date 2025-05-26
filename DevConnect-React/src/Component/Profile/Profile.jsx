import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

function Profile() {
  const [profile, setProfile] = useState({
    profileUserName: "",
    profileBio: "",
    gitHubLink: "",
    profilePhoto: null,
    userId: "",
  });

  const location = useLocation();

  useEffect(() => {
    if (location.state?.userId) {
      setProfile((prev) => ({ ...prev, userId: location.state.userId }));
    }
  }, [location]);

  const handleOnChange = (event) => {
    const { name, value, files } = event.target;
    if (name === "profilePhoto") {
      setProfile({ ...profile, [name]: files[0] });
    } else {
      setProfile({ ...profile, [name]: value });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Profile submitted:", profile);
  };

  return (
    <div className="container my-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow p-4">
            <h2 className="text-center mb-4">Make Profile</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="profileUserName" className="form-label">
                  Name
                </label>
                <input
                  name="profileUserName"
                  type="text"
                  className="form-control"
                  value={profile.profileUserName}
                  onChange={handleOnChange}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="profileBio" className="form-label">
                  Bio
                </label>
                <textarea
                  name="profileBio"
                  className="form-control"
                  value={profile.profileBio}
                  onChange={handleOnChange}
                  rows="3"
                />
              </div>

              <div className="mb-3">
                <label htmlFor="gitHubLink" className="form-label">
                  GitHub Link
                </label>
                <input
                  name="gitHubLink"
                  type="url"
                  className="form-control"
                  value={profile.gitHubLink}
                  onChange={handleOnChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="profilePhoto" className="form-label">
                  Profile Photo
                </label>
                <input
                  name="profilePhoto"
                  type="file"
                  className="form-control"
                  onChange={handleOnChange}
                  accept="image/*"
                />
              </div>

              <button type="submit" className="btn btn-primary w-100">
                Save Profile
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
