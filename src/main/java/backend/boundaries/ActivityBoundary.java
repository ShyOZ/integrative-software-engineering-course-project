package backend.boundaries;

import java.util.Date;
import java.util.Map;

import backend.activities.ActivityId;
import backend.instances.Instance;
import backend.instances.UserId;

public class ActivityBoundary {
	
	private ActivityId activityId;
	private String type;
	private Map<String, Instance> instance;
	private Date createdTimeStamp;
	private Map<String, UserId> invokedBy;
	private Map<String, Object> activityAtributes;
	
	public ActivityBoundary() {
		
	}

	public ActivityBoundary(ActivityId activityId, String type, Map<String, Instance> instance, Date createdTimeStamp,
			Map<String, UserId> invokedBy, Map<String, Object> activityAtributes) {
		super();
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimeStamp = createdTimeStamp;
		this.invokedBy = invokedBy;
		this.activityAtributes = activityAtributes;
	}

	public ActivityId getActivityId() {
		return activityId;
	}

	public void setActivityId(ActivityId activityId) {
		this.activityId = activityId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Instance> getInstance() {
		return instance;
	}

	public void setInstance(Map<String, Instance> instance) {
		this.instance = instance;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Map<String, UserId> getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(Map<String, UserId> invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getActivityAtributes() {
		return activityAtributes;
	}

	public void setActivityAtributes(Map<String, Object> activityAtributes) {
		this.activityAtributes = activityAtributes;
	}

	@Override
	public String toString() {
		return "ActivityBoundary [activityId=" + activityId + ", type=" + type + ", instance=" + instance
				+ ", createdTimeStamp=" + createdTimeStamp + ", invokedBy=" + invokedBy + ", activityAtributes="
				+ activityAtributes + "]";
	}
	
	
}
