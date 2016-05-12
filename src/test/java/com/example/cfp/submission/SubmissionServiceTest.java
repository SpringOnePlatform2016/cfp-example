package com.example.cfp.submission;

import java.util.List;

import com.example.cfp.domain.Submission;
import com.example.cfp.domain.SubmissionStatus;
import com.example.cfp.domain.Track;
import com.example.cfp.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE, classes = SubmissionService.class))
public class SubmissionServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SubmissionService submissionService;

	@Test
	public void createTalk() throws Exception {
		User speaker = this.entityManager.persist(
				new User("john@example.com", "John Smith"));
		SubmissionRequest request = new SubmissionRequest();
		request.setSpeaker(speaker);
		request.setTalk("Alice in Wonderland", "my abstract", Track.ALTERNATE_LANGUAGES,
				"this rocks");
		Submission submission = this.submissionService.create(request);
		assertThat(submission).isNotNull();
		assertThat(submission.getSpeaker()).isNotNull();
		assertThat(submission.getSpeaker().getId()).isEqualTo(speaker.getId());
		assertThat(submission.getTitle()).isEqualTo("Alice in Wonderland");
		assertThat(submission.getSummary()).isEqualTo("my abstract");
		assertThat(submission.getTrack()).isEqualTo(Track.ALTERNATE_LANGUAGES);
		assertThat(submission.getNotes()).isEqualTo("this rocks");
		assertThat(submission.getStatus()).isEqualTo(SubmissionStatus.DRAFT);

		List<Submission> submissions = this.submissionService.getSubmissionRepository()
				.findBySpeaker(speaker);
		assertThat(submissions).hasSize(1);
	}

}
