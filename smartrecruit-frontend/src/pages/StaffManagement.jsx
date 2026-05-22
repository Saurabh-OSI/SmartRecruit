import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function StaffManagement() {
  const [staffUsers, setStaffUsers] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    role: "HR",
  });

  useEffect(() => {
    fetchStaffUsers();
  }, []);

  const fetchStaffUsers = async () => {
    setError("");

    try {
      const response = await api.get("/admin/users");
      setStaffUsers(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load staff accounts");
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleCreateStaffUser = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      await api.post("/admin/users", formData);

      setMessage(`${formData.role} account created successfully`);
      setFormData({
        name: "",
        email: "",
        password: "",
        role: "HR",
      });

      fetchStaffUsers();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to create staff account");
    }
  };

  const adminCount = staffUsers.filter((user) => user.role === "ADMIN").length;
  const hrCount = staffUsers.filter((user) => user.role === "HR").length;

  return (
    <div className="page">
      <div className="page-hero">
        <div>
          <p className="small-label">Admin Control</p>
          <h1>Staff Access Management</h1>
          <p>
            Create trusted HR and admin accounts without reopening public role-based
            registration.
          </p>
        </div>

        <div className="hero-count-box">
          <span>{staffUsers.length}</span>
          <p>Trusted Users</p>
        </div>
      </div>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <div className="staff-summary-grid">
        <div className="staff-summary-card">
          <span>{adminCount}</span>
          <p>Admin Accounts</p>
        </div>

        <div className="staff-summary-card">
          <span>{hrCount}</span>
          <p>HR Accounts</p>
        </div>
      </div>

      <div className="section-card">
        <h2>Create Trusted Account</h2>

        <form className="form-grid" onSubmit={handleCreateStaffUser}>
          <input
            type="text"
            name="name"
            placeholder="Full name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <input
            type="email"
            name="email"
            placeholder="Work email"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Temporary password"
            value={formData.password}
            onChange={handleChange}
            minLength={8}
            required
          />

          <select name="role" value={formData.role} onChange={handleChange}>
            <option value="HR">HR</option>
            <option value="ADMIN">ADMIN</option>
          </select>

          <button type="submit">Create Staff Account</button>
        </form>
      </div>

      <div className="section-card">
        <div className="toolbar-card">
          <div>
            <h2>Existing Staff Accounts</h2>
            <p>Current trusted HR and admin users in the system.</p>
          </div>
        </div>

        {staffUsers.length === 0 ? (
          <div className="empty-state">
            <p>No HR or admin accounts found.</p>
          </div>
        ) : (
          <div className="table-wrapper">
            <table className="pretty-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Created</th>
                </tr>
              </thead>
              <tbody>
                {staffUsers.map((user) => (
                  <tr key={user.id}>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                    <td>
                      <span className={`staff-role-chip ${user.role?.toLowerCase()}`}>
                        {user.role}
                      </span>
                    </td>
                    <td>
                      {user.createdAt ? new Date(user.createdAt).toLocaleString() : "N/A"}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default StaffManagement;
