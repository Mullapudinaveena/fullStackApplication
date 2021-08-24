package com.example.projectbackend.profile;

import java.util.Optional;
import java.util.UUID;

public class UserProfile {

	private final UUID userProfileId;
	private final String username;
	private String userProfileImageLink; // S3 key
	
	public UserProfile(UUID userProfileId, String username, String userProfileImageLink) {
		this.userProfileId = userProfileId;
		this.username = username;
		this.userProfileImageLink = userProfileImageLink;
	}

	public UUID getUserProfileId() {
		return userProfileId;
	}

	public String getUsername() {
		return username;
	}

	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}

	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userProfileId == null) ? 0 : userProfileId.hashCode());
		result = prime * result + ((userProfileImageLink == null) ? 0 : userProfileImageLink.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		if (userProfileId == null) {
			if (other.userProfileId != null)
				return false;
		} else if (!userProfileId.equals(other.userProfileId))
			return false;
		if (userProfileImageLink == null) {
			if (other.userProfileImageLink != null)
				return false;
		} else if (!userProfileImageLink.equals(other.userProfileImageLink))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
