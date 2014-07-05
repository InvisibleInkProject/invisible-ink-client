package no.invisibleink.api.model;

public class Registration {

	public static final String ENDPOINT = "register/";
	
	public static final String RESPONSE_CLIENT_ID = "client_id";
	public static final String RESPONSE_CLIENT_SECRET = "client_secret";
	
	public interface PostHandler {
		public void onSuccess(String clientID, String clientSecret);
		public void onFailure(int statusCode);
		public void onFailureUserAlreadyExits();
	}
}
