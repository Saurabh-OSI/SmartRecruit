import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function Applications() {
  const user = JSON.parse(localStorage.getItem("user"));

  const [applications, setApplications] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  const isHRorAdmin = user?.role === "HR" || user?.role === "ADMIN";

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    setError("");

    try {
      let response;

      if (isHRorAdmin) {
        response = await api.get("/applications");
      } else {
        response = await api.get(`/applications/candidate/${user.userId}`);
      }

      setApplications(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load applications");
    }
  };

  const updateStatus = async (applicationId, status) => {
    setMessage("");
    setError("");

    try {
      await api.put(`/applications/${applicationId}/status`, {
        status: status,
      });

      setMessage("Application status updated successfully");
      fetchApplications();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to update status");
    }
  };

  const filteredApplications =
    statusFilter === "ALL"
      ? applications
      : applications.filter((app) => app.status === statusFilter);

  return (
    <div className="page">
      <div className="page-hero">
        <div>
          <p className="small-label">
            {isHRorAdmin ? "Recruitment Review" : "My Applications"}
          </p>
          <h1>Applications</h1>
          <p>
            {isHRorAdmin
              ? "Review candidate applications and move them through the hiring pipeline."
              : "Track your submitted applications and current selection status."}
          </p>
        </div>

        <div className="hero-count-box">
          <span>{applications.length}</span>
          <p>Total Applications</p>
        </div>
      </div>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <div className="section-card">
        <div className="toolbar-card">
          <div>
            <h2>{isHRorAdmin ? "All Applications" : "My Applications"}</h2>
            <p>Filter and review applications by status.</p>
          </div>

          <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
            <option value="ALL">All Status</option>
            <option value="APPLIED">Applied</option>
            <option value="SHORTLISTED">Shortlisted</option>
            <option value="INTERVIEW">Interview</option>
            <option value="SELECTED">Selected</option>
            <option value="REJECTED">Rejected</option>
          </select>
        </div>

        {filteredApplications.length === 0 ? (
          <div className="empty-state">
            <p>No applications found for this filter.</p>
          </div>
        ) : (
          <div className="table-wrapper">
            <table className="pretty-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Candidate</th>
                  <th>Job</th>
                  <th>Company</th>
                  <th>Location</th>
                  <th>Status</th>
                  <th>Applied At</th>
                  {isHRorAdmin && <th>Action</th>}
                </tr>
              </thead>

              <tbody>
                {filteredApplications.map((app) => (
                  <tr key={app.id}>
                    <td>#{app.id}</td>
                    <td>
                      <strong>{app.candidateName}</strong>
                      <br />
                      <small>{app.candidateEmail}</small>
                    </td>
                    <td>{app.jobTitle}</td>
                    <td>{app.companyName}</td>
                    <td>{app.location}</td>
                    <td>
                      <span className={`status-badge ${app.status?.toLowerCase()}`}>
                        {app.status}
                      </span>
                    </td>
                    <td>
                      {app.appliedAt
                        ? new Date(app.appliedAt).toLocaleString()
                        : "N/A"}
                    </td>

                    {isHRorAdmin && (
                      <td>
                        <select
                          value={app.status}
                          onChange={(e) => updateStatus(app.id, e.target.value)}
                        >
                          <option value="APPLIED">APPLIED</option>
                          <option value="SHORTLISTED">SHORTLISTED</option>
                          <option value="INTERVIEW">INTERVIEW</option>
                          <option value="SELECTED">SELECTED</option>
                          <option value="REJECTED">REJECTED</option>
                        </select>
                      </td>
                    )}
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

export default Applications;