package no.invisibleink.app.controller.server_comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import no.invisibleink.app.controller.SessionManager;
import no.invisibleink.app.controller.Settings;
import no.invisibleink.app.model.InkList;
import no.invisibleink.app.view.user.RegisterActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class RegistrationTask extends AsyncTask<String, Void, Boolean> {
	
	private RegisterActivity activity;

	@Override
	protected Boolean doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://server.invisibleink.no/api/v1/register/"; //(String) params[0];
		String jsonString = buildJson(params[1], params[2], params[3], params[4], params[5], params[6]);
		HttpPost request = new HttpPost(uri);
		
		try {
			StringEntity ent = new StringEntity(jsonString);
			ent.setContentType("application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);//.getStatusLine().getStatusCode();
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 201){
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				StringBuilder jsonContent = new StringBuilder();
				while((line = br.readLine()) != null){
					jsonContent.append(line);
				}
				br.close();
			
				JSONObject jo = new JSONObject(jsonContent.toString());
				
				SessionManager mg = new SessionManager(activity);
				mg.register(jo.getString("client_id"), jo.getString("client_secret"));
				return true;
								
			
			}else if(statusCode == 400 || statusCode == 500){
				return false;
			}
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		//Log.d(LOG, "request: " + Settings.API_URL + stringEntity);
		
			
		
		// TODO: attempt authentication against a network service.
		//activity.showProgress(true);
		//perform request while progress bar is showing
		
		//check response -> success = return true, else false (reminder for registration: switch(error) e.g. username/email already in use, ... 
		//throw exception instead of setting error info in onPostExecute ? (therefore remove LoginActivity)
		//SessionID ?! 
		//sessionID = "123456";
		return true;
	}

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
