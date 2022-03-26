package backend.controllers;

import java.time.LocalDate;
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
import backend.instances.Gender;
import backend.instances.Name;
import backend.instances.Profile;
import backend.instances.Residence;
import backend.instances.UserId;

@RestController
public class ProfileController {
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER +"/profile",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
		boundary.setCreatedTimeStamp(new Date());
		return boundary;
	}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/profile/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceId") String instanceId,
			@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
	}
	
	@RequestMapping(
			method = RequestMethod.GET, 
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/profile/{instanceId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retriveProfile(@PathVariable("instanceId") String instanceId) {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("Domain", DuetApplication.DOMAIN);
		instanceIdMap.put("ID", UUID.randomUUID().toString());

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId", new UserId("Netta.Konsens@s.afeka.ac.il"));

		Name name = new Name("Neta", "Konsens");
		LocalDate dob = LocalDate.of(1997, 4, 2);
		Gender gender = Gender.FEMALE;
		Residence residence = new Residence("Israel", "Ness Ziona");
		String bio = "Software Engineering student";
		
		Profile profile = new Profile(name, dob, gender, residence, bio, null);

		InstanceBoundary instance = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(false),
				new Date(), userMap, location, profile.getInstanceAttributes());
		return instance;
	}
	
	@RequestMapping(
			method = RequestMethod.GET, 
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/profile", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllProfiles() {
		// MOCKUP implementation
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("Domain", DuetApplication.DOMAIN);
		instanceIdMap.put("ID", UUID.randomUUID().toString());

		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);

		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId", new UserId("Netta.Konsens@s.afeka.ac.il"));

		Profile p1 = new Profile();
		Profile p2 = new Profile();
		Profile p3 = new Profile();
		
		p1.setName(new Name("Moshe", "Cohen"));
		p2.setName(new Name("Dana", "Levi"));
		p3.setName(new Name("Tal", "Klein"));
		
		p1.setGender(Gender.MALE);
		p2.setGender(Gender.FEMALE);
		p3.setGender(Gender.MALE);
		
		p1.setResidence(new Residence("Israel", "Tel Aviv"));
		p2.setResidence(new Residence("Israel", "Jerusalem"));
		p3.setResidence(new Residence("Israel", "Ashdod"));
		
		Profile[] profiles = new Profile[] {p1, p2, p3};
		
		InstanceBoundary[] instances = new InstanceBoundary[3];
		for (int i = 0; i < 3; i++) {
			instances[i] = new InstanceBoundary(instanceIdMap, "txt", "txt_name", new Boolean(true),
					new Date(), userMap, location, profiles[i].getInstanceAttributes());
		}
	
		return instances;
	}
	
}
