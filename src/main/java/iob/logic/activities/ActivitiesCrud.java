package iob.logic.activities;

import org.springframework.data.repository.CrudRepository;

import iob.data.ActivityEntity;

public interface ActivitiesCrud extends CrudRepository<ActivityEntity, String> {
}
