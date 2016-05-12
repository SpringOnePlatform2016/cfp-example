package com.example.cfp.web;

import javax.validation.constraints.NotNull;

import com.example.cfp.domain.Track;
import org.hibernate.validator.constraints.NotEmpty;

class SubmissionForm {

	@NotEmpty(message="Title should not be empty")
	private String title;

	@NotEmpty(message="Summary should not be empty")
	private String summary;

	@NotNull
	private Track track;

	private String notes;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Track getTrack() {
		return this.track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
