package com.example.cfp.web;

import com.example.cfp.integration.github.Commit;
import com.example.cfp.integration.github.GithubClient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.HOME)
public class HomeController {

	private final GithubClient githubClient;

	public HomeController(GithubClient githubClient) {
		this.githubClient = githubClient;
	}

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("latestFrameworkCommit",
				getLatestCommit("spring-projects", "spring-framework"));
		model.addAttribute("latestBootCommit",
				getLatestCommit("spring-projects", "spring-boot"));
		return "index";
	}

	private Commit getLatestCommit(String organization, String project) {
		return this.githubClient
				.getRecentCommits(organization, project)
				.stream().findFirst().get();
	}
}
