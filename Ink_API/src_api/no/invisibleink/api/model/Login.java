package no.invisibleink.api.model;

public class Login {

	public static final String ENDPOINT = "access_token/";
	
	public static final String HEADER_GRANT_TYPE = "grant_type";
	public static final String GRANT_TYPE_PASSWORD = "password";
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	public static final String HEADER_USERNAME = User.USERNAME;
	public static final String HEADER_PASSWORD = User.PASSWORD;
	public static final String HEADER_SCOPE = "scope";
	public static final String SCOPE_READ = "read";
	public static final String HEADER_CLIENT_ID = Registration.RESPONSE_CLIENT_ID;
	public static final String HEADER_CLIENT_SECRET = Registration.RESPONSE_CLIENT_SECRET;
	public static final String HEADER_REFRESH_TOKEN = GRANT_TYPE_REFRESH_TOKEN;
	
	public static final String RESPONSE_ACCESS_TOKEN = "access_token";
	public static final String RESPONSE_REFRESH_TOKEN = "refresh_token";
	public static final String RESPONSE_EXPIRES_IN = "expires_in";
	
	public interface PostHandler {
		public void onSuccess(String accessToken, String refreshToken, String expires_in);
		public void onFailure(int statusCode);
		public void onFailureInvalid();
	}
}
