package iob.logic.instances;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.InstanceEntity;
import iob.logic.users.UserId;

@Component
public class InstanceConverter {
	private ObjectMapper jackson;

	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}

	public InstanceEntity toEntity(InstanceBoundary boundary) {
		InstanceEntity entity = new InstanceEntity();

		entity.setInstanceId(toEntity(boundary.getInstanceId().get("domain"), boundary.getInstanceId().get("id")));

		if (boundary.getType() != null) {
			entity.setType(boundary.getType());
		}

		if (boundary.getName() != null) {
			entity.setName(boundary.getName());
		}

		if (boundary.getActive() != null) {
			entity.setActive(boundary.getActive());
		}

		entity.setCreatedTimestamp(boundary.getCreatedTimestamp());

		if (boundary.getCreatedBy() != null) {
			String domain = boundary.getCreatedBy().get("id").getDomain();
			if (domain != null) {
				entity.setCreatedByDomain(domain);
			}

			String email = boundary.getCreatedBy().get("id").getEmail();
			if (email != null) {
				entity.setCreatedByEmail(email);
			}
		}

		if (boundary.getLocation() != null) {
			Double lat = boundary.getLocation().get("lat");
			if (lat != null) {
				entity.setLat(lat);
			}

			Double lng = boundary.getLocation().get("lng");
			if (lng != null) {
				entity.setLng(lng);
			}
		}

		if (boundary.getInstanceAttributes() != null) {
			entity.setAttributes(toEntity(boundary.getInstanceAttributes()));
		}

		return entity;
	}

	public String toEntity(String instanceDomain, String instanceId) {
		return instanceDomain + "/" + instanceId;
	}

	public String toEntity(Map<String, Object> instanceAttributes) {
		try {
			return this.jackson.writeValueAsString(instanceAttributes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public InstanceBoundary toBoundary(InstanceEntity entity) {
		InstanceBoundary boundary = new InstanceBoundary();

		boundary.setInstanceId(toBoundary(entity.getInstanceId()));
		boundary.setType(entity.getType());
		boundary.setName(entity.getName());
		boundary.setActive(entity.getActive());
		boundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		boundary.setCreatedBy(toBoundary(entity.getCreatedByDomain(), entity.getCreatedByEmail()));
		boundary.setLocation(toBoundary(entity.getLat(), entity.getLng()));
		if (entity.getAttributes() != null) {
			boundary.setInstanceAttributes(toBoundaryFromJsonString(entity.getAttributes()));
		}
		
		return boundary;
	}
	
	private Map<String, Double> toBoundary(double lat, double lng) {
		Map<String, Double> location = new HashMap<String, Double>();
		location.put("lat", lat);
		location.put("lng", lng);
		
		return location;
	}

	private Map<String, UserId> toBoundary(String createdByDomain, String createdByEmail) {
		Map<String, UserId> userMap = new HashMap<String, UserId>();
		userMap.put("UserId", new UserId(createdByDomain, createdByEmail));
		
		return userMap;
	}

	public Map<String, String> toBoundary(String instanceId){
		Map<String, String> instanceIdMap = new HashMap<String, String>();
		
		String[] domainId = instanceId.split("/");
		instanceIdMap.put("domain", domainId[0]);
		instanceIdMap.put("id", domainId[1]);
		
		return instanceIdMap;
	}
	
	public Map<String, Object> toBoundaryFromJsonString(String json){
		try {
			return this.jackson.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
