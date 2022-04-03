package iob.api.controllers;

import java.util.ArrayList;
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

import iob.api.boundaries.InstanceBoundary;
import iob.api.instances.Chat;
import iob.api.instances.Message;
import iob.api.instances.Profile;
import iob.api.instances.UserId;

@RestController
public class GeneralInstanceController {

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "/iob/instances", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("domain", "2022b.Yaeli.Bar.Gimelshtei");
		instanceIdMap.put("id", UUID.randomUUID().toString());
		boundary.setInstanceId(instanceIdMap);
		boundary.setCreatedTimestamp(new Date());
		return boundary;
	}

	@RequestMapping(
			method = RequestMethod.PUT, 
			path = "/iob/instances/{instanceDomain}/{instanceId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId, @RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/instances/{instanceDomain}/{instanceId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId) {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("domain", instanceDomain);
		instanceIdMap.put("id", instanceId);

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId", new UserId("2022b.Yaeli.Bar.Gimelstei", "sorany123@gmail.com"));

		Message m1 = new Message(new Profile(), new Profile(), "helloWorld");
		Message m2 = new Message(new Profile(), new Profile(), "helloWorld2");
		ArrayList<Message> lstMessages = new ArrayList<Message>();
		lstMessages.add(m1);
		lstMessages.add(m2);

		Chat chat = new Chat(lstMessages);

		InstanceBoundary instance = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(true),
				new Date(), userMap, location, chat.getInstanceAttributes());
		return instance;
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/instances", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("domain", "2022b.Yaeli.Bar.Gimelshtei");
		instanceIdMap.put("id", UUID.randomUUID().toString());

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();

		Message m1 = new Message(new Profile(), new Profile(), "helloWorld");
		Message m2 = new Message(new Profile(), new Profile(), "helloWorld2");
		ArrayList<Message> lstMessages = new ArrayList<Message>();
		lstMessages.add(m1);
		lstMessages.add(m2);

		Chat chat = new Chat(lstMessages);

		InstanceBoundary instance = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(true),
				new Date(), userMap, location, chat.getInstanceAttributes());
		InstanceBoundary[] instanceboundry = { instance };
		return instanceboundry;
	}

}
