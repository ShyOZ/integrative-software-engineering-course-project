package iob.logic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import iob.api.boundaries.NewUserBoundary;
import iob.api.boundaries.UserBoundary;
import iob.api.instances.UserId;

@Service
public class UsersServiceJPA implements UsersService {

	@Override
	public UserBoundary createUser(NewUserBoundary user) {
		UserBoundary newUser = new UserBoundary();
		newUser.setUsername(user.getUsername());
		newUser.setAvatar(user.getAvatar());
		newUser.setRole(user.getRole());
		newUser.setUserId(new UserId("2022b.Yaeli.Bar.Gimelstei", "yaeli@gmail.com"));
		
		//add user to data base
		
		return newUser;
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserBoundary newUser = new UserBoundary();
		newUser.setUsername(update.getUsername());
		newUser.setAvatar(update.getAvatar());
		newUser.setRole(update.getRole());
		newUser.setUserId(new UserId(userDomain, userEmail));
		//add user to data base
		return newUser;
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		List<UserBoundary> getAllUsers = new ArrayList<>();
		// add implementation here
		return getAllUsers;
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}
}
