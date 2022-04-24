package iob.logic;

import java.util.List;

import iob.logic.instances.InstanceBoundary;

public interface InstancesService {
	
	public InstanceBoundary createInstance(InstanceBoundary insatance);
	
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update);
	
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId);
	 
	public List<InstanceBoundary> getAllInstances();
	
	public void deleteAllInstances();
}