package serviceRequests;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import entityClasses.Author;

public class deneme {

	static final String REST_URI = "http://localhost:9996/tubitakUzayIntern/";
	static final String ADD_PATH = "rest/getAuthor";

	public static void main(String[] args) {

		WebClient plainAddClient = WebClient.create(REST_URI);
		plainAddClient.path(ADD_PATH).path(9999999 + "/").accept("text/plain");
		String s = plainAddClient.get(String.class);

		System.out.println("author 33. : " + s);
		
		
		


	}

}
