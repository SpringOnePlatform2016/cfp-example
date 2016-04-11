package com.example.cfp.web;

import com.example.cfp.domain.Submission;
import com.example.cfp.domain.Track;
import com.example.cfp.security.SecurityConfig;
import com.example.cfp.submission.SubmissionService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CfpController.class)
@Import(SecurityConfig.class)
public class CfpControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SubmissionService submissionService;

	@Test
	public void submitTalk() throws Exception {
		given(this.submissionService.create(any())).willReturn(new Submission());
		this.mvc.perform(post("/submit")
				.param("email", "john@example.com")
				.param("name", "John Smith")
				.param("title", "Alice in Wonderland")
				.param("summary", "my abstract")
				.param("track", Track.ALTERNATE_LANGUAGES.getId())
				.with(csrf()))
				.andExpect(status().isFound())
				.andExpect(header().string(HttpHeaders.LOCATION, "/submit?navSection=submit"));
		verify(this.submissionService).create(any());
	}

}
