package com.saurabh.smartrecruit.dto;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequest {

    @NotNull
    private Long candidateId;

    @NotNull
    private Long jobId;

    private String coverLetter;

    public ApplicationRequest() {
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
}