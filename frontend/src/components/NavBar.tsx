import React from "react";
import { userAPI } from "../services/UserService";
import { NavLink, useNavigate } from "react-router-dom";

const NavBar = () => {
  const { data: user, error, isLoading, refetch } = userAPI.useFetchUserQuery();
  const navigate = useNavigate();

  return (
    <>
      <nav
        className="navbar navbar-expand-md fixed-top bg-light"
        data-bs-theme="light"
        style={{ marginBottom: "250px" }}
      >
        <div className="container">
          <NavLink to="/" end className="navbar-brand">
            <img
              src={logo}
              alt="Logo"
              width="40"
              height="40"
              style={{ padding: "0", margin: "0", borderRadius: "0.370rem" }}
            />
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarTogglerDemo02"
            aria-controls="navbarTogglerDemo02"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item"></li>
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
};

export default NavBar;
