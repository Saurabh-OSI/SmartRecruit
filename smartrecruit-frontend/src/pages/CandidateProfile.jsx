import { useEffect, useState } from "react";
import api from "../api/axiosConfig";

function CandidateProfile() {
  const user = JSON.parse(localStorage.getItem("user"));

  const [profile, setProfile] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const [formData, setFormData] = useState({
    userId: user?.userId || "",
    phone: "",
    education: "",
    experience: "",
    skills: "",
    resumeUrl: "",
    linkedinUrl: "",
    githubUrl: "",
  });

  useEffect(() => {
    if (user?.role === "CANDIDATE") {
      fetchProfile();
    }
  }, []);

  const fetchProfile = async () => {
    setError("");

    try {
      const response = await api.get(`/candidates/user/${user.userId}`);
      const data = response.data;

      setProfile(data);

      setFormData({
        userId: data.userId,
        phone: data.phone || "",
        education: data.education || "",
        experience: data.experience || "",
        skills: data.skills || "",
        resumeUrl: data.resumeUrl || "",
        linkedinUrl: data.linkedinUrl || "",
        githubUrl: data.githubUrl || "",
      });
    } catch (err) {
      setProfile(null);

      if (err.response?.status !== 400 && err.response?.status !== 404) {
        setError(err.response?.data?.message || "Unable to load profile");
      }
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const saveProfile = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      if (profile) {
        const response = await api.put(`/candidates/profile/${profile.id}`, formData);
        setProfile(response.data);
        setMessage("Profile updated successfully");
      } else {
        const response = await api.post("/candidates/profile", formData);
        setProfile(response.data);
        setMessage("Profile created successfully");
      }
    } catch (err) {
      setError(err.response?.data?.message || "Unable to save profile");
    }
  };

  if (user?.role !== "CANDIDATE") {
    return (
      <div className="page">
        <div className="page-hero">
          <div>
            <p className="small-label">Access Restricted</p>
            <h1>Candidate Profile</h1>
            <p>Only candidate users can create or update a candidate profile.</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="page-hero">
        <div>
          <p className="small-label">My Profile</p>
          <h1>Candidate Profile</h1>
          <p>
            Manage your education, skills, experience, and portfolio links for recruitment.
          </p>
        </div>

        <div className="hero-count-box">
          <span>{profile ? "✓" : "!"}</span>
          <p>{profile ? "Profile Ready" : "Incomplete"}</p>
        </div>
      </div>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <div className="profile-card-grid">
        <div className="profile-summary-card">
          <div className="avatar-circle">
            {user?.name?.charAt(0)?.toUpperCase() || "U"}
          </div>

          <h2>{user?.name}</h2>
          <p>{user?.email}</p>

          {profile ? (
            <div className="info-list">
              <div className="info-item">
                <strong>Phone</strong>
                {profile.phone}
              </div>

              <div className="info-item">
                <strong>Education</strong>
                {profile.education}
              </div>

              <div className="info-item">
                <strong>Skills</strong>
                {profile.skills}
              </div>

              <div className="link-row">
                {profile.resumeUrl && (
                  <a href={profile.resumeUrl} target="_blank" rel="noreferrer">
                    Resume
                  </a>
                )}

                {profile.linkedinUrl && (
                  <a href={profile.linkedinUrl} target="_blank" rel="noreferrer">
                    LinkedIn
                  </a>
                )}

                {profile.githubUrl && (
                  <a href={profile.githubUrl} target="_blank" rel="noreferrer">
                    GitHub
                  </a>
                )}
              </div>
            </div>
          ) : (
            <div className="empty-state">
              <p>No profile found. Create your profile using the form.</p>
            </div>
          )}
        </div>

        <div className="section-card">
          <h2>{profile ? "Update Profile" : "Create Profile"}</h2>

          <form className="form-grid" onSubmit={saveProfile}>
            <input
              type="text"
              name="phone"
              placeholder="Phone Number"
              value={formData.phone}
              onChange={handleChange}
              required
            />

            <input
              type="text"
              name="education"
              placeholder="Education"
              value={formData.education}
              onChange={handleChange}
              required
            />

            <textarea
              name="experience"
              placeholder="Experience"
              value={formData.experience}
              onChange={handleChange}
            />

            <textarea
              name="skills"
              placeholder="Skills"
              value={formData.skills}
              onChange={handleChange}
              required
            />

            <input
              type="url"
              name="resumeUrl"
              placeholder="Resume URL"
              value={formData.resumeUrl}
              onChange={handleChange}
            />

            <input
              type="url"
              name="linkedinUrl"
              placeholder="LinkedIn URL"
              value={formData.linkedinUrl}
              onChange={handleChange}
            />

            <input
              type="url"
              name="githubUrl"
              placeholder="GitHub URL"
              value={formData.githubUrl}
              onChange={handleChange}
            />

            <button type="submit">
              {profile ? "Update Profile" : "Create Profile"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default CandidateProfile;