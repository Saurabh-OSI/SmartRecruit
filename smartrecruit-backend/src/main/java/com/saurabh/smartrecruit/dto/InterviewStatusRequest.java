package com.saurabh.smartrecruit.dto;

import com.saurabh.smartrecruit.entity.InterviewStatus;

import jakarta.validation.constraints.NotNull;

public class InterviewStatusRequest {

    @NotNull
    private InterviewStatus status;

    private String feedback;

    public InterviewStatusRequest() {
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}