package iob.logic.activities;

import iob.logic.users.User;

public class DislikeActivity extends ActivityInstance {
	User likedBy;
	User liked;
	
	public DislikeActivity() {
	}
	
	public DislikeActivity(User likedBy, User liked) {
		this.likedBy = likedBy;
		this.liked = liked;
	}
	
	public User getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(User likedBy) {
		this.likedBy = likedBy;
	}
	public User getLiked() {
		return liked;
	}
	public void setLiked(User liked) {
		this.liked = liked;
	}
}
