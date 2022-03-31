package iob.api.boundaries;

import java.util.Date;
import java.util.Map;

import iob.api.instances.UserId;

public class InstanceBoundary {
	
	private Map<String, String> instanceId;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimeStamp;
	private Map<String, UserId> createdBy ;
	private Map<String, Double> location;
	private Map<String, Object> instanceAttributes;
	
	public InstanceBoundary() {
		
	}
	
	public InstanceBoundary(Map<String, String> instanceId, String type, String name, Boolean active,
			Date createdTimeStamp, Map<String, UserId> createdBy, Map<String, Double> location,
			Map<String, Object> instanceAttributes) {
		super();
		this.instanceId = instanceId;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimeStamp = createdTimeStamp;
		this.createdBy = createdBy;
		this.location = location;
		this.instanceAttributes = instanceAttributes;
	}

	public Map<String, String> getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Map<String, String> instanceId) {
		this.instanceId = instanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Map<String, UserId> getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Map<String, UserId> createdBy) {
		this.createdBy = createdBy;
	}

	public Map<String, Double> getLocation() {
		return location;
	}

	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}

	public Map<String, Object> getInstanceAttributes() {
		return instanceAttributes;
	}

	public void setInstanceAttributes(Map<String, Object> instanceAttributes) {
		this.instanceAttributes = instanceAttributes;
	}

	@Override
	public String toString() {
		return "InstanceBoundary [instanceId=" + instanceId + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimeStamp=" + createdTimeStamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", instanceAttributes=" + instanceAttributes + "]";
	}
	
	
	
	
}
