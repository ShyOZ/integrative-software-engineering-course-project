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

	@RequestMapping(method = RequestMethod.DELETE, path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER
			+ "/users")
	public void deleteAllUsers() {
	}

	@RequestMapping(method = RequestMethod.DELETE, path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER
			+ "/instances")
	public void deleteAllInstances() {
	}

	@RequestMapping(method = RequestMethod.DELETE, path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER
			+ "/activities")
	public void deleteAllActivities() {
	}

	@RequestMapping(method = RequestMethod.GET, path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER
			+ "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers() {
		return new UserBoundary[]{new UserBoundary(), new UserBoundary()};
	}

	@RequestMapping(method = RequestMethod.GET, path = DuetApplication.API_PREFIX + DuetApplication.ADMIN_HEADER
			+ "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] exportAllActivities() {
		return new ActivityBoundary[]{new ActivityBoundary(), new ActivityBoundary()};
	}
}
