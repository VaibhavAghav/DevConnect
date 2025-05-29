import React, { useState, useEffect } from "react";
import { Heart, MessageCircle, Edit, Trash, X } from "lucide-react";
import Cookies from "js-cookie";

const PostCard = ({ post, currentUser, showActions }) => {
  const token = Cookies.get("token");
  const userName = localStorage.getItem("userName");
  const [liked, setLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(post.likedByProfiles.length);

  const [comments, setComments] = useState([]);
  const [visibleComments, setVisibleComments] = useState(2);
  const [newComment, setNewComment] = useState("");

  const [isEditing, setIsEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(post.postTitle);
  const [editContent, setEditContent] = useState(post.postContent);

  // Like status fetch
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
        setLiked(text ? JSON.parse(text) : false);
      } catch (error) {
        console.error("Error fetching like status", error);
      }
    };

    fetchLikeStatus();
  }, [post.postId, currentUser, token]);

  // Fetch comments
  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/comment/post/${post.postId}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const data = await response.json();
        console.log(data);

        setComments(data);
      } catch (error) {
        console.error("Error fetching comments", error);
      }
    };

    fetchComments();
  }, [post.postId, token]);

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
        method,
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

  const handleDeletePost = async () => {
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

  const handleAddComment = async () => {
    if (!newComment.trim()) return;
    try {
      const commentData = {
        content: newComment,
        user: { userName: userName },
        post: { postId: post.postId },
      };

      const response = await fetch(`http://localhost:8080/comment/add`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(commentData),
      });

      if (response.ok) {
        const newCommentData = await response.json();
        setComments((prev) => [newCommentData, ...prev]);
        setNewComment("");
      } else {
        alert("Failed to add comment");
      }
    } catch (error) {
      console.error("Error adding comment", error);
    }
  };

  const handleDeleteComment = async (commentId) => {
    if (window.confirm("Delete this comment?")) {
      try {
        const response = await fetch(
          `http://localhost:8080/comment/delete/${commentId}`,
          {
            method: "DELETE",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.ok) {
          setComments((prev) => prev.filter((c) => c.commentId !== commentId));
        } else {
          alert("Failed to delete comment");
        }
      } catch (error) {
        console.error("Error deleting comment", error);
      }
    }
  };

  const handleShowMore = () => {
    setVisibleComments((prev) => prev + 2);
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
              onClick={handleDeletePost}
            >
              <Trash size={16} className="me-1" /> Delete
            </button>
          </div>
        )}

        {/* Comments Section */}
        <div className="mt-3">
          <h6 className="mb-2">Comments:</h6>
          {comments.slice(0, visibleComments).map((comment) => (
            <div
              key={comment.commentId}
              className="mb-2 d-flex justify-content-between"
            >
              <div>
                <strong>{comment.user.username}:</strong>{" "}
                <span>{comment.content}</span>
              </div>
              {comment.user.username === userName && (
                <button
                  className="btn btn-sm btn-link text-danger p-0"
                  onClick={() => handleDeleteComment(comment.commentId)}
                >
                  <Trash size={14} />
                </button>
              )}
            </div>
          ))}

          {visibleComments < comments.length && (
            <button
              className="btn btn-sm btn-link text-primary p-0"
              onClick={handleShowMore}
            >
              Show more
            </button>
          )}

          {/* Add Comment */}
          <div className="d-flex mt-2">
            <input
              type="text"
              className="form-control form-control-sm me-2"
              placeholder="Add a comment..."
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
            />
            <button
              className="btn btn-sm btn-primary"
              onClick={handleAddComment}
            >
              Post
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PostCard;
