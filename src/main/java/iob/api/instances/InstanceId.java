package iob.api.instances;

import iob.DuetApplication;

public class InstanceId {
	private String domain = DuetApplication.DOMAIN;
	private String id;

	public InstanceId() {
		
	}

	public InstanceId(String email) {
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
		return "InstanceId [domain=" + domain + ", id=" + id + "]";
	}
}
