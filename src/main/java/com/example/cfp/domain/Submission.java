package com.example.cfp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@SuppressWarnings("serial")
public class Submission implements Serializable {

	@GeneratedValue
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "speaker_id")
	private User speaker;

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

	public User getSpeaker() {
		return speaker;
	}

	public void setSpeaker(User speaker) {
		this.speaker = speaker;
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
