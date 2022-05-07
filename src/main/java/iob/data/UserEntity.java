package iob.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@CompoundIndex(def = "{'userId.domain': 1, 'userId.email': 1}", name = "domain and email", unique = true)
public class UserEntity {

	private @Id UserEntityId userId;
	private UserRole role;
	private String username;
	private String avatar;
	private @Indexed int version;

	public UserEntity() {

	}

	public UserEntity(UserEntityId userId, UserRole role, String username, String avatar) {
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

	public UserEntityId getUserId() {
		return userId;
	}

	public void setUserId(UserEntityId userId) {
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
