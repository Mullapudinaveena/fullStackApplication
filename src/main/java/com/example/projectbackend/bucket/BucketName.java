package com.example.projectbackend.bucket;

public enum BucketName {
	PROFILE_IMAGE("image-storage-123");
	
	private final String bucketName;

	private BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}
	
}
