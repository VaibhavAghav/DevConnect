import React, { useState } from "react";
import Cookies from "js-cookie";

function AddPost() {
  const [postTitle, setPostTitle] = useState("");
  const [postContent, setPostContent] = useState("");
  const [postPhoto, setPostPhoto] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const userName = localStorage.getItem("userName");
  const token = Cookies.get("token");

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    setPostPhoto(file);

    // Preview the image
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreviewUrl(reader.result);
      };
      reader.readAsDataURL(file);
    } else {
      setPreviewUrl(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!userName) {
      alert("User not logged in!");
      return;
    }

    const formData = new FormData();
    formData.append("postTitle", postTitle);
    formData.append("postContent", postContent);
    formData.append("postphoto", postPhoto);
    formData.append("userName", userName);

    try {
      const response = await fetch("http://localhost:8080/post/add", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      });

      if (response.ok) {
        const result = await response.json();
        console.log("Post Created: ", result);
        alert("Post created successfully!");
        setPostTitle("");
        setPostContent("");
        setPostPhoto(null);
        setPreviewUrl(null);
      } else {
        const errorText = await response.text();
        console.error("Failed to create post: ", errorText);
        alert("Failed to create post: " + errorText);
      }
    } catch (error) {
      console.error("Error: ", error);
      alert("Error creating post.");
    }
  };

  return (
    <div className="container my-5">
      <div
        className="card shadow-sm p-4 mx-auto"
        style={{ maxWidth: "600px", borderRadius: "1rem" }}
      >
        <h2 className="mb-4 text-center">Create Your Post</h2>
        <form onSubmit={handleSubmit} encType="multipart/form-data">
          <div className="mb-3">
            <label className="form-label">Post Title</label>
            <input
              type="text"
              className="form-control"
              value={postTitle}
              onChange={(e) => setPostTitle(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">Post Content</label>
            <textarea
              className="form-control"
              rows="4"
              value={postContent}
              onChange={(e) => setPostContent(e.target.value)}
              required
            ></textarea>
          </div>

          <div className="mb-3">
            <label className="form-label">Post Image</label>
            <input
              type="file"
              className="form-control"
              accept="image/*"
              onChange={handlePhotoChange}
              required
            />
          </div>

          {/* Image Preview */}
          {previewUrl && (
            <div className="mb-3 text-center">
              <img
                src={previewUrl}
                alt="Post Preview"
                className="img-fluid rounded"
                style={{
                  maxHeight: "300px",
                  objectFit: "cover",
                  border: "1px solid #ddd",
                }}
              />
            </div>
          )}

          <button type="submit" className="btn btn-primary w-100">
            Publish Post
          </button>
        </form>
      </div>
    </div>
  );
}

export default AddPost;
