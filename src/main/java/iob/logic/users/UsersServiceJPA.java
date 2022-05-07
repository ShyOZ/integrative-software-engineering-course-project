package iob.logic.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iob.data.UserEntity;
import iob.data.UserEntityId;
import iob.data.UserRole;
import iob.logic.BadRequestException;
import iob.logic.ExtendedUsersService;
import iob.logic.utility.ConfigProperties;
import iob.mongo_repository.UserRepository;

@Service
public class UsersServiceJPA implements ExtendedUsersService {
	private UserBoundary defaultUserBoundary;
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
		UserEntity entity = new UserEntity();
		entity.setUserId(this.userConverter.toEntity(user.getEmail(), this.domain));

		if (user.getAvatar() != null)
			entity.setAvatar(user.getAvatar());
		else
			throw new BadRequestException("avatar is missing");

		if (user.getUsername() != null)
			entity.setUserName(user.getUsername());
		else
			throw new BadRequestException("username is missing");

		if (user.getRole() != null)
			entity.setRole(UserRole.valueOf(user.getRole()));
		else
			throw new BadRequestException("role is missing");

		if (user.getEmail() != null)
			entity.setUserId(this.userConverter.toEntity(new UserId(user.getEmail(), this.domain)));
		else
			throw new BadRequestException("email is missing");

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
		throw new RuntimeException("deprecated method - use getAllUsers with paginayion instead");
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
			throw new UserNotFoundException("could not find message by id: " + id);
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
			// TODO: throw UNAUTHORIZED
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getUsersByVersion(int version, int size, int page) {
		return this.userRepo.findAllByVersion(version, PageRequest.of(page, size, Direction.ASC, "userId")).stream()
				.map(this.userConverter::toBoundary).collect(Collectors.toList());
	}
}
