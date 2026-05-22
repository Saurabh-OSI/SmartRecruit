package com.saurabh.smartrecruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurabh.smartrecruit.entity.ApplicationStatus;
import com.saurabh.smartrecruit.entity.JobApplication;

public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByCandidateId(Long candidateId);

    List<JobApplication> findByJobId(Long jobId);

    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);

    long countByStatus(ApplicationStatus status);
}