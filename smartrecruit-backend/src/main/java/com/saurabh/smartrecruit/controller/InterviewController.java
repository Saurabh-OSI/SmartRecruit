package com.saurabh.smartrecruit.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saurabh.smartrecruit.dto.InterviewRequest;
import com.saurabh.smartrecruit.dto.InterviewResponse;
import com.saurabh.smartrecruit.dto.InterviewStatusRequest;
import com.saurabh.smartrecruit.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/schedule")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public InterviewResponse scheduleInterview(@Valid @RequestBody InterviewRequest request) {
        return interviewService.scheduleInterview(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<InterviewResponse> getAllInterviews() {
        return interviewService.getAllInterviews();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public InterviewResponse getInterviewById(@PathVariable Long id) {
        return interviewService.getInterviewById(id);
    }

    @GetMapping("/application/{applicationId}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public List<InterviewResponse> getInterviewsByApplication(@PathVariable Long applicationId) {
        return interviewService.getInterviewsByApplication(applicationId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public InterviewResponse updateInterview(@PathVariable Long id,
                                             @Valid @RequestBody InterviewRequest request) {
        return interviewService.updateInterview(id, request);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public InterviewResponse updateInterviewStatus(@PathVariable Long id,
                                                   @Valid @RequestBody InterviewStatusRequest request) {
        return interviewService.updateInterviewStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public String deleteInterview(@PathVariable Long id) {
        return interviewService.deleteInterview(id);
    }
}