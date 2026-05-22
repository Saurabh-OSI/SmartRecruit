package com.saurabh.smartrecruit.dto;

import java.time.LocalDateTime;

import com.saurabh.smartrecruit.entity.InterviewStatus;

public class InterviewResponse {

    private Long id;

    private Long applicationId;

    private Long candidateId;
    private String candidateName;
    private String candidateEmail;

    private Long jobId;
    private String jobTitle;
    private String companyName;

    private LocalDateTime interviewDateTime;
    private String interviewMode;
    private String meetingLink;
    private String interviewerName;
    private String feedback;
    private InterviewStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InterviewResponse() {
    }

    public InterviewResponse(Long id, Long applicationId, Long candidateId, String candidateName,
                             String candidateEmail, Long jobId, String jobTitle, String companyName,
                             LocalDateTime interviewDateTime, String interviewMode, String meetingLink,
                             String interviewerName, String feedback, InterviewStatus status,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.candidateEmail = candidateEmail;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.interviewDateTime = interviewDateTime;
        this.interviewMode = interviewMode;
        this.meetingLink = meetingLink;
        this.interviewerName = interviewerName;
        this.feedback = feedback;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getCandidateEmail() {
        return candidateEmail;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public String getFeedback() {
        return feedback;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}