package iob.logic.users;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.UserEntity;
import iob.data.UserEntityId;
import iob.data.UserRole;
import iob.logic.ExtendedUsersService;
import iob.logic.customExceptions.BadRequestException;
import iob.logic.customExceptions.EntityNotFoundException;
import iob.logic.customExceptions.UnauthorizedRequestException;
import iob.logic.utility.ConfigProperties;
import iob.mongo_repository.UserRepository;

@Service
public class UsersServiceJPA implements ExtendedUsersService {
	private UserConverter userConverter;
	private String domain;
	private ConfigProperties configProperties;
	private UserRepository userRepo;

	@Autowired
	public UsersServiceJPA(UserConverter userConverter, ConfigProperties configProperties, UserRepository userRepo) {
		this.userConverter = userConverter;
		this.configProperties = configProperties;
		this.domain = this.configProperties.getApplicationDomain();
		this.userRepo = userRepo;
	}

	@Override
	@Transactional
	public UserBoundary createUser(NewUserBoundary user) {

		if (user.getEmail() == null)
			throw new BadRequestException("email is missing");

		if (user.getRole() == null)
			throw new BadRequestException("role is missing");

		if (user.getUsername() == null)
			throw new BadRequestException("username is missing");

		if (user.getAvatar() == null)
			throw new BadRequestException("avatar is missing");

		UserBoundary userBoundary = new UserBoundary(new UserId(user.getEmail(), domain),
				UserRole.valueOf(user.getRole()), user.getUsername(), user.getAvatar());

		UserEntity entity = userConverter.toBoundary(userBoundary);
		entity = this.userRepo.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		UserEntity logged = getUserEntityById(new UserEntityId(userEmail, userDomain));
		return this.userConverter.toBoundary(logged);
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = getUserEntityById(new UserEntityId(userEmail, userDomain));
		if (update.getAvatar() != null) {
			entity.setAvatar(update.getAvatar());
		}
		if (update.getUsername() != null) {
			entity.setUserName(update.getUsername());
		}
		if (update.getRole() != null) {
			entity.setRole(update.getRole());
		}
		entity = this.userRepo.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		throw new RuntimeException("deprecated method - use getAllUsers with pagination instead");
	}

	@Override
	@Transactional
	public void deleteAllUsers(String userDomain, String userEmail) {
		UserEntity entity = getUserEntityById(new UserEntityId(userEmail, userDomain));
		System.out.println(entity.toString());
		if (entity.getRole().equals(UserRole.ADMIN)) {
			this.userRepo.deleteAll();
		}
	}

	private UserEntity getUserEntityById(UserEntityId id) {
		Optional<UserEntity> op = this.userRepo.findById(id);

		if (op.isPresent()) {
			UserEntity entity = op.get();
			return entity;
		} else {
			throw new EntityNotFoundException("could not find user with id: " + id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(int size, int page, String domain, String email) {
		UserEntity entity = getUserEntityById(new UserEntityId(email, domain));
		if (entity.getRole().equals(UserRole.ADMIN)) {
			return this.userRepo.findAll(PageRequest.of(page, size, Direction.ASC, "userId")).stream() // Stream<MessageEntity>
					.map(this.userConverter::toBoundary) // Stream<Message>
					.collect(Collectors.toList()); // List<Message>
		} else {
			throw new UnauthorizedRequestException("user must be an admin to perform this action");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getUsersByVersion(int version, int size, int page) {
		return this.userRepo.findAllByVersion(version, PageRequest.of(page, size, Direction.ASC, "userId")).stream()
				.map(this.userConverter::toBoundary).collect(Collectors.toList());
	}
}
