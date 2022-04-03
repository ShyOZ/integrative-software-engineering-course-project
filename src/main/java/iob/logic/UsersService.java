package iob.logic;

import java.util.List;

import iob.api.boundaries.NewUserBoundary;
import iob.api.boundaries.UserBoundary;

public interface UsersService {
	public UserBoundary createUser(NewUserBoundary user);
	public UserBoundary login(String userDomain, String userEmail);
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update);
	public List<UserBoundary> getAllUsers();
	public void deleteAllUsers();
}
