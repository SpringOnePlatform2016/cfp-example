package com.example.cfp.web;

import com.example.cfp.domain.SubmissionRepository;
import com.example.cfp.domain.SubmissionStatus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.ADMIN)
@RequestMapping("/admin")
public class AdminController {

	private final SubmissionRepository submissionRepository;

	public AdminController(SubmissionRepository submissionRepository) {
		this.submissionRepository = submissionRepository;
	}

	@RequestMapping("")
	public String admin(Model model) {

		model.addAttribute("submissions", this.submissionRepository.findAll());
		model.addAttribute("viewHelper", new ViewRenderingHelper());
		return "admin/index";
	}

	static class ViewRenderingHelper {

		public String submissionTableClass(SubmissionStatus status) {
			if (SubmissionStatus.ACCEPTED.equals(status)) {
				return "success";
			}
			else if (SubmissionStatus.REJECTED.equals(status)) {
				return "error";
			}
			return "";
		}
	}

}
