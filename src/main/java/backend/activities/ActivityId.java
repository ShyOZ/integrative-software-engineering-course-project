package backend.activities;

import backend.DuetApplication;

public class ActivityId {
	private String domain = DuetApplication.DOMAIN;
	private String id;

	public ActivityId() {
		
	}

	public ActivityId(String email) {
		this.id = email;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return id;
	}

	public void setEmail(String email) {
		this.id = email;
	}

	@Override
	public String toString() {
		return "ActivityId [domain=" + domain + ", id=" + id + "]";
	}
	
}
