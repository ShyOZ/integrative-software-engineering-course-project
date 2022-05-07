package iob.utility;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import iob.logic.users.NewUserBoundary;
import iob.logic.users.UserBoundary;

@Component
@ConfigurationProperties(prefix = "test-objects")
public class TestProperties {

	UserBoundary adminUser;
	NewUserBoundary newAdmin;
	UserBoundary managerUser;
	UserBoundary playerUser;
	NewUserBoundary newPlayer;

	public TestProperties() {

	}

	public TestProperties(UserBoundary adminUser, NewUserBoundary newAdmin, UserBoundary managerUser,
			UserBoundary playerUser, NewUserBoundary newPlayer) {
		this.adminUser = adminUser;
		this.newAdmin = newAdmin;
		this.managerUser = managerUser;
		this.playerUser = playerUser;
		this.newPlayer = newPlayer;
	}

	public UserBoundary getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(UserBoundary adminUser) {
		this.adminUser = adminUser;
	}

	public UserBoundary getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(UserBoundary managerUser) {
		this.managerUser = managerUser;
	}

	public UserBoundary getPlayerUser() {
		return playerUser;
	}

	public void setPlayerUser(UserBoundary playerUser) {
		this.playerUser = playerUser;
	}

	public NewUserBoundary getNewAdmin() {
		return newAdmin;
	}

	public void setNewAdmin(NewUserBoundary newAdmin) {
		this.newAdmin = newAdmin;
	}

	public NewUserBoundary getNewPlayer() {
		return newPlayer;
	}

	public void setNewPlayer(NewUserBoundary newPlayer) {
		this.newPlayer = newPlayer;
	}
}
