package iob.logic;

import org.springframework.data.repository.CrudRepository;

import iob.data.InstanceEntity;

//TODO: Replace String with the proper ID object Type.
public interface InstancesCrud extends CrudRepository<InstanceEntity, String> {

}
