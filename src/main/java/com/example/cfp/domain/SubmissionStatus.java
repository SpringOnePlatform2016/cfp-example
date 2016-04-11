package com.example.cfp.domain;

public enum SubmissionStatus {

	DRAFT("Draft"),

	SUBMITTED("Submitted"),

	ACCEPTED("Accepted"),

	REJECTED("Rejected");

	private String displayName;

	SubmissionStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getId() {
		return name();
	}


}
