package com.saurabh.smartrecruit.repository;

import com.saurabh.smartrecruit.entity.Interview;
import com.saurabh.smartrecruit.entity.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationId(Long applicationId);

    long countByStatus(InterviewStatus status);
}