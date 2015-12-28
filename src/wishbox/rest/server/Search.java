package wishbox.rest.server;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wishbox.nlp.QueryParser;
import wishbox.rest.client.RestRequester;
import wishbox.util.Util;

@Path("/")
public class Search {
	
	@Path("search")
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public String postSearch(String req) {
		Map<String, String> params = new HashMap<String, String>();
		String serviceProvider = QueryParser.parseQuery(req);
		params.put("service", serviceProvider);
		//Search json
		/*for(Entry<Object, Object> entry : Util.serviceProviderMap.get(serviceProvider)){
			
		}*/
		log(Util.serviceProviderMap);
		
		RestRequester.makeGetRequest(params);
        return "";
    }
	
	static void log(Object o) {
		System.out.println(o.toString());
	}
}
