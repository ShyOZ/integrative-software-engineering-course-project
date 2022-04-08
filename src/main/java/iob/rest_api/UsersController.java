package iob.rest_api;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.data.UserRole;
import iob.logic.instances.UserId;
import iob.logic.users.NewUserBoundary;
import iob.logic.users.User;
import iob.logic.users.UserBoundary;

@RestController
public class UsersController {

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "iob/users", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody NewUserBoundary newUser) {
		// MOCKUP implementation
		UserBoundary userBoundary = new UserBoundary(new UserId("2022b.Yaeli.bar.gimelshtei", "email@gmail.com"),
				UserRole.ADMIN, "user name check", "cool avatr");
		return userBoundary;
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "iob/users/login/{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userDomain") String userDomain, 
			@PathVariable("userEmail") String userEmail) {
		// MOCKUP implementation
		UserBoundary userBoundary = new UserBoundary(new UserId("2022b.Yaeli.bar.gimelshtei", "email@gmail.com"),
				UserRole.ADMIN, "user name check", "cool avatr");
		return userBoundary;
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
