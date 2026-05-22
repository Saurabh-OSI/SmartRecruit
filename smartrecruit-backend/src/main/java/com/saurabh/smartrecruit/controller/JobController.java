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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saurabh.smartrecruit.dto.JobRequest;
import com.saurabh.smartrecruit.dto.JobResponse;
import com.saurabh.smartrecruit.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public JobResponse createJob(@Valid @RequestBody JobRequest request) {
        return jobService.createJob(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public List<JobResponse> getActiveJobs() {
        return jobService.getActiveJobs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public JobResponse getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public JobResponse updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        return jobService.updateJob(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteJob(@PathVariable Long id) {
        return jobService.deleteJob(id);
    }

    @PutMapping("/{id}/close")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public String closeJob(@PathVariable Long id) {
        return jobService.closeJob(id);
    }

    @GetMapping("/search/title")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public List<JobResponse> searchByTitle(@RequestParam String title) {
        return jobService.searchByTitle(title);
    }

    @GetMapping("/search/location")
    @PreAuthorize("hasAnyRole('ADMIN','HR','CANDIDATE')")
    public List<JobResponse> searchByLocation(@RequestParam String location) {
        return jobService.searchByLocation(location);
    }
}