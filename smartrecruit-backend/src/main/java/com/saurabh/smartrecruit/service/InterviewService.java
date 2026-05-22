package com.saurabh.smartrecruit.service;

import com.saurabh.smartrecruit.dto.InterviewRequest;
import com.saurabh.smartrecruit.dto.InterviewResponse;
import com.saurabh.smartrecruit.dto.InterviewStatusRequest;
import com.saurabh.smartrecruit.entity.ApplicationStatus;
import com.saurabh.smartrecruit.entity.Interview;
import com.saurabh.smartrecruit.entity.InterviewStatus;
import com.saurabh.smartrecruit.entity.JobApplication;
import com.saurabh.smartrecruit.exception.ResourceNotFoundException;
import com.saurabh.smartrecruit.repository.ApplicationRepository;
import com.saurabh.smartrecruit.repository.InterviewRepository;
import com.saurabh.smartrecruit.security.AccessControlService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final AccessControlService accessControlService;

    public InterviewService(InterviewRepository interviewRepository,
                            ApplicationRepository applicationRepository,
                            AccessControlService accessControlService) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.accessControlService = accessControlService;
    }

    public InterviewResponse scheduleInterview(InterviewRequest request) {

        JobApplication application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + request.getApplicationId()));

        Interview interview = new Interview();
        interview.setApplication(application);
        interview.setInterviewDateTime(request.getInterviewDateTime());
        interview.setInterviewMode(request.getInterviewMode());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setInterviewerName(request.getInterviewerName());
        interview.setFeedback(request.getFeedback());
        interview.setStatus(InterviewStatus.SCHEDULED);

        application.setStatus(ApplicationStatus.INTERVIEW);
        applicationRepository.save(application);

        Interview savedInterview = interviewRepository.save(interview);

        return mapToResponse(savedInterview);
    }

    public List<InterviewResponse> getAllInterviews() {

        return interviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InterviewResponse getInterviewById(Long id) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));

        accessControlService.requireCandidateOwnership(interview.getApplication().getCandidate().getId());

        return mapToResponse(interview);
    }

    public List<InterviewResponse> getInterviewsByApplication(Long applicationId) {

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        accessControlService.requireCandidateOwnership(application.getCandidate().getId());

        return interviewRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InterviewResponse updateInterview(Long id, InterviewRequest request) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));

        interview.setInterviewDateTime(request.getInterviewDateTime());
        interview.setInterviewMode(request.getInterviewMode());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setInterviewerName(request.getInterviewerName());
        interview.setFeedback(request.getFeedback());

        Interview updatedInterview = interviewRepository.save(interview);

        return mapToResponse(updatedInterview);
    }

    public InterviewResponse updateInterviewStatus(Long id, InterviewStatusRequest request) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));

        interview.setStatus(request.getStatus());

        if (request.getFeedback() != null && !request.getFeedback().isBlank()) {
            interview.setFeedback(request.getFeedback());
        }

        Interview updatedInterview = interviewRepository.save(interview);

        return mapToResponse(updatedInterview);
    }

    public String deleteInterview(Long id) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));

        interviewRepository.delete(interview);

        return "Interview deleted successfully";
    }

    private InterviewResponse mapToResponse(Interview interview) {

        JobApplication application = interview.getApplication();

        return new InterviewResponse(
                interview.getId(),
                application.getId(),
                application.getCandidate().getId(),
                application.getCandidate().getName(),
                application.getCandidate().getEmail(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getCompanyName(),
                interview.getInterviewDateTime(),
                interview.getInterviewMode(),
                interview.getMeetingLink(),
                interview.getInterviewerName(),
                interview.getFeedback(),
                interview.getStatus(),
                interview.getCreatedAt(),
                interview.getUpdatedAt()
        );
    }
}
