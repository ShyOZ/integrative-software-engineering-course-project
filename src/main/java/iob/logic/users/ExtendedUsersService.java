package iob.logic.users;

import java.util.List;

public interface ExtendedUsersService extends UsersService {
	public List<UserBoundary> getAllUsers(int size, int page, String domain, String email);
	public List<UserBoundary> getUsersByVersion(int version, int size, int page);
}
