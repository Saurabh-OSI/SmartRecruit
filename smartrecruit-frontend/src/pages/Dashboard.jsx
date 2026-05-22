import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function Dashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    setError("");

    try {
      const response = await api.get("/dashboard/summary");
      setDashboard(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load dashboard");
    }
  };

  if (error) {
    return (
      <div className="page dashboard-page">
        <p className="error">{error}</p>
      </div>
    );
  }

  if (!dashboard) {
    return (
      <div className="page dashboard-page">
        <p>Loading dashboard...</p>
      </div>
    );
  }

  const totalApplications = dashboard.totalApplications || 0;

  const getPercent = (value) => {
    if (!totalApplications || totalApplications === 0) {
      return 0;
    }

    return Math.round((value / totalApplications) * 100);
  };

  return (
    <div className="page dashboard-page">
      <div className="dashboard-hero">
        <div>
          <p className="small-label">Recruitment Overview</p>
          <h1>Recruitment Dashboard</h1>
          <p>
            Monitor jobs, candidate applications, interviews, and hiring progress from one centralized workspace.
          </p>
        </div>

        <div className="hero-badge">
          <span>HR Panel</span>
          <small>Live Summary</small>
        </div>
      </div>

      <div className="dashboard-overview-bar">
        <div>
          <span>Total Jobs</span>
          <strong>{dashboard.totalJobs}</strong>
        </div>

        <div>
          <span>Active Jobs</span>
          <strong>{dashboard.activeJobs}</strong>
        </div>

        <div>
          <span>Total Applications</span>
          <strong>{dashboard.totalApplications}</strong>
        </div>

        <div>
          <span>Total Candidates</span>
          <strong>{dashboard.totalCandidates}</strong>
        </div>
      </div>

      <div className="dashboard-main-layout">
        <div className="dashboard-panel">
          <div className="panel-header">
            <div>
              <h2>Application Pipeline</h2>
              <p>Current distribution of applications across hiring stages.</p>
            </div>
          </div>

          <div className="pipeline-table">
            <div className="pipeline-row">
              <div>
                <strong>Applied</strong>
                <span>New applications submitted by candidates</span>
              </div>

              <div className="pipeline-value">
                <b>{dashboard.appliedApplications}</b>
                <small>{getPercent(dashboard.appliedApplications)}%</small>
              </div>
            </div>

            <div className="pipeline-row">
              <div>
                <strong>Shortlisted</strong>
                <span>Candidates selected for next round</span>
              </div>

              <div className="pipeline-value">
                <b>{dashboard.shortlistedApplications}</b>
                <small>{getPercent(dashboard.shortlistedApplications)}%</small>
              </div>
            </div>

            <div className="pipeline-row">
              <div>
                <strong>Interview</strong>
                <span>Applications moved to interview stage</span>
              </div>

              <div className="pipeline-value">
                <b>{dashboard.interviewApplications}</b>
                <small>{getPercent(dashboard.interviewApplications)}%</small>
              </div>
            </div>

            <div className="pipeline-row">
              <div>
                <strong>Selected</strong>
                <span>Candidates successfully selected</span>
              </div>

              <div className="pipeline-value selected">
                <b>{dashboard.selectedApplications}</b>
                <small>{getPercent(dashboard.selectedApplications)}%</small>
              </div>
            </div>

            <div className="pipeline-row">
              <div>
                <strong>Rejected</strong>
                <span>Applications rejected during screening</span>
              </div>

              <div className="pipeline-value rejected">
                <b>{dashboard.rejectedApplications}</b>
                <small>{getPercent(dashboard.rejectedApplications)}%</small>
              </div>
            </div>
          </div>
        </div>

        <div className="dashboard-side-panel">
          <div className="dashboard-panel">
            <div className="panel-header">
              <div>
                <h2>Interview Summary</h2>
                <p>Interview scheduling and completion status.</p>
              </div>
            </div>

            <div className="interview-summary-list">
              <div>
                <span>Total Interviews</span>
                <strong>{dashboard.totalInterviews}</strong>
              </div>

              <div>
                <span>Scheduled</span>
                <strong>{dashboard.scheduledInterviews}</strong>
              </div>

              <div>
                <span>Completed</span>
                <strong>{dashboard.completedInterviews}</strong>
              </div>

              <div>
                <span>Cancelled</span>
                <strong>{dashboard.cancelledInterviews}</strong>
              </div>
            </div>
          </div>

          <div className="dashboard-panel">
            <div className="panel-header">
              <div>
                <h2>Hiring Health</h2>
                <p>Quick operational view of recruitment progress.</p>
              </div>
            </div>

            <div className="health-list">
              <div>
                <span>Open Job Ratio</span>
                <strong>
                  {dashboard.totalJobs
                    ? `${Math.round((dashboard.activeJobs / dashboard.totalJobs) * 100)}%`
                    : "0%"}
                </strong>
              </div>

              <div>
                <span>Selection Rate</span>
                <strong>{getPercent(dashboard.selectedApplications)}%</strong>
              </div>

              <div>
                <span>Interview Conversion</span>
                <strong>{getPercent(dashboard.interviewApplications)}%</strong>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="dashboard-panel">
        <div className="panel-header">
          <div>
            <h2>Recruitment Insights</h2>
            <p>High-level indicators for HR decision making.</p>
          </div>
        </div>

        <div className="insight-row">
          <div>
            <span>Applications per Active Job</span>
            <strong>
              {dashboard.activeJobs
                ? Math.round(dashboard.totalApplications / dashboard.activeJobs)
                : 0}
            </strong>
            <p>Average candidate interest per open role.</p>
          </div>

          <div>
            <span>Pending Review</span>
            <strong>{dashboard.appliedApplications}</strong>
            <p>Applications waiting for HR action.</p>
          </div>

          <div>
            <span>Finalized Applications</span>
            <strong>
              {(dashboard.selectedApplications || 0) + (dashboard.rejectedApplications || 0)}
            </strong>
            <p>Applications where decision has been completed.</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;