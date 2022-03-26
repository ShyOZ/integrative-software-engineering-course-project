package backend.controllers;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.ActivityBoundary;

@RestController
public class ActivityController {

	@RequestMapping(
			method = RequestMethod.POST,
			path = DuetApplication.API_PREFIX + DuetApplication.ACTIVITIES_HEADER,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public Object createInstance(@RequestBody ActivityBoundary boundary) {
		// MOCKUP implementation
		boundary.setActivityId(null);
		boundary.setCreatedTimeStamp(new Date());
		return boundary;
	}
}
