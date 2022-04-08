package iob.logic.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import iob.data.UserEntity;
import iob.logic.MessageNotFoundException;
import iob.logic.instances.UserId;

@Service
public class UsersServiceJPA implements UsersService {
	private String configurableUser;
	private UsersCrud userCrud;
	private userConverter userConverter;

	@Autowired
	public UsersServiceJPA(UsersCrud messageCrud, userConverter messageConverter) {
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
		UserEntity logged = getMessageEntityById(this.userConverter.toEntity(new UserId(userDomain, userEmail)));
		return this.userConverter.toBoundary(logged);
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = getMessageEntityById(this.userConverter.toEntity(new UserId(userDomain, userEmail)));
		entity.setAvatar(update.getAvatar());
		entity.setUserName(update.getUsername());
		entity.setRole(this.userConverter.toEntity(update.getRole()));
		entity = this.userCrud.save(entity);
		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers() {
		Iterable<UserEntity> iter = this.userCrud.findAll();
		List<UserBoundary> rv = new ArrayList<>();
		for (UserEntity msg : iter) {
			rv.add(this.userConverter.toBoundary(msg));
		}
		return rv;
	}

	@Override
	@Transactional
	public void deleteAllUsers() {
		this.userCrud.deleteAll();	
	}
	
	private UserEntity getMessageEntityById(String id) {
		Optional<UserEntity> op = this.userCrud.findById(id);

		if (op.isPresent()) {
			UserEntity entity = op.get();
			return entity;
		} else {
			throw new MessageNotFoundException("could not find message by id: " + id);
		}
	}
	
}
