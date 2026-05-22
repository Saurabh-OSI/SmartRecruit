package com.saurabh.smartrecruit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saurabh.smartrecruit.dto.ApplicationRequest;
import com.saurabh.smartrecruit.dto.ApplicationResponse;
import com.saurabh.smartrecruit.dto.ApplicationStatusRequest;
import com.saurabh.smartrecruit.entity.ApplicationStatus;
import com.saurabh.smartrecruit.entity.Job;
import com.saurabh.smartrecruit.entity.JobApplication;
import com.saurabh.smartrecruit.entity.User;
import com.saurabh.smartrecruit.exception.BadRequestException;
import com.saurabh.smartrecruit.exception.ResourceNotFoundException;
import com.saurabh.smartrecruit.repository.ApplicationRepository;
import com.saurabh.smartrecruit.repository.JobRepository;
import com.saurabh.smartrecruit.repository.UserRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public ApplicationResponse applyForJob(ApplicationRequest request) {

        User candidate = userRepository.findById(request.getCandidateId())
        .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + request.getCandidateId()));

Job job = jobRepository.findById(request.getJobId())
        .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + request.getJobId()));

if (!job.isActive()) {
    throw new BadRequestException("This job is closed. You cannot apply.");
}

if (applicationRepository.existsByCandidateIdAndJobId(request.getCandidateId(), request.getJobId())) {
    throw new BadRequestException("Candidate already applied for this job");
}

        JobApplication application = new JobApplication();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setCoverLetter(request.getCoverLetter());
        application.setStatus(ApplicationStatus.APPLIED);

        JobApplication savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    public List<ApplicationResponse> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationResponse getApplicationById(Long id) {

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        return mapToResponse(application);
    }

    public List<ApplicationResponse> getApplicationsByCandidate(Long candidateId) {

        return applicationRepository.findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationResponse updateStatus(Long id, ApplicationStatusRequest request) {

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        application.setStatus(request.getStatus());

        JobApplication updatedApplication = applicationRepository.save(application);

        return mapToResponse(updatedApplication);
    }

    public String deleteApplication(Long id) {

        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        applicationRepository.delete(application);

        return "Application deleted successfully";
    }

    private ApplicationResponse mapToResponse(JobApplication application) {

        User candidate = application.getCandidate();
        Job job = application.getJob();

        return new ApplicationResponse(
                application.getId(),
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail(),
                job.getId(),
                job.getTitle(),
                job.getCompanyName(),
                job.getLocation(),
                application.getStatus(),
                application.getCoverLetter(),
                application.getAppliedAt(),
                application.getUpdatedAt()
        );
    }
}