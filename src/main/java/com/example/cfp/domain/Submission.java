package com.example.cfp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@SuppressWarnings("serial")
public class Submission implements Serializable {

	@GeneratedValue
	@Id
	private Long id;

	@Column(nullable = false)
	private String speakerEmail;

	@Column
	private String speakerName;

	@Enumerated
	private Track track;

	@Column
	private String title;

	@Column
	private SubmissionStatus status;

	@Column
	@Lob
	private String summary;

	public Submission() {
		this.status = SubmissionStatus.DRAFT;
	}

	public Long getId() {
		return this.id;
	}

	public String getSpeakerEmail() {
		return this.speakerEmail;
	}

	public void setSpeakerEmail(String speakerEmail) {
		this.speakerEmail = speakerEmail;
	}

	public String getSpeakerName() {
		return this.speakerName;
	}

	public void setSpeakerName(String speakerName) {
		this.speakerName = speakerName;
	}

	public Track getTrack() {
		return this.track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SubmissionStatus getStatus() {
		return this.status;
	}

	public void setStatus(SubmissionStatus status) {
		this.status = status;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "Submission{" + "id=" + this.id + ", title='" + this.title + '\'' + ", status='" + this.status + '\'' + '}';
	}

}
