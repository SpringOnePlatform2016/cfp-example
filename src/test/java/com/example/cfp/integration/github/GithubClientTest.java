package com.example.cfp.integration.github;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest(GithubClient.class)
public class GithubClientTest {

	@MockBean
	private CounterService counterService;

	@Autowired
	private GithubClient githubClient;

	@Autowired
	private MockRestServiceServer mockServer;

	@Test
	public void getRecentCommits() {
		expectJson("https://api.github.com/repos/spring-projects/spring-framework/commits",
				"github/spring-framework-commits.json");
		List<Commit> recentCommits = this.githubClient.getRecentCommits(
				"spring-projects", "spring-framework");
		this.mockServer.verify();
		assertThat(recentCommits).hasSize(5);
		assertCommit(recentCommits.get(0), "7737c3c",
				"Warn about non-static BeanDefinitionRegistryPostProcessor declarations on @Configuration classes",
				"2016-05-02T13:19:05Z",
				"jhoeller", "Juergen Hoeller", "https://avatars.githubusercontent.com/u/1263688?v=3");
		assertCommit(recentCommits.get(3), "09b45d2",
				"Validate callback is always invoked in DMLC#stop",
				"2016-05-02T11:33:05Z",
				"snicoll", "Stephane Nicoll", "https://avatars.githubusercontent.com/u/490484?v=3");
		verify(this.counterService, times(1)).increment("cfp.github.requests");
	}

	@Test
	public void getRecentCommitsNoCommit() {
		expectJson("https://api.github.com/repos/spring-projects/spring-boot/commits",
				"github/no-commit.json");
		List<Commit> latestCommit = this.githubClient.getRecentCommits("spring-projects", "spring-boot");
		assertThat(latestCommit).hasSize(0);
		verify(this.counterService, times(1)).increment("cfp.github.requests");
	}

	@Test
	public void getRecentPolishCommitNoMatch() {
		expectJsonPages("https://api.github.com/repos/spring-projects/spring-framework/commits",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json");
		Commit recentPolish = this.githubClient.getRecentPolishCommit("spring-projects", "spring-framework");
		assertThat(recentPolish).isNull();
		verify(this.counterService, times(5)).increment("cfp.github.requests");
	}

	@Test
	public void getRecentPolishCommitMatch() {
		expectJsonPages("https://api.github.com/repos/spring-projects/spring-framework/commits",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits.json",
				"github/spring-framework-commits-no-polish.json",
				"github/spring-framework-commits-no-polish.json");
		Commit recentPolish = this.githubClient.getRecentPolishCommit("spring-projects", "spring-framework");
		assertThat(recentPolish).isNotNull();
		assertThat(recentPolish.getMessage()).isEqualTo("Polishing");
		assertThat(recentPolish.getSha()).isEqualTo("07ea3745c49ec506e17dcb56107639cf36339d2c");
		verify(this.counterService, times(3)).increment("cfp.github.requests");
	}

	@Test
	public void getRecentPolishCommitNoMatchSinglePage() {
		expectJsonPages("https://api.github.com/repos/spring-projects/spring-framework/commits",
				"github/spring-framework-commits-no-polish.json");
		Commit recentPolish = this.githubClient.getRecentPolishCommit("spring-projects", "spring-framework");
		assertThat(recentPolish).isNull();
		verify(this.counterService, times(1)).increment("cfp.github.requests");
	}

	@Test
	public void getUser() {
		expectJson("https://api.github.com/users/jsmith", "github/jsmith.json");
		GithubUser user = this.githubClient.getUser("jsmith");
		assertThat(user.getEmail()).isEqualTo("john@example.com");
		assertThat(user.getName()).isEqualTo("John Smith");
		assertThat(user.getCompany()).isEqualTo("Acme Inc.");
		assertThat(user.getBlog()).isEqualTo("https://acme.org/blog");
		assertThat(user.getAvatar()).isEqualTo("https://acme.org/team/jsmith/avatar");
	}

	private void expectJsonPages(String initialUrl, String... pages) {
		for (int i = 0; i < pages.length; i++) {
			String url = i == 0 ? initialUrl : initialUrl + "?page=" + (i + 1);
			String nextPage = (i + 1) < pages.length ? initialUrl + "?page=" + (i + 2) : null;
			expectJson(url, pages[i], nextPage);
		}
	}

	private void expectJson(String url, String bodyPath) {
		expectJson(url, bodyPath, null);
	}

	private void expectJson(String url, String bodyPath, String nextPage) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		if (nextPage != null) {
			httpHeaders.set(HttpHeaders.LINK,
					String.format("<%s>; rel=\"next\"", nextPage));
		}
		this.mockServer.expect(requestTo(url))
				.andExpect(method(HttpMethod.GET))
				.andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
				.andRespond(withStatus(HttpStatus.OK)
						.body(new ClassPathResource(bodyPath))
						.headers(httpHeaders));
	}

	private void assertCommit(Commit commit, String sha, String message, String date,
			String committerId, String committerName, String committerAvatar) {
		assertThat(commit).isNotNull();
		assertThat(commit.getSha()).isEqualTo(sha);
		assertThat(commit.getMessage()).isEqualTo(message);
		assertThat(commit.getDate().toString()).isEqualTo(date);
		Commit.Committer committer = commit.getCommitter();
		assertThat(committer).isNotNull();
		assertThat(committer.getId()).isEqualTo(committerId);
		assertThat(committer.getName()).isEqualTo(committerName);
		assertThat(committer.getAvatarUrl()).isEqualTo(committerAvatar);
	}

}
