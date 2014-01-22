package no.invisibleink.core.server_comm;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PostInkTask extends AsyncTask<JSONObject, Void, Integer>{

	//TODO: globalise me ! 
	private String SERVER = "http://server.invisibleink.no/api/v1/message/";
	
	private Context context;
	
	public PostInkTask(Context c){
		context = c;
	}
	
	@Override
	protected Integer doInBackground(JSONObject... params) { //add Location to params ... 
		JSONObject obj = (JSONObject) params[0];
		HttpClient client = new DefaultHttpClient();
		
		try {
			HttpPost request = new HttpPost(SERVER);
			
			StringEntity ent = new StringEntity(obj.toString());
			ent.setContentType("application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			
			int statusCode = response.getStatusLine().getStatusCode();
			Log.d(this.getClass().getName(), "request: " + SERVER + obj.toString());
			
			return statusCode;

			//BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			//String line = "";
			//String responeEntityContent = "";
			//while((line = br.readLine()) != null){
			//	responeEntityContent += line;
			//}
			//br.close();
			//Log.d(this.getClass().getName(), responeEntityContent);	


		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}	
		
		return -1;
	}
	
	 @Override
	protected void onPostExecute(Integer statusCode) {
		if (statusCode == HttpURLConnection.HTTP_CREATED){
			Log.d(this.getClass().getName(), "response: " + statusCode);
			Toast.makeText(context, "Sucessfull (" + statusCode + ")", Toast.LENGTH_LONG).show();
		} else {
			Log.e(this.getClass().getName(), "response: " + statusCode);
			Toast.makeText(context, "Fail (" + statusCode + ")", Toast.LENGTH_LONG).show();
		}	 
	}

}
