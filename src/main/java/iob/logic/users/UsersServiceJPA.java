package iob.logic.users;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import iob.logic.instances.UserId;

@Service
public class UsersServiceJPA implements UsersService {

	@Override
	public UserBoundary createUser(NewUserBoundary user) {
		UserBoundary newUser = new UserBoundary();
		
		
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
