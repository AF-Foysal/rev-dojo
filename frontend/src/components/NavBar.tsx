import React from "react";
import { userAPI } from "../services/UserService";
import { NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/react.svg";

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
              <li className="nav-item">
                <NavLink
                  to="/documents"
                  end
                  className="nav-link"
                  aria-current="page"
                >
                  Documents
                </NavLink>
              </li>
              {user?.data.user.role !== "USER" && (
                <li className="nav-item">
                  <NavLink to="/users" end className="nav-link">
                    Users
                  </NavLink>
                </li>
              )}
            </ul>
            <div className="flex-shrink-0 dropdown">
              <a
                className="d-block link-body-emphasis text-decoration-none dropdown-toggle profile-dropdown"
                style={{ cursor: "pointer" }}
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <img src="" alt="" />
              </a>
              <ul
                className="dropdown-menu dropdown-menu-end"
                style={{ paddingInline: "10px" }}
              >
                <li>
                  <NavLink
                    to="/user/profile"
                    end
                    className="rounded-2 dropdown-item d-flex gap-2 align-items-center"
                  >
                    <img src="" alt="" />
                    <div className="">
                      <p
                        style={{
                          display: "block",
                          margin: 0,
                          padding: 0,
                          color: "#000 !important",
                        }}
                      >
                        {user?.data.user.firstName}
                      </p>
                      <p
                        style={{
                          display: "block",
                          margin: 0,
                          padding: 0,
                          fontSize: "12px",
                          fontWeight: 600,
                        }}
                      >
                        {user?.data.user.email}
                      </p>
                    </div>
                  </NavLink>
                </li>
                <hr className="dropdown-divider" />
              </ul>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
};

export default NavBar;
