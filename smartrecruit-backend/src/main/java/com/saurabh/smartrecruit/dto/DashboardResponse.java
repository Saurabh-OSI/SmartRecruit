package com.saurabh.smartrecruit.dto;

public class DashboardResponse {

    private long totalJobs;
    private long activeJobs;
    private long closedJobs;

    private long totalCandidates;

    private long totalApplications;
    private long appliedApplications;
    private long shortlistedApplications;
    private long interviewApplications;
    private long selectedApplications;
    private long rejectedApplications;

    private long totalInterviews;
    private long scheduledInterviews;
    private long completedInterviews;
    private long cancelledInterviews;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalJobs, long activeJobs, long closedJobs,
                             long totalCandidates,
                             long totalApplications, long appliedApplications,
                             long shortlistedApplications, long interviewApplications,
                             long selectedApplications, long rejectedApplications,
                             long totalInterviews, long scheduledInterviews,
                             long completedInterviews, long cancelledInterviews) {
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.closedJobs = closedJobs;
        this.totalCandidates = totalCandidates;
        this.totalApplications = totalApplications;
        this.appliedApplications = appliedApplications;
        this.shortlistedApplications = shortlistedApplications;
        this.interviewApplications = interviewApplications;
        this.selectedApplications = selectedApplications;
        this.rejectedApplications = rejectedApplications;
        this.totalInterviews = totalInterviews;
        this.scheduledInterviews = scheduledInterviews;
        this.completedInterviews = completedInterviews;
        this.cancelledInterviews = cancelledInterviews;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public long getActiveJobs() {
        return activeJobs;
    }

    public long getClosedJobs() {
        return closedJobs;
    }

    public long getTotalCandidates() {
        return totalCandidates;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getAppliedApplications() {
        return appliedApplications;
    }

    public long getShortlistedApplications() {
        return shortlistedApplications;
    }

    public long getInterviewApplications() {
        return interviewApplications;
    }

    public long getSelectedApplications() {
        return selectedApplications;
    }

    public long getRejectedApplications() {
        return rejectedApplications;
    }

    public long getTotalInterviews() {
        return totalInterviews;
    }

    public long getScheduledInterviews() {
        return scheduledInterviews;
    }

    public long getCompletedInterviews() {
        return completedInterviews;
    }

    public long getCancelledInterviews() {
        return cancelledInterviews;
    }
}