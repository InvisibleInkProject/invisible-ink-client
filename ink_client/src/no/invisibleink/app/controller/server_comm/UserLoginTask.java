package no.invisibleink.app.controller.server_comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import no.invisibleink.app.controller.SessionManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * Represents an asynchronous login task used to authenticate the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
	
	private SessionManager mg;
	
	public UserLoginTask(SessionManager sm){
		super();
		this.mg = sm;
	}
	
	@Override
	protected Boolean doInBackground(String... params){
				
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(params[0]);

		//set up params:
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();  

		pairs.add(new BasicNameValuePair("grant_type","password"));  
		pairs.add(new BasicNameValuePair("username",params[1]));  
		pairs.add(new BasicNameValuePair("password",params[2]));  
		pairs.add(new BasicNameValuePair("client_id",mg.getClientId()));  
		pairs.add(new BasicNameValuePair("client_secret",mg.getClientSecret()));  
		pairs.add(new BasicNameValuePair("scope","read"));  
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,HTTP.UTF_8); //add necessary params to http post 
			request.setEntity(entity);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}  
	
		
		try {

			HttpResponse response = client.execute(request); //execute request
			int statusCode = response.getStatusLine().getStatusCode(); 
			
			if(statusCode == 200){ //Successful request
				//process response details
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				StringBuilder jsonContent = new StringBuilder();
				while((line = br.readLine()) != null){
					jsonContent.append(line);
				}
				br.close();
			
				JSONObject jo = new JSONObject(jsonContent.toString());
				
				//save tokens in shared Preferences:
				mg.login(jo.getString("access_token"), jo.getString("refresh_token"), jo.getString("expires_in"), params[1]);
				return true; //success
			
			}else if(statusCode == 400 || statusCode == 500){ //some kind of error
				return false; //failure
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
}