package com.example.cfp.integration.github;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.boot.jackson.JsonObjectDeserializer;

public class CommitDeserializer extends JsonObjectDeserializer<Commit> {

	@Override
	protected Commit deserializeObject(JsonParser jsonParser,
			DeserializationContext deserializationContext,
			ObjectCodec objectCodec, JsonNode jsonNode) throws IOException {

		JsonNode commitNode = jsonNode.get("commit");
		JsonNode committerNode = jsonNode.get("committer");
		String sha = nullSafeValue(jsonNode.get("sha"), String.class);
		String message = singleLine(nullSafeValue(commitNode.get("message"), String.class));

		String committerId = nullSafeValue(committerNode.get("login"), String.class);
		String committerName = nullSafeValue(commitNode.get("committer").get("name"), String.class);
		String date = nullSafeValue(commitNode.get("committer").get("date"), String.class);
		String committerAvatar = nullSafeValue(committerNode.get("avatar_url"), String.class);
		Commit.Committer committer =
				new Commit.Committer(committerId, committerName, committerAvatar);
		return new Commit(sha, message, committer, Instant.parse(date));
	}

	private String singleLine(String s) {
		if (s == null) {
			return null;
		}
		int i = s.indexOf('\n');
		if (i != -1) {
			return s.substring(0, i);
		}
		return s;
	}

}
