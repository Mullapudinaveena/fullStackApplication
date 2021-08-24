package com.example.projectbackend.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.projectbackend.bucket.BucketName;
import com.example.projectbackend.filestore.FileStore;

@Service
public class UserProfileService {

	private final UserProfileDataAcessService userProfileDataAccessService;
	private final FileStore fileStore;
	
	@Autowired
	public UserProfileService(UserProfileDataAcessService userProfileDataAccessService, FileStore fileStore) {
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}
	
	List<UserProfile> getUserProfiles(){
		return userProfileDataAccessService.getUserProfiles();
	}

	public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
		// check if image is not empty
		if (file.isEmpty()) {
			throw new IllegalStateException("File must be an image of format JPEG or PNG");
		}
		
		// if file is an image
		if(Arrays.asList(ContentType.IMAGE_JPEG,ContentType.IMAGE_PNG).contains(file.getContentType())){
			throw new IllegalStateException("Cannot upload empty file");
		}
		
		// the user exists in our database
		UserProfile user = getUserProfileOrThrow(userProfileId);
		
		// grab some metadata from file if any
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));
			
		// store the image in s3 and update database with s3 image link
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
		
		try {
			fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
			user.setUserProfileImageLink(filename);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public byte[] downloadUserProfileImage(UUID userProfileId) {
		UserProfile user = getUserProfileOrThrow(userProfileId);
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		return user.getUserProfileImageLink()
			.map(key -> fileStore.download(path, key))
			.orElse(new byte[0]);
		
	}
	
	 private UserProfile getUserProfileOrThrow(UUID userProfileId) {
	        return userProfileDataAccessService
	                .getUserProfiles()
	                .stream()
	                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
	                .findFirst()
	                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
	    }

	
}
