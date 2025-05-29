import React, { useEffect, useState } from "react";
import Cookies from "js-cookie";
import PostCard from "./PostCard";
import { Link } from "react-router-dom";

function GetUserAllPost() {
  const userName = localStorage.getItem("userName");
  const [posts, setPosts] = useState([]);
  const token = Cookies.get("token");

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/post/user/${userName}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          console.log("current user post " + data);

          setPosts(data);
        } else {
          const errMsg = await response.text();
          console.error("Failed to fetch posts:", errMsg);
        }
      } catch (error) {
        console.error("Error fetching user posts:", error);
      }
    };

    fetchPosts();
  }, [userName, token]);

  return (
    <div className="container my-5">
      <h2 className="mb-4">Posts by @{userName}</h2>
      {posts.length === 0 ? (
        <h3>
          {" "}
          <Link to={"/addpost"}> Upload Your First Post. </Link>
        </h3>
      ) : (
        <div className="d-flex flex-wrap justify-content-start gap-4">
          {posts.map((post) => (
            <PostCard
              key={post.postId}
              post={post}
              currentUser={userName}
              showActions={true}
            />
          ))}
        </div>
      )}
    </div>
  );
}

export default GetUserAllPost;
