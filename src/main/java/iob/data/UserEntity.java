package iob.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class UserEntity{
	
	@Id
	private String userId;
	private UserRole role;
	private String username;
	private String avatar;
	private int version;
	
	public UserEntity() {
		
	}	
	
	public UserEntity(String userId, UserRole role, String username, String avatar) {
		this.userId = userId;
		this.username = username;
		this.role = role;
		this.avatar = avatar;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
 	public int getVersion() {
 		return version;
 	}
	
	public void setVersion(int version) {
		this.version = version;
	}

}
