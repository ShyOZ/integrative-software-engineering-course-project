package iob.logic;

import org.springframework.data.repository.CrudRepository;

import iob.data.ActivityEntity;

//TODO: Replace String with the proper ID object Type.
public interface UsersCrud extends CrudRepository<ActivityEntity, String> {

}
