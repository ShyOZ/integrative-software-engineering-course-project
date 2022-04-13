package iob.rest_api;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.logic.activities.ActivitiesService;
import iob.logic.activities.ActivityBoundary;
import iob.logic.instances.InstancesService;
import iob.logic.users.UserBoundary;
import iob.logic.users.UsersService;

@RestController
public class AdminController {
	private InstancesService instanceService;
	private UsersService userService;
	private ActivitiesService activitiesSrevice;
	
	@Autowired
	public void setInstanceService(InstancesService instanceService, 
			UsersService userService, ActivitiesService activitiesSrevice ) {
		this.instanceService = instanceService;
		this.userService = userService;
		this.activitiesSrevice = activitiesSrevice;
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/users")
	public void deleteAllUsers() {
		this.userService.deleteAllUsers();
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/instances")
	public void deleteAllInstances() {
		this.instanceService.deleteAllInstances();
	}

	@RequestMapping(
			method = RequestMethod.DELETE, 
			path = "/iob/admin/activities")
	public void deleteAllActivities() {
		this.activitiesSrevice.deleteAllActivities();
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/users", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> exportAllUsers() {
		return this.userService.getAllUsers();
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/admin/activities", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActivityBoundary> exportAllActivities() {
		return this.activitiesSrevice.getAllActivities();
	}
}
