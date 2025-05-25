import React from "react";

function HomePage() {
  return (
    <div className="container my-5">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold">Welcome to DevConnect</h1>
        <p className="lead text-secondary">
          A community platform where developers connect, collaborate, and grow
          together. Share your work, find talent, and join forces on awesome
          projects.
        </p>
        <a href="/posts" className="btn btn-primary btn-lg mt-3">
          Explore Posts
        </a>
      </div>

      <div className="row text-center">
        <div className="col-md-4 mb-4">
          <div className="p-4 border rounded shadow-sm h-100">
            <h3>Discover Developers</h3>
            <p>
              Find and connect with talented developers from around the world.
            </p>
          </div>
        </div>
        <div className="col-md-4 mb-4">
          <div className="p-4 border rounded shadow-sm h-100">
            <h3>Share Your Projects</h3>
            <p>
              Showcase your work and collaborate on open-source and private
              projects.
            </p>
          </div>
        </div>
        <div className="col-md-4 mb-4">
          <div className="p-4 border rounded shadow-sm h-100">
            <h3>Join the Community</h3>
            <p>
              Engage in discussions, ask questions, and learn from fellow
              developers.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomePage;
