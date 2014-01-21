package no.invisibleink.core.server_comm;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PostInkTask extends AsyncTask<Object, Void, Boolean>{

	//TODO: globalise me ! 
	private String SERVER = "http://server.invisibleink.no/api/v1/message/";
	
//	private Context context;
	
//	public PostInkTask(Context c){
//		context = c;
//	}
	
	@Override
	protected Boolean doInBackground(Object... params) { //add Location to params ... 
		Location l = (Location) params[0];
		String message = (String) params[1];

		return create(l,message);
	}
	
	 @Override
	protected void onPostExecute(Boolean result) {
		String message = "";
		if (result) {
			message = "Ink successfully posted";
		} else {
			message = "an error occurred";
		}	 
//		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	

	public boolean create(Location location, String message){
		Log.d(this.getClass().getName(), "request: " + SERVER);		
		HttpClient client = new DefaultHttpClient();
			
		try {
			HttpPost request = new HttpPost(SERVER);
						
//			Gson gsonBuilder = new GsonBuilder().create();
			JSONObject obj = new JSONObject();
			obj.put("text", message);
			obj.put("radius", 50);
			obj.put("user_id", 1);
//			obj.put("created", "2000-01-01T00:00:00");
//			obj.put("updated", "2000-01-01T00:00:00");			
			
			
			if(location!=null){//TODO: remove me! 
				obj.put("location_lat", location.getLatitude());			
				obj.put("location_lon", location.getLongitude());
			}else{		
				obj.put("location_lat", 60.0);
				obj.put("location_lon", 10.0);
			}
			
			StringEntity ent = new StringEntity(obj.toString());
Log.d(this.getClass().getName(), "ent=" + ent.toString())	;		
			request.setHeader("content-type", "application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 201){
				return true;
			}
			
Log.d(this.getClass().getName(), "response: " + statusCode);				
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}	
		
		return false;
	}

}
