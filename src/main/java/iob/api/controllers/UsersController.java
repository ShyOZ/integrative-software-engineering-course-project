package iob.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.api.boundaries.NewUserBoundary;
import iob.api.boundaries.UserBoundary;

@RestController
public class UsersController {

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "iob/users", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody NewUserBoundary newUser) {
		// MOCKUP implementation
		Map<String, String> userIdMap = new HashMap<String, String>();

		userIdMap.put("domain", "2022b.Yaeli.Bar.Gimelshtei");
		userIdMap.put("email", newUser.getEmail());

		UserBoundary user = new UserBoundary(userIdMap, newUser.getRole(), newUser.getUsername(), newUser.getAvatar());
		return user;
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "iob/users/login/{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userDomain") String userDomain, 
			@PathVariable("userEmail") String userEmail) {
		// MOCKUP implementation
		Map<String, String> userIdMap = new HashMap<String, String>();

		userIdMap.put("domain", userDomain);
		userIdMap.put("email", userEmail);

		UserBoundary user = new UserBoundary(userIdMap, "TESTER", "Shy_Ohev_Zion", "ShyOZ");
		return user;
	}

	@RequestMapping(
			method = RequestMethod.PUT, 
			path = "iob/users/{userDomain}/{userEmail}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody UserBoundary user, @PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		// MOCKUP implementation
		System.err.println("userDomain: " + userDomain + "\nuserEmail: " + userEmail);
	}
}
