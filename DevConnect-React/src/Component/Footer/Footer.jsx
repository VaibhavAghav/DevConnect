import React from "react";
import { FaGithub, FaTwitter, FaLinkedin, FaGlobe } from "react-icons/fa";

function Footer() {
  return (
    <footer className="bg-light text-dark py-4 mt-5">
      <div className="container">
        <div className="row">
          {/* About Section */}
          <div className="col-md-4 mb-3">
            <h5>DevConnect</h5>
            <p className="small">
              DevConnect is a community platform where developers connect,
              collaborate, and grow together. Share your work, find talent, and
              join forces on awesome projects.
            </p>
          </div>

          {/* Quick Links */}
          <div className="col-md-4 mb-3">
            <h5>Quick Links</h5>
            <ul className="list-unstyled">
              <li>
                <a href="/about" className="text-dark text-decoration-none">
                  About
                </a>
              </li>
              <li>
                <a href="/posts" className="text-dark text-decoration-none">
                  Posts
                </a>
              </li>
              <li>
                <a
                  href="/developers"
                  className="text-dark text-decoration-none"
                >
                  Developers
                </a>
              </li>
              <li>
                <a href="/contact" className="text-dark text-decoration-none">
                  Contact
                </a>
              </li>
            </ul>
          </div>

          {/* Social Links */}
          <div className="col-md-4 mb-3">
            <h5>Connect With Us</h5>
            <div className="d-flex gap-3 fs-4">
              <a
                href="https://github.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-dark"
              >
                <FaGithub />
              </a>
              <a
                href="https://twitter.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-dark"
              >
                <FaTwitter />
              </a>
              <a
                href="https://linkedin.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-dark"
              >
                <FaLinkedin />
              </a>
              <a
                href="https://devconnect.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="text-dark"
              >
                <FaGlobe />
              </a>
            </div>
          </div>
        </div>

        {/* Footer Bottom */}
        <div className="border-top pt-3 text-center small text-muted">
          &copy; {new Date().getFullYear()} DevConnect. All rights reserved.
        </div>
      </div>
    </footer>
  );
}

export default Footer;
