import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";

function UpdateProfile() {
  const [profile, setProfile] = useState({
    profileId: "",
    profileUserName: "",
    profileBio: "",
    gitHubLink: "",
    profilePhoto: null,
    userId: "",
    publicImage: "",
  });

  const [previewImage, setPreviewImage] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const userName = localStorage.getItem("userName");
        const token = Cookies.get("token");
        console.log("UserName ", userName);
        console.log("Token ", token);

        const response = await fetch(
          `http://localhost:8080/profile/user/${userName}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          console.log("Profile Data ", data);
          setProfile({
            profileId: data.profileId || "",
            profileUserName: data.profileUserName || "",
            profileBio: data.profileBio || "",
            gitHubLink: data.gitHubLink || "",
            profilePhoto: null,
            userId: data.userId || "",
            publicImage: data.publicImage || "",
          });
        } else {
          console.error("Fetch failed:", response.status);
        }
      } catch (error) {
        console.error("Fetch error:", error);
      }
    };

    fetchProfile();
  }, []);

  const handleOnChange = (event) => {
    const { name, value, files } = event.target;
    if (name === "profilePhoto") {
      const file = files[0];
      setProfile({ ...profile, [name]: file });
      if (file) {
        setPreviewImage(URL.createObjectURL(file));
      } else {
        setPreviewImage(null);
      }
    } else {
      setProfile({ ...profile, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Profile submitted:", profile);

    const token = Cookies.get("token");
    const profileId = profile.profileId;

    const formData = new FormData();
    formData.append("profileId", profile.profileId);
    formData.append("profileUserName", profile.profileUserName);
    formData.append("profileBio", profile.profileBio);
    formData.append("gitHubLink", profile.gitHubLink);
    formData.append("userId", profile.userId);

    if (profile.profilePhoto) {
      formData.append("profilePhoto", profile.profilePhoto);
    }

    try {
      const response = await fetch(
        `http://localhost:8080/profile/update/${profileId}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        }
      );

      if (response.ok) {
        console.log("Data saved ", await response.json());
      } else {
        console.log("Data not saved ", await response.text());
      }
    } catch (error) {
      console.error("Error in submitting ", error);
    }
  };

  return (
    <div className="container my-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow p-4">
            <div className="position-relative mb-4">
              <button
                className="btn btn-outline-primary position-absolute start-0"
                style={{ top: "50%", transform: "translateY(-50%)" }}
                onClick={() => navigate("/posts")}
              >
                Posts
              </button>
              <h2
                className="text-center mx-auto"
                style={{ width: "fit-content" }}
              >
                Mark Profile
              </h2>
            </div>

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

              {/* Preview Image */}
              {(previewImage || profile.publicImage) && (
                <div className="mb-3 text-center">
                  <img
                    src={previewImage || profile.publicImage}
                    alt="Profile Preview"
                    className="img-fluid rounded"
                    style={{ maxHeight: "200px" }}
                  />
                </div>
              )}

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

export default UpdateProfile;
