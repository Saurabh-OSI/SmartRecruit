package com.saurabh.smartrecruit.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InterviewRequest {

    @NotNull
    private Long applicationId;

    @NotNull
    private LocalDateTime interviewDateTime;

    @NotBlank
    private String interviewMode;

    private String meetingLink;

    @NotBlank
    private String interviewerName;

    private String feedback;

    public InterviewRequest() {
    }

    public Long getApplicationId() {
        return applicationId;
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

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public void setInterviewMode(String interviewMode) {
        this.interviewMode = interviewMode;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}