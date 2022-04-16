package iob.logic.users;

import java.util.List;

public interface UsersService {
	public UserBoundary createUser(NewUserBoundary user);
	public UserBoundary login(String userDomain, String userEmail);
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update);
	@Deprecated
	public List<UserBoundary> getAllUsers();
	public void deleteAllUsers(String userDomain, String userEmail);
}
