/*nsimon2
 *  a RESTful web service that returns city and state given a zipcode
 */

package assignment;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/zips")
public class Zip {
	
   @GET
	@Produces(MediaType.TEXT_PLAIN)	
	@Path("{newzip}")
	public String findSC(@PathParam("newzip") String newzip) {
		String state=null, city = null;
	System.out.println("inside find zips"+newzip);
	if (newzip.length() == 5) {				
    for (Map.Entry<String, String> entry : getMap().entrySet()) {
			if (entry.getKey().equals(newzip)) {
				String[] cityState = entry.getValue().split("-");
				city = cityState[0];
				state = cityState[1];
			
		}
	 }
	}
	return city+"-"+state;
	
     
	}
    public Map<String, String> getMap() {
 	Map<String, String> zipCityState = new HashMap<String, String>();
	zipCityState.put("22312", "Alexandria-VA");
	zipCityState.put("22030", "Fairfax-VA");
	zipCityState.put("22301", "Tysons Corner-MD");
	zipCityState.put("20148", "Ashburn-VA");
 	
	return zipCityState;
    }
	

}

