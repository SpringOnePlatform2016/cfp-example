package com.example.cfp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cfp")
public class CfpProperties {

	private final Github github = new Github();

	private final Security security = new Security();

	public Github getGithub() {
		return this.github;
	}

	public Security getSecurity() {
		return this.security;
	}


	public static class Github {

		/**
		 * Access token ("username:access_token") to query public github endpoints.
		 */
		private String token;

		public String getToken() {
			return this.token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

	public static class Security {

		/**
		 * Github users that have admin rights.
		 */
		private List<String> admins = Arrays.asList("snicoll", "bclozel");

		public List<String> getAdmins() {
			return admins;
		}

		public void setAdmins(List<String> admins) {
			this.admins = admins;
		}

	}

}
