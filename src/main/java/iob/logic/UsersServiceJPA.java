package iob.logic;

import java.util.List;
import org.springframework.stereotype.Service;
import iob.api.boundaries.NewUserBoundary;
import iob.api.boundaries.UserBoundary;

@Service
public class UsersServiceJPA implements UsersService {

	@Override
	public UserBoundary createUser(NewUserBoundary user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}
	//TODO IMPLEMENT!!!	
}
