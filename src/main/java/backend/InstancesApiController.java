package backend;

import java.nio.charset.*; // for generating random instance ID

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstancesApiController {
	
	private static final String HEADER = "instances";
	
	@RequestMapping(
		method = RequestMethod.GET,
		path= DuetApplication.API_PREFIX + HEADER + "/" + DuetApplication.DOMAIN + "/{matchId}",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary getMatchById (
			@PathVariable("matchId") String matchId) {
		// TODO need to fill
		return null;
	}
}
