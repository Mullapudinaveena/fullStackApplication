package com.example.projectbackend.profile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.projectbackend.datastore.UserProfileDataStore;

@Repository
public class UserProfileDataAcessService {

	private final UserProfileDataStore userProfileDataStore;
	
	@Autowired
	public UserProfileDataAcessService(UserProfileDataStore userProfileDataStore) {
		this.userProfileDataStore = userProfileDataStore;
	}
	
	List<UserProfile> getUserProfiles(){
		return userProfileDataStore.getUserProfiles();
	}
}
