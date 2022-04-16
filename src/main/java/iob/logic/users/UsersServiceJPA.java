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

@Service
public class UsersServiceJPA implements ExtendedUsersService {
	private String configurableUser;
	private UsersCrud userCrud;
	private UserConverter userConverter;

	@Autowired
	public UsersServiceJPA(UsersCrud messageCrud, UserConverter messageConverter) {
		this.userCrud = messageCrud;
		this.userConverter = messageConverter;
	}
	
	@Value("${configurable.user.text:Default user}")
	public void setConfigurableUser(String configurableUser) {
		this.configurableUser = configurableUser;
	}
	
	@PostConstruct
	public void init (){
		System.err.println("configurableUser: " + this.configurableUser);
	}

	@Override
	@Transactional
	public UserBoundary createUser(NewUserBoundary user) {
		UserEntity entity = this.userConverter.toEntity(user);
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		UserEntity logged = getUserEntityById(this.userConverter.toEntity(new UserId(userDomain, userEmail)));
		return this.userConverter.toBoundary(logged);
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = getUserEntityById(this.userConverter.toEntity(new UserId(userDomain, userEmail)));
		entity.setAvatar(update.getAvatar());
		entity.setUserName(update.getUsername());
		entity.setRole(this.userConverter.toEntity(update.getRole()));
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
		if(entity.getRole().toString().equals(UserRoleLogic.ADMIN.toString().toLowerCase())) {
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
		if(entity.getRole().toString().equals(UserRoleLogic.ADMIN.toString().toLowerCase())) {
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
