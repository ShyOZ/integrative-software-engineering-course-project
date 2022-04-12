package iob.rest_api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.logic.activities.ActivityBoundary;
import iob.logic.activities.ActivityId;
import iob.logic.instances.InstancesService;
import iob.logic.instances.UserId;
import iob.logic.users.UserBoundary;
import iob.logic.users.UserRoleLogic;

@RestController
public class AdminController {
	private InstancesService instanceService;
	
	@Autowired
	public void setInstanceService(InstancesService instanceService) {
		this.instanceService = instanceService;
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/users")
	public void deleteAllUsers() {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/instances")
	public void deleteAllInstances() {
		instanceService.deleteAllInstances();
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/activities")
	public void deleteAllActivities() {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/users", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers() {
		UserBoundary ub1 = new UserBoundary(new UserId("2022b.Yaeli.bar.gimelshtei", "email@gmail.com"),
				UserRoleLogic.ADMIN, "user name check", "cool avatr");
		UserBoundary ub2 = new UserBoundary(new UserId("2022b.Yaeli.bar.gimelshtei", "email@gmail.com"),
				UserRoleLogic.ADMIN, "user name check", "cool avatr");
		return new UserBoundary[] { ub1, ub2 };
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/activities", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities() {
		ActivityBoundary ab = new ActivityBoundary();
		ab.setActivityId(new ActivityId("2022b.Yaeli.Bar.Gimelstei", "123"));
		ab.setType("demo activity");
		ab.setCreatedTimestamp(new Date());
		ab.setInstance(null);

		UserId uid = new UserId("2022b.Yaeli.Bar.Gimelstei", "ShyOZ@iob.com");
		uid.setDomain("2022b.Yaeli.Bar.Gimelstei");
		Map<String, UserId> invoker = new HashMap<>();
		invoker.put("userId", uid);
		ab.setInvokedBy(invoker);

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("key1", "what a wonderful value");
		Map<String, String> subKey = new HashMap<>();
		subKey.put("key2subkey1", "a nested json");
		attributes.put("key2", subKey);
		ab.setActivityAttributes(attributes);

		return new ActivityBoundary[] { ab };
	}
}
