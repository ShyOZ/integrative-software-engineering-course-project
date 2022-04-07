package iob.logic.users;

import iob.data.UserRole;
import iob.logic.instances.UserId;

public class UserBoundary {
	private User user;

	
	public UserBoundary() {
		
	}

	public UserBoundary(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserBoundary [user=" + user.toString() + "]";
	}
}
