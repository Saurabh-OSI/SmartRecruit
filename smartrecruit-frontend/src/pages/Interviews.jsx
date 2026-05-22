import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function Interviews() {
  const user = JSON.parse(localStorage.getItem("user"));

  const [interviews, setInterviews] = useState([]);
  const [applications, setApplications] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  const [formData, setFormData] = useState({
    applicationId: "",
    interviewDateTime: "",
    interviewMode: "Online",
    meetingLink: "",
    interviewerName: "",
    feedback: "",
  });

  const isHRorAdmin = user?.role === "HR" || user?.role === "ADMIN";

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setError("");

    try {
      if (isHRorAdmin) {
        const interviewResponse = await api.get("/interviews");
        const applicationResponse = await api.get("/applications");

        setInterviews(interviewResponse.data);
        setApplications(applicationResponse.data);
      } else {
        const applicationResponse = await api.get(`/applications/candidate/${user.userId}`);
        const userApplications = applicationResponse.data;

        let allInterviews = [];

        for (const app of userApplications) {
          try {
            const interviewResponse = await api.get(`/interviews/application/${app.id}`);
            allInterviews = [...allInterviews, ...interviewResponse.data];
          } catch {
            // ignore empty interview list
          }
        }

        setInterviews(allInterviews);
      }
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load interviews");
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const scheduleInterview = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      await api.post("/interviews/schedule", formData);

      setMessage("Interview scheduled successfully");

      setFormData({
        applicationId: "",
        interviewDateTime: "",
        interviewMode: "Online",
        meetingLink: "",
        interviewerName: "",
        feedback: "",
      });

      fetchData();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to schedule interview");
    }
  };

  const updateInterviewStatus = async (interviewId, status) => {
    setMessage("");
    setError("");

    try {
      await api.put(`/interviews/${interviewId}/status`, {
        status: status,
        feedback: "Interview status updated from frontend.",
      });

      setMessage("Interview status updated successfully");
      fetchData();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to update interview status");
    }
  };

  const filteredInterviews =
    statusFilter === "ALL"
      ? interviews
      : interviews.filter((interview) => interview.status === statusFilter);

  return (
    <div className="page">
      <div className="page-hero">
        <div>
          <p className="small-label">
            {isHRorAdmin ? "Interview Management" : "My Interview Schedule"}
          </p>
          <h1>Interviews</h1>
          <p>
            {isHRorAdmin
              ? "Schedule interviews, track outcomes, and manage candidate interview status."
              : "View your scheduled interviews and interview status updates."}
          </p>
        </div>

        <div className="hero-count-box">
          <span>{interviews.length}</span>
          <p>Total Interviews</p>
        </div>
      </div>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      {isHRorAdmin && (
        <div className="section-card">
          <h2>Schedule Interview</h2>

          <form className="form-grid" onSubmit={scheduleInterview}>
            <select
              name="applicationId"
              value={formData.applicationId}
              onChange={handleChange}
              required
            >
              <option value="">Select Application</option>

              {applications.map((app) => (
                <option key={app.id} value={app.id}>
                  #{app.id} - {app.candidateName} - {app.jobTitle} ({app.status})
                </option>
              ))}
            </select>

            <input
              type="datetime-local"
              name="interviewDateTime"
              value={formData.interviewDateTime}
              onChange={handleChange}
              required
            />

            <select
              name="interviewMode"
              value={formData.interviewMode}
              onChange={handleChange}
              required
            >
              <option value="Online">Online</option>
              <option value="Offline">Offline</option>
              <option value="Telephonic">Telephonic</option>
            </select>

            <input
              type="text"
              name="meetingLink"
              placeholder="Meeting Link / Location"
              value={formData.meetingLink}
              onChange={handleChange}
            />

            <input
              type="text"
              name="interviewerName"
              placeholder="Interviewer Name"
              value={formData.interviewerName}
              onChange={handleChange}
              required
            />

            <textarea
              name="feedback"
              placeholder="Initial Feedback / Notes"
              value={formData.feedback}
              onChange={handleChange}
            />

            <button type="submit">Schedule Interview</button>
          </form>
        </div>
      )}

      <div className="section-card">
        <div className="toolbar-card">
          <div>
            <h2>{isHRorAdmin ? "All Interviews" : "My Interviews"}</h2>
            <p>Filter and track interview status.</p>
          </div>

          <select value={statusFilter} onChange={(e) => setStatusFilter(e.target.value)}>
            <option value="ALL">All Status</option>
            <option value="SCHEDULED">Scheduled</option>
            <option value="COMPLETED">Completed</option>
            <option value="CANCELLED">Cancelled</option>
          </select>
        </div>

        {filteredInterviews.length === 0 ? (
          <div className="empty-state">
            <p>No interviews found for this filter.</p>
          </div>
        ) : (
          <div className="table-wrapper">
            <table className="pretty-table">
              <thead>
                <tr>
  <th>ID</th>
  <th>Candidate</th>
  <th>Job</th>
  <th>Date & Time</th>
  <th>Mode</th>
  <th>Meeting Link / Location</th>
  <th>Interviewer</th>
  <th>Status</th>
  <th>Feedback</th>
  {isHRorAdmin && <th>Action</th>}
</tr>
              </thead>

              <tbody>
                {filteredInterviews.map((interview) => (
                  <tr key={interview.id}>
                    <td>#{interview.id}</td>
                    <td>
                      <strong>{interview.candidateName}</strong>
                      <br />
                      <small>{interview.candidateEmail}</small>
                    </td>
                    <td>
                      {interview.jobTitle}
                      <br />
                      <small>{interview.companyName}</small>
                    </td>
                    <td>
                      {interview.interviewDateTime
                        ? new Date(interview.interviewDateTime).toLocaleString()
                        : "N/A"}
                    </td>
                    <td>{interview.interviewMode}</td>

<td>
  {interview.meetingLink ? (
    interview.meetingLink.startsWith("http") ? (
      <a
        className="meeting-link"
        href={interview.meetingLink}
        target="_blank"
        rel="noreferrer"
      >
        Join Meeting
      </a>
    ) : (
      <span>{interview.meetingLink}</span>
    )
  ) : (
    "N/A"
  )}
</td>

<td>{interview.interviewerName}</td>

<td>
  <span className={`status-badge ${interview.status?.toLowerCase()}`}>
    {interview.status}
  </span>
</td>
                    <td>{interview.feedback || "N/A"}</td>

                    {isHRorAdmin && (
                      <td>
                        <select
                          value={interview.status}
                          onChange={(e) =>
                            updateInterviewStatus(interview.id, e.target.value)
                          }
                        >
                          <option value="SCHEDULED">SCHEDULED</option>
                          <option value="COMPLETED">COMPLETED</option>
                          <option value="CANCELLED">CANCELLED</option>
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

export default Interviews;