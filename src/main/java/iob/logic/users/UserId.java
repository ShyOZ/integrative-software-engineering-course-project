package iob.logic.users;

import org.springframework.beans.factory.annotation.Value;

public class UserId {
	@Value("${spring.application.name}")
	private String domain;
	private String email;

	public UserId() {

	}

	public UserId(String email, String domain) {
		this.email = email;
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}

	@Value("{spring.application.name}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserId [domain=" + domain + ", email=" + email + "]";
	}

}
