package iob.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Activity Table
 * 
 * Instance ID | Type         | Attributes
 * InstaceId   | VARCHAR(255) | Map
 * */

@Entity
@Table(name = "INSTANCES_TABLE")
public class InstanceEntity {
	private String instanceId;
	private String type;
	//private Map<String, Object> attributes
	//private InstanceId instanceId

	public InstanceEntity() {

	}
	
	public InstanceEntity(String instanceId, String type) {
		this.instanceId = instanceId;
		this.type = type;
	}

	@Column(name="Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Id
	public String getinstanceId() {
		return instanceId;
	}

	public void setinstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
}
