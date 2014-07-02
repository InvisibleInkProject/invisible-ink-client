package no.invisibleink.api.model;

public class Login {

	public static final String ENDPOINT = "access_token";
	
	public static final String GRANT_TYPE = "grant_type";
	public static final String GRANT_TYPE_PASSWORD = "password";
	public static final String USERNAME = User.USERNAME;
	public static final String PASSWORD = User.PASSWORD;
	public static final String SCOPE = "scope";
	public static final String SCOPE_READ = "read";
	public static final String CLIENT_ID = Registration.RESPONSE_CLIENT_ID;
	public static final String CLIENT_SECRET = Registration.RESPONSE_CLIENT_SECRET;
	
	public static final String RESPONSE_ACCESS_TOKEN = "access_token";
	public static final String RESPONSE_REFRESH_TOKEN = "refresh_token";
	
	public interface PostHandler {
		public void onSuccess(String accessToken, String refreshToken);
		public void onFailure(int statusCode);
	}
}
