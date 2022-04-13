package iob.logic.activities;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.ActivityEntity;
import iob.logic.instances.Instance;
import iob.logic.instances.UserId;

@Component
public class ActivityConverter {
	private ObjectMapper jackson;
	
	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}
	
	public ActivityEntity toEntity(ActivityBoundary boundary) {
		ActivityEntity entity = new ActivityEntity();
		
		entity.setactivityId(toEntity(boundary.getActivityId().getDomain(), boundary.getActivityId().getId()));
		if (boundary.getInvokedBy() != null)
			entity.setInvokedBy(toEntity(boundary.getInvokedBy()));
		if (boundary.getType() != null)
			entity.setType(boundary.getType());
		if (boundary.getInstance() != null)
			entity.setInstance(toEntity(boundary.getInstance()));
		if (boundary.getCreatedTimestamp() != null)
			entity.setCreatedTimestamp(boundary.getCreatedTimestamp());
		if (boundary.getActivityAttributes() != null)
			entity.setAttributes(toEntity(boundary.getActivityAttributes()));
		
		return entity;
	}

	public String toEntity(String activityDomain, String activityId) {
		return activityDomain + "/" + activityId;
	}
	
	public String toEntity(Map<String, ?> m) {
		try {
			return this.jackson.writeValueAsString(m);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public ActivityBoundary toBoundary(ActivityEntity entity) {
		ActivityBoundary boundary = new ActivityBoundary();
		
		boundary.setActivityId(activityIdToBoundary(entity.getactivityId()));
		boundary.setType(entity.getType());
		boundary.setInstance(instanceToBoundary(entity.getInstance()));
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setInvokedBy(invokedByToBoundary(entity.getInvokedBy()));
		boundary.setActivityAttributes(attributesToBoundary(entity.getAttributes()));
		
		return boundary;
	}
	
	public ActivityId activityIdToBoundary(String activityId) {
		
		String[] domainId = activityId.split("/");
		
		return new ActivityId(domainId[0], domainId[1]);
	}
	
	public Map<String, Instance> instanceToBoundary(String s) {
		try {
			return jackson.readValue(s, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, UserId> invokedByToBoundary(String s) {
		try {
			return jackson.readValue(s, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, Object> attributesToBoundary(String s) {
		try {
			return jackson.readValue(s, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
