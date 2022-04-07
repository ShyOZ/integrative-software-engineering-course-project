package iob.logic.users;

import org.springframework.data.repository.CrudRepository;

import iob.data.UserEntity;

//TODO: Replace String with the proper ID object Type.
public interface UsersCrud extends CrudRepository<UserEntity, String> {

}
