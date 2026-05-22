package com.saurabh.smartrecruit.service;

import org.springframework.stereotype.Service;

import com.saurabh.smartrecruit.dto.DashboardResponse;
import com.saurabh.smartrecruit.entity.ApplicationStatus;
import com.saurabh.smartrecruit.entity.InterviewStatus;
import com.saurabh.smartrecruit.repository.ApplicationRepository;
import com.saurabh.smartrecruit.repository.CandidateProfileRepository;
import com.saurabh.smartrecruit.repository.InterviewRepository;
import com.saurabh.smartrecruit.repository.JobRepository;

@Service
public class DashboardService {

    private final JobRepository jobRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public DashboardService(JobRepository jobRepository,
                            CandidateProfileRepository candidateProfileRepository,
                            ApplicationRepository applicationRepository,
                            InterviewRepository interviewRepository) {
        this.jobRepository = jobRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    public DashboardResponse getSummary() {

        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.countByActiveTrue();
        long closedJobs = jobRepository.countByActiveFalse();

        long totalCandidates = candidateProfileRepository.count();

        long totalApplications = applicationRepository.count();
        long appliedApplications = applicationRepository.countByStatus(ApplicationStatus.APPLIED);
        long shortlistedApplications = applicationRepository.countByStatus(ApplicationStatus.SHORTLISTED);
        long interviewApplications = applicationRepository.countByStatus(ApplicationStatus.INTERVIEW);
        long selectedApplications = applicationRepository.countByStatus(ApplicationStatus.SELECTED);
        long rejectedApplications = applicationRepository.countByStatus(ApplicationStatus.REJECTED);

        long totalInterviews = interviewRepository.count();
        long scheduledInterviews = interviewRepository.countByStatus(InterviewStatus.SCHEDULED);
        long completedInterviews = interviewRepository.countByStatus(InterviewStatus.COMPLETED);
        long cancelledInterviews = interviewRepository.countByStatus(InterviewStatus.CANCELLED);

        return new DashboardResponse(
                totalJobs,
                activeJobs,
                closedJobs,
                totalCandidates,
                totalApplications,
                appliedApplications,
                shortlistedApplications,
                interviewApplications,
                selectedApplications,
                rejectedApplications,
                totalInterviews,
                scheduledInterviews,
                completedInterviews,
                cancelledInterviews
        );
    }
}