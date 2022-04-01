package iob.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INSTANCES_TABLE")
public class InstanceEntity {
	//TODO: OVERRIDE BASIC IMPLEMENTATION
	private String instanceId;

	@Id
	public String getinstanceId() {
		return instanceId;
	}

	public void setinstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
