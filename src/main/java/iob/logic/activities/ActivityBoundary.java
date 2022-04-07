package iob.logic.activities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import iob.logic.instances.Instance;
import iob.logic.instances.UserId;

public class ActivityBoundary {

	private ActivityId activityId;
	private String type;
	private Map<String, Instance> instance;
	private Date createdTimestamp;
	private Map<String, UserId> invokedBy;
	private Map<String, Object> activityAttributes;

	public ActivityBoundary() {
		this.instance = new HashMap<>();
		this.invokedBy = new HashMap<>();
		this.activityAttributes = new HashMap<>();
	}

	public ActivityBoundary(ActivityId activityId, String type, Map<String, Instance> instance, Date createdTimestamp,
			Map<String, UserId> invokedBy, Map<String, Object> activityAttributes) {
		this();
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.activityAttributes = activityAttributes;
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

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Map<String, UserId> getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(Map<String, UserId> invokedBy) {
		this.invokedBy = invokedBy;
	}

	public Map<String, Object> getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(Map<String, Object> activityAttributes) {
		this.activityAttributes = activityAttributes;
	}

	@Override
	public String toString() {
		return String.format(
				"ActivityBoundary [activityId=%s, type=%s, instance=%s, createdTimestamp=%s, invokedBy=%s, activityAttributes=%s]",
				activityId, type, instance, createdTimestamp, invokedBy, activityAttributes);
	}

}
