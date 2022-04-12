package iob.rest_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.instances.InstanceBoundary;
import iob.logic.instances.InstancesService;

@RestController
public class GeneralInstanceController {
	private InstancesService instanceService;
	
	@Autowired
	public void setInstanceService(InstancesService instanceService) {
		this.instanceService = instanceService;
	}

	@RequestMapping(
			method = RequestMethod.POST, 
			path = "/iob/instances", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary boundary) {
		return instanceService.createInstance(boundary);
	}

	@RequestMapping(
			method = RequestMethod.PUT, 
			path = "/iob/instances/{instanceDomain}/{instanceId}", 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId, @RequestBody InstanceBoundary boundary) {
		instanceService.updateInstance(instanceDomain, instanceId, boundary);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/instances/{instanceDomain}/{instanceId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary retrieveInstance(@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceId) {
		return instanceService.getSpecificInstance(instanceDomain, instanceId);
	}

	@RequestMapping(
			method = RequestMethod.GET, 
			path = "/iob/instances", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {
		return instanceService.getAllInstances().toArray(new InstanceBoundary[0]);
	}

}
