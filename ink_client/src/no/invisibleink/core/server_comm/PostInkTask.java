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

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

public class PostInkTask extends AsyncTask<Object, Void, Boolean>{

	//TODO: globalise me ! 
	private String SERVER = "http://server.invisibleink.no/api/v1/message/";
	
	private Context context;
	
	public PostInkTask(Context c){
		context = c;
	}
	
	@Override
	protected Boolean doInBackground(Object... params) { //add Location to params ... 
		Location l = (Location)params[0];
		String message = (String)params[1];
		// TODO Auto-generated method stub
		return create(l,message);
	}
	
	 @Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		String message = "";
		if(result){message="Ink successfully posted";}else{message="an error occurred";}		 
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	

	public boolean create(Location location, String message){
		
		HttpClient client = new DefaultHttpClient();
			
		try {
			HttpPost request = new HttpPost(SERVER);
						
			JSONObject obj = new JSONObject();
			obj.put("text", message);
			
			
			if(location!=null){//TODO: remove me! 
				obj.put("location_lat", location.getLatitude());			
				obj.put("location_lon", location.getLongitude());
			}else{		
				obj.put("location_lat", 60.0);
				obj.put("location_lon", 10.0);
			}
			
			StringEntity ent = new StringEntity(obj.toString());
			
			request.setHeader("content-type", "application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			
			//StatusLine sl = response.getStatusLine();
			if (response.getStatusLine().getStatusCode()==201){
				return true;
			}
			
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally {
			client.getConnectionManager().shutdown();
		}	
		
		return false;
	}

}
