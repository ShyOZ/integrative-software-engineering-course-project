package iob.api.instances;

import iob.DuetApplication;

public class UserId {
	private String domain = DuetApplication.DOMAIN;
	private String email;

	public UserId() {
		
	}

	public UserId(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "UserId [domain=" + domain + ", email=" + email + "]";
	}
	
	
}

