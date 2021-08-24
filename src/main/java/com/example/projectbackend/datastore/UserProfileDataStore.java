package com.example.projectbackend.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.projectbackend.profile.UserProfile;


@Repository
public class UserProfileDataStore {

	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
	
	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("0bf3ec82-2744-48a4-b139-0dba0bcf60a8"), "Alice James", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("2cbda4bc-74f5-48b5-b7fa-7fe922c433da"), "David Jones", null));
	}
	
	public List<UserProfile> getUserProfiles(){
		return USER_PROFILES;
	}
}
