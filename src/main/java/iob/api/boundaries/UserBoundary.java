package iob.api.boundaries;

import java.util.HashMap;
import java.util.Map;

public class UserBoundary {
	
	private Map<String, String> userId;
	private String role;
	private String username;
	private String avatar;
	
	public UserBoundary() {
		userId = new HashMap<>();
	}

	public UserBoundary(Map<String, String> userId, String role, String username, String avatar) {
		this();
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public Map<String, String> getUserId() {
		return userId;
	}

	public void setUserId(Map<String, String> userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar
				+ "]";
	}
	
}
