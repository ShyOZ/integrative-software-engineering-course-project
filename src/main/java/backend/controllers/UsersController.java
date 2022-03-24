package backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.NewUserBoundary;
import backend.boundaries.UserBoundary;

@RestController
public class UsersController {

	@RequestMapping(method = RequestMethod.POST, path = DuetApplication.API_PREFIX
			+ DuetApplication.USERS_HEADER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary addNewUser(@RequestBody NewUserBoundary newUser) {
		Map<String, String> userIdMap = new HashMap<String, String>();

		userIdMap.put("domain", DuetApplication.DOMAIN.substring(1));
		userIdMap.put("email", newUser.getEmail());

		UserBoundary user = new UserBoundary(userIdMap, newUser.getRole(), newUser.getUserName(), newUser.getAvatar());
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, path = DuetApplication.API_PREFIX + DuetApplication.USERS_HEADER
			+ "/{userDomain}/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		Map<String, String> userIdMap = new HashMap<String, String>();

		userIdMap.put("domain", userDomain);
		userIdMap.put("email", userEmail);

		UserBoundary user = new UserBoundary(userIdMap, "TESTER", "Shy_Ohev_Zion", "ShyOZ");
		return user;
	}

	@RequestMapping(method = RequestMethod.PUT, path = DuetApplication.API_PREFIX + DuetApplication.USERS_HEADER
			+ "/{userDomain}/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody UserBoundary user, @PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {

		System.err.println("userDomain: " + userDomain + "\nuserEmail: " + userEmail);

	}
}
