package iob.logic.activities;

import org.springframework.data.repository.CrudRepository;

import iob.data.ActivityEntity;

//TODO: Replace String with the proper ID object Type.
public interface ActivitiesCrud extends CrudRepository<ActivityEntity, String> {

}
