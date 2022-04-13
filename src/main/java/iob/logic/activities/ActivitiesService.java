package iob.logic.activities;

import java.util.List;

public interface ActivitiesService {
		
	public Object invokeActivity(ActivityBoundary activity);
	public List<ActivityBoundary> getAllActivities();
	public void deleteAllActivities();
} 
