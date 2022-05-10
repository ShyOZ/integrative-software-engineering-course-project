package iob.data;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@CompoundIndex(def = "{domain: 1, email: 1}", name = "user Id - domain and email", unique = true)
public class UserEntity {

	private String domain;
	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	private @Indexed int version;

	public UserEntity() {

	}

	public UserEntity(String domain, String email, UserRole role, String username, String avatar) {
		this.domain = domain;
		this.email = email;
		this.username = username;
		this.role = role;
		this.avatar = avatar;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
