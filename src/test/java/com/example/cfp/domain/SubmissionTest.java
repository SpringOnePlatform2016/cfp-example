package com.example.cfp.domain;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubmissionTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	public SubmissionRepository submissionRepository;

	@Test
	public void newSubmissionHasDraftStatus() {
		User speaker = this.entityManager.persist(
				new User("juergen@example.com", "Jürgen Höller"));
		Submission submission = new Submission();
		submission.setSpeaker(speaker);
		Submission saved = this.submissionRepository.save(submission);
		assertThat(saved.getStatus()).isEqualTo(SubmissionStatus.DRAFT);
	}

	@Test
	public void findBySpeaker() {
		User speaker = this.entityManager.persist(
				new User("juergen@example.com", "Jürgen Höller"));
		this.submissionRepository.save(createDummySubmission(speaker, "Foo"));
		this.submissionRepository.save(createDummySubmission(speaker, "Bar"));

		List<Submission> submissions = this.submissionRepository.findBySpeaker(speaker);
		assertThat(submissions).hasSize(2);
	}

	private Submission createDummySubmission(User speaker, String title) {
		Submission submission = new Submission();
		submission.setSpeaker(speaker);
		submission.setTitle(title);
		submission.setSummary("Live coding 4tw");
		submission.setTrack(Track.SERVER_SIDE_JAVA);
		return submission;
	}

}
