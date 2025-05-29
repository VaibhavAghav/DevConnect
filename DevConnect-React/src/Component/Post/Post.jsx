import React, { useEffect, useState } from "react";
import Cookies from "js-cookie";
import PostCard from "./PostCard";

function Post() {
  const [posts, setPosts] = useState([]);
  const token = Cookies.get("token");
  const userName = localStorage.getItem("userName");
  useEffect(() => {
    const fetchPosts = async () => {
      const response = await fetch("http://localhost:8080/post", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.ok) {
        const allpost = await response.json();
        console.log(allpost);
        
        setPosts(allpost);
      }
    };
    fetchPosts();
  }, [token]);

  return (
    <div className="container py-5">
      <h1 className="text-center mb-5">All Posts</h1>
      <div className="d-flex flex-wrap justify-content-center gap-4">
        {posts.length === 0 ? (
          <p className="text-center text-muted">No posts available</p>
        ) : (
          posts.map((post) => (
            <PostCard
              key={post.postId}
              post={post}
              currentUser={userName}
              showActions={false}
            />
          ))
        )}
      </div>
    </div>
  );
}

export default Post;
