package iob.data;

public class UserEntityId {
	private String domain;
	private String email;
	
	public UserEntityId() {
	}

	public UserEntityId(String email, String domain) {
		this.email = email;
		this.domain = domain;
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
}
