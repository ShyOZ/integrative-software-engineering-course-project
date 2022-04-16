package iob.rest_api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.logic.activities.ActivitiesService;
import iob.logic.activities.ActivityBoundary;
import iob.logic.instances.InstancesService;
import iob.logic.users.ExtendedUsersService;
import iob.logic.users.UserBoundary;

@RestController
public class AdminController {
	private InstancesService instanceService;
	private ExtendedUsersService userService;
	private ActivitiesService activitiesSrevice;
	
	@Autowired
	public void setInstanceService(InstancesService instanceService, 
			ExtendedUsersService userService, ActivitiesService activitiesSrevice ) {
		this.instanceService = instanceService;
		this.userService = userService;
		this.activitiesSrevice = activitiesSrevice;
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/users?userDomain={domain}&userEmail={email}")
	public void deleteAllUsers(
			@PathVariable("domain") String domain,
			@PathVariable("email") String email) {
		this.userService.deleteAllUsers(domain, email);
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/instances?userDomain={domain}&userEmail={email}")
	public void deleteAllInstances(
			@PathVariable("domain") String domain,
			@PathVariable("email") String email) {
		this.instanceService.deleteAllInstances();
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/activities?userDomain={domain}&userEmail={email}")
	public void deleteAllActivities(
			@PathVariable("domain") String domain,
			@PathVariable("email") String email) {
		this.activitiesSrevice.deleteAllActivities();
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/users?userDomain={domain}&userEmail={email}&size={size}&page={page}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> exportAllUsers(
			@PathVariable("domain") String domain,
			@PathVariable("email") String email,
			@PathVariable(name="size", required = false, value = "10") int size,
			@PathVariable(name="page", required = false, value = "0") int page) {
		return this.userService.getAllUsers(size,page, domain, email);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/activities?userDomain={domain}&userEmail={email}&size={size}&page={page}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActivityBoundary> exportAllActivities(
			@PathVariable("domain") String domain,
			@PathVariable("email") String email,
			@PathVariable(name="size", required = false, value = "10") int size,
			@PathVariable(name="page", required = false, value = "0") int page) {
		return this.activitiesSrevice.getAllActivities();
	}
}
