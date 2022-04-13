package iob.logic.instances;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.InstanceEntity;

@Service
public class InstancesServiceJPA implements InstancesService {
	private String configurableInstance;
	private InstancesCrud instanceCrud;
	private InstanceConverter instanceConverter;

	@Autowired
	public InstancesServiceJPA(InstancesCrud instanceCrud, InstanceConverter instanceConverter) {
		this.instanceCrud = instanceCrud;
		this.instanceConverter = instanceConverter;
	}
	
	@Value("${configurable.instance.text:Default instance}")
	public void setConfigurableInstance(String configurableInstance) {
		this.configurableInstance = configurableInstance;
	}

	@PostConstruct
	public void init (){
		System.err.println("configurableInstance: " + configurableInstance);
	}
	
	@Override
	@Transactional
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		Map<String, String> instanceIdMap = new HashMap<String, String>();
		instanceIdMap.put("domain", "2022b.Yaeli.Bar.Gimelshtei");
		instanceIdMap.put("id", UUID.randomUUID().toString());
		instance.setInstanceId(instanceIdMap);
		
		instance.setCreatedTimestamp(new Date());
		
		if (instance.getActive() == null) {
			instance.setActive(true);
		}
		
		if (instance.getLocation() == null) {
			Map<String, Double> location = new HashMap<>();
			location.put("lat", 0.0);
			location.put("lng", 0.0);
			instance.setLocation(location);
		}
		
		InstanceEntity entity = instanceConverter.toEntity(instance);
		entity = instanceCrud.save(entity);
		return instanceConverter.toBoundary(entity);
	}
	
	@Override
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		InstanceEntity entity = getInstanceEntityById(instanceDomain, instanceId);
		
		if (update.getInstanceId() != null) {
			// do nothing
		}
		
		if (update.getType() != null) {
			entity.setType(update.getType());
		}
			
		if (update.getName() != null) {
			entity.setName(update.getName());
		}
		
		if (update.getActive() != null) {
			entity.setActive(update.getActive());
		}
		
		if (update.getCreatedTimestamp() != null) {
			// do nothing
		}
		
		if (update.getCreatedBy() != null) {
			String domain = update.getCreatedBy().get("UserId").getDomain();
			if (domain != null) {
				entity.setCreatedByDomain(domain);
			}
			
			String email = update.getCreatedBy().get("UserId").getEmail();
			if (email != null) {
				entity.setCreatedByEmail(email);
			}
		}
		
		if (update.getLocation() != null) {
			Double lat = update.getLocation().get("lat");
			if (lat != null) {
				entity.setLat(lat);
			}
			
			Double lng = update.getLocation().get("lng");
			if (lng != null) {
				entity.setLng(lng);
			}
		}
		
		if (update.getInstanceAttributes() != null) {
			entity.setAttributes(instanceConverter.toEntity(update.getInstanceAttributes()));
		}
	
		entity = instanceCrud.save(entity);
		
		return instanceConverter.toBoundary(entity);
	}

	private InstanceEntity getInstanceEntityById(String instanceDomain, String instanceId) {
		Optional<InstanceEntity> op = instanceCrud.findById(instanceConverter.toEntity(instanceDomain, instanceId)); 
			
		if (op.isPresent()) {
			InstanceEntity entity = op.get();
			return entity;
		} else {
			throw new InstanceNotFoundException("could not find instance by id: " + instanceDomain + ", " + instanceId);
		}
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		return instanceConverter.toBoundary(getInstanceEntityById(instanceDomain, instanceId));
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		Iterable<InstanceEntity> iter = instanceCrud.findAll();
		List<InstanceBoundary> rv = new ArrayList<>();
		for (InstanceEntity entity : iter) {
				rv.add(instanceConverter.toBoundary(entity));
		}
		
		return rv;
	}

	@Override
	public void deleteAllInstances() {
		instanceCrud.deleteAll();	
	}

}
