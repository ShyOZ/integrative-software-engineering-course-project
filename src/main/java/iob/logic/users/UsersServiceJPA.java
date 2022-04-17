package iob.logic.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iob.data.UserEntity;
import iob.data.UserRole;

@Service
public class UsersServiceJPA implements ExtendedUsersService {
	private String configurableUser;
	private User configurable;
	private UsersCrud userCrud;
	private UserConverter userConverter;
	private String domain;

	@Autowired
	public UsersServiceJPA(UsersCrud messageCrud, UserConverter messageConverter,
			@Value("${spring.application.name}") String domain) {
		this.userCrud = messageCrud;
		this.userConverter = messageConverter;
		this.domain = domain;
	}
	
	@Value("${configurable.user.text:Default user}")
	public void setConfigurableUser(String configurableUser) {
		this.configurableUser = configurableUser;
		this.configurable = toUser(this.configurableUser);
	}
	
	private User toUser(String configurableUser) {
		String[] userStr = configurableUser.split("/");
		return new User(new UserId(userStr[0],this.domain), UserRole.valueOf(userStr[1]), userStr[2], userStr[3]);
	}

	@PostConstruct
	public void init (){
		System.err.println("configurableUser: " + this.configurableUser);
	}

	@Override
	@Transactional
	public UserBoundary createUser(NewUserBoundary user) {
		UserEntity entity = new UserEntity();
		entity.setUserId(this.userConverter.toEntity(domain, user.getEmail()));
		
		if(user.getAvatar() != null) 
			entity.setAvatar(user.getAvatar());
		else 
			entity.setAvatar(this.configurable.getAvatar());
		
		if(user.getUsername() != null) 
			entity.setUserName(user.getUsername());
		else 
			entity.setUserName(this.configurable.getUsername());
		
		if(user.getRole() != null) 
			entity.setRole(UserRole.valueOf(user.getRole()));
		else 
			entity.setRole(this.configurable.getRole());
		
		if(user.getEmail() != null)
			entity.setUserId(this.userConverter.toEntity(new UserId(user.getEmail(), this.domain)));
		else
			entity.setUserId(this.userConverter.toEntity(this.configurable.getUserId()));
		
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		UserEntity logged = getUserEntityById(this.userConverter.toEntity(new UserId(userEmail, userDomain)));
		return this.userConverter.toBoundary(logged);
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = getUserEntityById(this.userConverter.toEntity(new UserId(userEmail, userDomain)));
		if (update.getAvatar() != null) {
			entity.setAvatar(update.getAvatar());
		}
		if (update.getUsername() != null) {
			entity.setUserName(update.getUsername());
		}
		if (update.getRole() != null) {
			entity.setRole(update.getRole());
		}
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		throw new RuntimeException("deprecated method - use getAllUsers with paginayion instead");
	}

	@Override
	@Transactional
	public void deleteAllUsers(String userDomain, String userEmail) {
		UserEntity entity = getUserEntityById(this.userConverter.toEntity(new UserId(userDomain, userEmail)));
		if(entity.getRole().toString().equals(UserRole.ADMIN.toString())) {
			this.userCrud.deleteAll();	
		}
	}
	
	private UserEntity getUserEntityById(String id) {
		Optional<UserEntity> op = this.userCrud.findById(id);

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
		UserEntity entity = getUserEntityById(this.userConverter.toEntity(new UserId(domain, email)));
		if(entity.getRole().toString().equals(UserRole.ADMIN.toString())) {
			return this.userCrud
					.findAll(PageRequest.of(page, size, Direction.ASC, "userId"))
					.stream() // Stream<MessageEntity>
					.map(this.userConverter::toBoundary) // Stream<Message>
					.collect(Collectors.toList()); // List<Message>
		}
		else {
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getUsersByVersion(int version, int size, int page) {
		return this.userCrud
				.findAllByVersion(version, PageRequest.of(page, size, Direction.ASC, "userId"))
				.stream()
				.map(this.userConverter::toBoundary)
				.collect(Collectors.toList());
	}
}
