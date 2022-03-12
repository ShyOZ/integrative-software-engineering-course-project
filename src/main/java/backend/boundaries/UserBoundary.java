package backend.boundaries;

import java.util.Map;

public class UserBoundary {
	
	private Map<String, String> userId;
	private String role;
	private String userName;
	private String avatar;
	
	public UserBoundary() {
		
	}

	public UserBoundary(Map<String, String> userId, String role, String userName, String avatar) {
		super();
		this.userId = userId;
		this.role = role;
		this.userName = userName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", userName=" + userName + ", avatar=" + avatar
				+ "]";
	}
	
}
