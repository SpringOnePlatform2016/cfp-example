package com.example.cfp.domain;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubmissionTest {

	@Autowired
	public SubmissionRepository submissionRepository;

	@Test
	public void newSubmissionHasDraftStatus() {
		Submission submission = new Submission();
		submission.setSpeakerEmail("john@example.com");
		Submission saved = this.submissionRepository.save(submission);
		assertThat(saved.getStatus()).isEqualTo(SubmissionStatus.DRAFT);
	}

	@Test
	public void findBySpeakerEmail() {
		this.submissionRepository.save(createDummySubmission("john@example.com", "Foo"));
		this.submissionRepository.save(createDummySubmission("john@example.com", "Bar"));

		List<Submission> submissions = this.submissionRepository.findBySpeakerEmail("john@example.com");
		assertThat(submissions).hasSize(2);
	}

	private Submission createDummySubmission(String email, String title) {
		Submission submission = new Submission();
		submission.setSpeakerEmail(email);
		submission.setTitle(title);
		submission.setSummary("Live coding 4tw");
		submission.setTrack(Track.SERVER_SIDE_JAVA);
		return submission;
	}

}
