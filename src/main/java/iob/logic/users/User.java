package iob.logic.users;

import iob.logic.instances.UserId;

public class User {
	private UserId userId;
	private UserRoleLogic role;
	private String username;
	private String avatar;
	
	
	public User() {
		
	}


	public User(UserId userId, UserRoleLogic role, String username, String avatar) {
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}


	public UserId getUserId() {
		return userId;
	}


	public void setUserId(UserId userId) {
		this.userId = userId;
	}


	public UserRoleLogic getRole() {
		return role;
	}


	public void setRole(UserRoleLogic role) {
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
		return "User [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar + "]";
	}
}
