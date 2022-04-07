package iob.logic.instances;

import org.springframework.beans.factory.annotation.Value;

public class InstanceId {
	private String domain;
	private String id;

	public InstanceId() {

	}

	public InstanceId(String email) {
		this.id = email;
	}

	public String getDomain() {
		return domain;
	}

	@Value("{spring.application.name}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "InstanceId [domain=" + domain + ", id=" + id + "]";
	}
}
