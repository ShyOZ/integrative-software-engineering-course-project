package iob.logic.users;

public class UserBoundary {
	private UserId userId;
	private UserRoleLogic role;
	private String username;
	private String avatar;
	

	public UserBoundary() {
		
	}
	
	public UserBoundary(UserId userId, UserRoleLogic role, String username, String avatar) {
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
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar
				+ "]";
	}

	

	
}
