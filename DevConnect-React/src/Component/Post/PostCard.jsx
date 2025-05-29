import React, { useState, useEffect } from "react";
import { Heart, MessageCircle, Edit, Trash, X } from "lucide-react";
import Cookies from "js-cookie";

const PostCard = ({ post, currentUser, showActions }) => {
  const token = Cookies.get("token");
  const userName = localStorage.getItem("userName");
  const [liked, setLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(post.likedByProfiles.length);

  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(post.postTitle);
  const [editContent, setEditContent] = useState(post.postContent);

  useEffect(() => {
    const fetchLikeStatus = async () => {
      if (!currentUser) return;
      try {
        const response = await fetch(
          `http://localhost:8080/post/isLiked/${post.postId}/${currentUser}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const text = await response.text();
        console.log("current user ", text);

        setLiked(text ? JSON.parse(text) : false);
      } catch (error) {
        console.error("Error fetching like status", error);
      }
    };

    fetchLikeStatus();
  }, [post.postId, currentUser, token]);

  const handleLikeToggle = async () => {
    if (!currentUser) {
      alert("Please log in to like posts.");
      return;
    }
    try {
      const url = liked
        ? `http://localhost:8080/post/unlike/${post.postId}/${currentUser}`
        : `http://localhost:8080/post/like/${post.postId}/${currentUser}`;
      const method = liked ? "DELETE" : "POST";

      const response = await fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        setLiked(!liked);
        setLikeCount((prev) => (liked ? prev - 1 : prev + 1));
      } else {
        console.error("Failed to toggle like");
      }
    } catch (error) {
      console.error("Error toggling like", error);
    }
  };

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to delete this post?")) {
      try {
        const response = await fetch(
          `http://localhost:8080/post/delete/${post.postId}`,
          {
            method: "DELETE",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (response.ok) {
          alert("Post deleted successfully");
          window.location.reload();
        } else {
          alert("Failed to delete post");
        }
      } catch (error) {
        console.error("Error deleting post", error);
      }
    }
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    setEditTitle(post.postTitle);
    setEditContent(post.postContent);
  };

  const handleSaveEdit = async () => {
    try {
      const formData = new FormData();
      formData.append("postTitle", editTitle);
      formData.append("postContent", editContent);
      formData.append("userName", userName);
      const response = await fetch(
        `http://localhost:8080/post/update/${post.postId}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
          },
          body: formData,
        }
      );

      if (response.ok) {
        alert("Post updated successfully");
        window.location.reload();
      } else {
        const errorMsg = await response.text();
        alert("Failed to update post: " + errorMsg);
      }
    } catch (error) {
      console.error("Error updating post", error);
    }
  };

  return (
    <div className="card my-4 shadow-sm" style={{ width: "350px" }}>
      <div style={{ width: "100%", paddingTop: "100%", position: "relative" }}>
        <img
          src={post.publicPostUrl}
          className="card-img-top"
          alt={post.postTitle}
          style={{
            position: "absolute",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            objectFit: "cover",
          }}
        />
      </div>

      <div className="card-body">
        <p className="card-text text-muted mb-2">{likeCount} likes</p>

        <div className="d-flex mb-3">
          <button
            className="btn btn-link text-primary d-flex align-items-center me-3 p-0 text-decoration-none"
            onClick={handleLikeToggle}
          >
            <Heart
              size={18}
              className="me-1"
              fill={liked ? "red" : "none"}
              color={liked ? "red" : "currentColor"}
            />
            {liked ? "Liked" : "Like"}
          </button>
          <button className="btn btn-link text-primary d-flex align-items-center p-0 text-decoration-none">
            <MessageCircle size={18} className="me-1" />
            Comment
          </button>
        </div>

        {isEditing ? (
          <>
            <input
              type="text"
              className="form-control mb-2"
              value={editTitle}
              onChange={(e) => setEditTitle(e.target.value)}
            />
            <textarea
              className="form-control mb-2"
              rows="3"
              value={editContent}
              onChange={(e) => setEditContent(e.target.value)}
            />
            <div className="d-flex justify-content-between">
              <button
                className="btn btn-sm btn-success"
                onClick={handleSaveEdit}
              >
                Save
              </button>
              <button
                className="btn btn-sm btn-secondary"
                onClick={handleCancelEdit}
              >
                <X size={16} /> Cancel
              </button>
            </div>
          </>
        ) : (
          <>
            <h6 className="card-subtitle mb-2 text-dark">
              {post.user.username} - {post.postTitle}
            </h6>
            <p className="card-text text-secondary">{post.postContent}</p>
          </>
        )}

        <div className="d-flex justify-content-between text-muted small mt-3">
          <span>Created: {new Date(post.postCreation).toLocaleString()}</span>
          <span>Updated: {new Date(post.postUpdation).toLocaleString()}</span>
        </div>

        {showActions && !isEditing && (
          <div className="d-flex justify-content-between mt-3">
            <button
              className="btn btn-sm btn-outline-primary d-flex align-items-center"
              onClick={handleEdit}
            >
              <Edit size={16} className="me-1" /> Edit
            </button>
            <button
              className="btn btn-sm btn-outline-danger d-flex align-items-center"
              onClick={handleDelete}
            >
              <Trash size={16} className="me-1" /> Delete
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default PostCard;
