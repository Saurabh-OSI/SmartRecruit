import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  const isHRorAdmin = user?.role === "HR" || user?.role === "ADMIN";
  const isAdmin = user?.role === "ADMIN";
  const isCandidate = user?.role === "CANDIDATE";

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/");
    window.location.reload();
  };

  return (
    <nav className="navbar">
      <h2>SmartRecruit</h2>

      <div className="nav-links">
        {isHRorAdmin && <Link to="/dashboard">Dashboard</Link>}
        {isAdmin && <Link to="/staff">Staff</Link>}

        <Link to="/jobs">Jobs</Link>

        {isCandidate && <Link to="/profile">Profile</Link>}

        <Link to="/applications">Applications</Link>
        <Link to="/interviews">Interviews</Link>
      </div>

      <div className="user-box">
        <span>{user?.name}</span>
        <span className="role">{user?.role}</span>
        <button onClick={logout}>Logout</button>
      </div>
    </nav>
  );
}

export default Navbar;
