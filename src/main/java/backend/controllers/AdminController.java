package backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.ActivityBoundary;
import backend.boundaries.UserBoundary;

@RestController
public class AdminController {

	@RequestMapping(
			method = RequestMethod.DELETE,
			path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER + DuetApplication.USERS_HEADER)
	public void deleteAllUsers() {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.DELETE,
			path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER + DuetApplication.INSTANCES_HEADER)
	public void deleteAllInstances() {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.DELETE,
			path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER + DuetApplication.ACTIVITIES_HEADER)
	public void deleteAllActivities() {
		// MOCKUP implementation
	}

	@RequestMapping(
			method = RequestMethod.GET,
			path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER + DuetApplication.USERS_HEADER,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers() {
		// MOCKUP implementation
		return new UserBoundary[]{new UserBoundary(), new UserBoundary()};
	}

	@RequestMapping(
			method = RequestMethod.GET,
			path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER + DuetApplication.ACTIVITIES_HEADER,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities() {
		// MOCKUP implementation
		return new ActivityBoundary[]{new ActivityBoundary(), new ActivityBoundary()};
	}
}
