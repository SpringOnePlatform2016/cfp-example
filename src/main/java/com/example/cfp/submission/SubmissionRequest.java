package com.example.cfp.submission;

import com.example.cfp.domain.Track;

public class SubmissionRequest {

	private String email;

	private String name;

	private String title;

	private String summary;

	private Track track;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public void setSpeaker(String email, String name) {
		setEmail(email);
		setName(name);
	}

	public void setTalk(String title, String summary, Track track) {
		setTitle(title);
		setSummary(summary);
		setTrack(track);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SubmissionRequest that = (SubmissionRequest) o;

		if (this.email != null ? !this.email.equals(that.email) : that.email != null) return false;
		if (this.name != null ? !this.name.equals(that.name) : that.name != null) return false;
		if (this.title != null ? !this.title.equals(that.title) : that.title != null) return false;
		if (this.summary != null ? !this.summary.equals(that.summary) : that.summary != null) return false;
		return this.track == that.track;
	}

	@Override
	public int hashCode() {
		int result = this.email != null ? this.email.hashCode() : 0;
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		result = 31 * result + (this.title != null ? this.title.hashCode() : 0);
		result = 31 * result + (this.summary != null ? this.summary.hashCode() : 0);
		result = 31 * result + (this.track != null ? this.track.hashCode() : 0);
		return result;
	}

}
