package com.example.cfp.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubmissionRepository extends PagingAndSortingRepository<Submission, Long> {

	List<Submission> findBySpeakerEmail(String email);

}
