package com.example.cfp.domain;

public enum Track {

	ALTERNATE_LANGUAGES("Alternate Languages"),

	CLOUD("Cloud"),

	SERVER_SIDE_JAVA("Server side Java"),

	MOBILE("Mobile");

	private String displayName;

	Track(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getId() {
		return name();
	}
}
