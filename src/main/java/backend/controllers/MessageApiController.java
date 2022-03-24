package backend.controllers;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.InstanceBoundary;
import backend.instances.Message;
import backend.instances.Profile;
import backend.instances.UserId;

@RestController
public class MessageApiController {
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER +"/message",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
		boundary.setCreatedTimeStamp(new Date());
		return boundary;
	}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/message/{instanceDomain}/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceDomain") String instanceDomain, 
			@PathVariable("instanceId") String instanceId, @RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/message/{instanceDomain}/{instanceId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveMessage(@PathVariable("instanceId") String instanceId) {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("Domain", DuetApplication.DOMAIN);
		instanceIdMap.put("ID", UUID.randomUUID().toString());

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId", new UserId("sorany123@gmail.com"));

		Message m1 = new Message(new Profile(1), new Profile(2), "helloWorld");

		InstanceBoundary instance = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(true),
				new Date(), userMap, location, m1.getInstanceAttributes());
		return instance;
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER+ "/message", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllMessage() {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("Domain", DuetApplication.DOMAIN);
		instanceIdMap.put("ID", UUID.randomUUID().toString());

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId", new UserId("sorany123@gmail.com"));

		Message m1 = new Message(new Profile(1), new Profile(2), "helloWorld");

		InstanceBoundary instance = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(true),
				new Date(), userMap, location, m1.getInstanceAttributes());
		InstanceBoundary[] instanceboundry = { instance };
		return instanceboundry;
	}
}