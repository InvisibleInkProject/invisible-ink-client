package no.invisibleink.app.controller.server_comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import no.invisibleink.app.controller.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * Represents an asynchronous login task used to register the user
 * parameters should contain the following strings in this order: uri, username, password, email, birthday, gender, nationality
 */
public class RegistrationTask extends AsyncTask<String, Void, Boolean> {
	
	private SessionManager mg;
	
	public RegistrationTask(SessionManager sm){
		super();
		this.mg = sm;
	}

	@Override
	protected Boolean doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(params[0]); //params[0] = uri
		
		try {
			StringEntity ent = new StringEntity(buildJson(params[1], params[2], params[3], params[4], params[5], params[6]));
			ent.setContentType("application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 201){ //success
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				StringBuilder jsonContent = new StringBuilder();
				while((line = br.readLine()) != null){
					jsonContent.append(line);
				}
				br.close();
			
				JSONObject jo = new JSONObject(jsonContent.toString());
				
				//store id and secret in shared preferences: 
				mg.register(jo.getString("client_id"), jo.getString("client_secret"));
				return true;
			
			}else if(statusCode == 400 || statusCode == 500){
				return false;
				//TODO: handle failure codes here and in the UI! 
			}
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * turns the given Strings into a json formatted string that can be send to the server 
	 * @param username username
	 * @param password password
	 * @param email email address
	 * @param birthday birthday
	 * @param gender gender
	 * @param nationality nationality
	 * @return json-String
	 */
	private String buildJson(String username, String password, String email, String birthday, String gender, String nationality){
		String json = "{";
		json += "\"username\":\"" + username + "\",";
		json += "\"password\":\"" + password + "\",";
		json += "\"email\":\"" + email + "\",";
		json += "\"birthday\":\"" + birthday + "\",";
		json += "\"gender\":\"" + gender + "\",";
		json += "\"nationality\":\"" + nationality + "\"}";
		
		return json;
	}
	

}
