package com.saurabh.smartrecruit.dto;

import java.time.LocalDateTime;

public class CandidateProfileResponse {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String education;
    private String experience;
    private String skills;
    private String resumeUrl;
    private String linkedinUrl;
    private String githubUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CandidateProfileResponse() {
    }

    public CandidateProfileResponse(Long id, Long userId, String name, String email, String phone,
                                    String education, String experience, String skills,
                                    String resumeUrl, String linkedinUrl, String githubUrl,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.education = education;
        this.experience = experience;
        this.skills = skills;
        this.resumeUrl = resumeUrl;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkills() {
        return skills;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}