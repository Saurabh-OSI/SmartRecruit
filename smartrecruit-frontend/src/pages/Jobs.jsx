import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function Jobs() {
  const user = JSON.parse(localStorage.getItem("user"));

  const [jobs, setJobs] = useState([]);
  const [appliedJobIds, setAppliedJobIds] = useState([]);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [searchText, setSearchText] = useState("");

  const [jobForm, setJobForm] = useState({
    title: "",
    companyName: "",
    location: "",
    jobType: "",
    experienceLevel: "",
    salary: "",
    description: "",
    requiredSkills: "",
  });

  const canCreateJob = user?.role === "HR" || user?.role === "ADMIN";
  const canApply = user?.role === "CANDIDATE";

  useEffect(() => {
    fetchJobs();

    if (user?.role === "CANDIDATE") {
      fetchAppliedJobs();
    }
  }, []);

  const fetchJobs = async () => {
    setError("");

    try {
      const response = await api.get("/jobs");
      setJobs(response.data);
    } catch (err) {
      setError(err.response?.data?.message || "Unable to load jobs");
    }
  };

  const fetchAppliedJobs = async () => {
    try {
      const response = await api.get(`/applications/candidate/${user.userId}`);

      const ids = response.data.map((application) => application.jobId);

      setAppliedJobIds(ids);
    } catch (err) {
      console.log("Unable to load applied jobs", err);
    }
  };

  const handleChange = (e) => {
    setJobForm({
      ...jobForm,
      [e.target.name]: e.target.value,
    });
  };

  const createJob = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      const requestBody = {
        ...jobForm,
        salary: jobForm.salary ? Number(jobForm.salary) : null,
      };

      await api.post("/jobs", requestBody);

      setMessage("Job created successfully");

      setJobForm({
        title: "",
        companyName: "",
        location: "",
        jobType: "",
        experienceLevel: "",
        salary: "",
        description: "",
        requiredSkills: "",
      });

      fetchJobs();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to create job");
    }
  };

  const applyForJob = async (jobId) => {
    setMessage("");
    setError("");

    try {
      await api.post("/applications/apply", {
        candidateId: user.userId,
        jobId: jobId,
        coverLetter:
          "I am interested in this role and would like to apply for this opportunity.",
      });

      setAppliedJobIds([...appliedJobIds, jobId]);
      setMessage("Applied successfully");
    } catch (err) {
      setError(err.response?.data?.message || "Unable to apply for this job");
    }
  };

  const closeJob = async (jobId) => {
    setMessage("");
    setError("");

    try {
      await api.put(`/jobs/${jobId}/close`);
      setMessage("Job closed successfully");
      fetchJobs();
    } catch (err) {
      setError(err.response?.data?.message || "Unable to close job");
    }
  };

  const filteredJobs = jobs.filter((job) => {
    const search = searchText.toLowerCase();

    return (
      job.title?.toLowerCase().includes(search) ||
      job.companyName?.toLowerCase().includes(search) ||
      job.location?.toLowerCase().includes(search) ||
      job.requiredSkills?.toLowerCase().includes(search)
    );
  });

  const activeJobs = filteredJobs.filter((job) => job.active);
  const closedJobs = filteredJobs.filter((job) => !job.active);

  return (
    <div className="page jobs-page">
      {canApply && (
        <div className="candidate-jobs-hero">
          <div>
            <p className="small-label">Find Your Next Opportunity</p>
            <h1>Explore Java & Tech Jobs</h1>
            <p>
              Browse active openings, match your skills, and apply directly from
              your candidate panel.
            </p>
          </div>

          <div className="candidate-job-summary">
            <span>{activeJobs.length}</span>
            <p>Active Jobs</p>
          </div>
        </div>
      )}

      {canCreateJob && (
  <div className="hr-jobs-hero">
    <div>
      <p className="small-label">Job Management</p>
      <h1>Manage Recruitment Openings</h1>
      <p>
        Create job posts, monitor active openings, close completed roles, and manage recruitment opportunities.
      </p>
    </div>

    <div className="hr-job-stats">
      <div>
        <span>{jobs.length}</span>
        <p>Total Jobs</p>
      </div>

      <div>
        <span>{activeJobs.length}</span>
        <p>Active</p>
      </div>

      <div>
        <span>{closedJobs.length}</span>
        <p>Closed</p>
      </div>
    </div>
  </div>
)}

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      {canCreateJob && (
        <div className="section-card">
          <h2>Create New Job</h2>

          <form className="form-grid" onSubmit={createJob}>
            <input
              type="text"
              name="title"
              placeholder="Job Title"
              value={jobForm.title}
              onChange={handleChange}
              required
            />

            <input
              type="text"
              name="companyName"
              placeholder="Company Name"
              value={jobForm.companyName}
              onChange={handleChange}
              required
            />

            <input
              type="text"
              name="location"
              placeholder="Location"
              value={jobForm.location}
              onChange={handleChange}
              required
            />

            <input
              type="text"
              name="jobType"
              placeholder="Job Type"
              value={jobForm.jobType}
              onChange={handleChange}
            />

            <input
              type="text"
              name="experienceLevel"
              placeholder="Experience Level"
              value={jobForm.experienceLevel}
              onChange={handleChange}
            />

            <input
              type="number"
              name="salary"
              placeholder="Salary"
              value={jobForm.salary}
              onChange={handleChange}
            />

            <textarea
              name="description"
              placeholder="Job Description"
              value={jobForm.description}
              onChange={handleChange}
              required
            />

            <textarea
              name="requiredSkills"
              placeholder="Required Skills"
              value={jobForm.requiredSkills}
              onChange={handleChange}
            />

            <button type="submit">Create Job</button>
          </form>
        </div>
      )}

      <div className="section-card">
        <div className="jobs-toolbar">
          <div>
            <h2>{canApply ? "Recommended Jobs" : "Available Jobs"}</h2>
            <p>
              Showing {activeJobs.length} active job
              {activeJobs.length !== 1 ? "s" : ""}
            </p>
          </div>

          <input
            type="text"
            placeholder="Search by title, company, location, skills..."
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
          />
        </div>

        {activeJobs.length === 0 ? (
          <p>No active jobs found.</p>
        ) : (
          <div className={canApply ? "candidate-job-grid" : "job-list"}>
            {activeJobs.map((job) => (
              <div
                className={canApply ? "candidate-job-card" : "job-card"}
                key={job.id}
              >
                <div className="job-header">
                  <div>
                    <h3>{job.title}</h3>
                    <p>
                      {job.companyName} • {job.location}
                    </p>
                  </div>

                  <span className="badge active">Active</span>
                </div>

                {canApply && (
                  <div className="candidate-job-tags">
                    <span>{job.jobType || "Full Time"}</span>
                    <span>{job.experienceLevel || "Fresher"}</span>
                    <span>{job.salary ? `₹${job.salary}` : "Salary N/A"}</span>
                  </div>
                )}

                <p className="job-description">{job.description}</p>

                <p className="skills-line">
                  <strong>Skills:</strong> {job.requiredSkills || "N/A"}
                </p>

                {!canApply && (
                  <div className="job-meta">
                    <span>Type: {job.jobType || "N/A"}</span>
                    <span>Experience: {job.experienceLevel || "N/A"}</span>
                    <span>Salary: {job.salary || "N/A"}</span>
                  </div>
                )}

                <div className="action-row">
                  {canApply &&
                    (appliedJobIds.includes(job.id) ? (
                      <button className="applied-btn" disabled>
                        Applied
                      </button>
                    ) : (
                      <button
                        className="apply-btn"
                        onClick={() => applyForJob(job.id)}
                      >
                        Apply Now
                      </button>
                    ))}

                  {canCreateJob && (
                    <button
                      className="secondary-btn"
                      onClick={() => closeJob(job.id)}
                    >
                      Close Job
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {canCreateJob && closedJobs.length > 0 && (
        <div className="section-card">
          <h2>Closed Jobs</h2>

          <div className="job-list">
            {closedJobs.map((job) => (
              <div className="job-card" key={job.id}>
                <div className="job-header">
                  <div>
                    <h3>{job.title}</h3>
                    <p>
                      {job.companyName} • {job.location}
                    </p>
                  </div>

                  <span className="badge closed">Closed</span>
                </div>

                <p>{job.description}</p>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default Jobs;