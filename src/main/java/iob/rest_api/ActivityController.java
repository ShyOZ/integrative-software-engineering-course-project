package iob.rest_api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.logic.activities.ActivitiesService;
import iob.logic.activities.ActivityBoundary;


@RestController
public class ActivityController {
	private ActivitiesService activitiesSrevice;
	
	@Autowired
	public void setActivityService(ActivitiesService activitiesSrevice ) {
		this.activitiesSrevice = activitiesSrevice;
	}

	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/activities",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public Object createActivityInstance(@RequestBody ActivityBoundary boundary) {
		return this.activitiesSrevice.invokeActivity(boundary);
	}
}
