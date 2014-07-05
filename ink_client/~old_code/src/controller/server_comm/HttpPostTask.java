package no.invisibleink.app.controller.server_comm;

import java.net.HttpURLConnection;

import no.invisibleink.app.controller.Settings;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Tasks to push one ink to the server.
 */
public class HttpPostTask extends AsyncTask<String, Void, Integer> {

	private static final String LOG = "HttpPostTask";
	
	/**
	 * Context to show result in a toast.
	 */
	private Context context;
	
	public HttpPostTask(Context c){
		context = c;
	}
	
	@Override
	protected Integer doInBackground(String ... params) {
		HttpClient client = new DefaultHttpClient();
		
		try {
			String uri = (String) params[0];
			String stringEntity = (String) params[1];

			HttpPost request = new HttpPost(uri);
			
			StringEntity ent = new StringEntity(stringEntity);
			ent.setContentType("application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			
			int statusCode = response.getStatusLine().getStatusCode();
			Log.d(LOG, "request: " + Settings.API_URL + stringEntity);
			
			return statusCode;

		} catch (Exception e) {
			Log.e(LOG, "Exception, " + e.getMessage());			
		} finally {
			client.getConnectionManager().shutdown();
		}	
		
		return -1;
	}
	
	 @Override
	protected void onPostExecute(Integer statusCode) {
		if (statusCode == HttpURLConnection.HTTP_CREATED){
			Log.d(LOG, "response: " + statusCode);
			Toast.makeText(context, "Sucessfull (" + statusCode + ")", Toast.LENGTH_LONG).show();
		} else {
			Log.e(LOG, "response: " + statusCode);
			Toast.makeText(context, "Fail (" + statusCode + ")", Toast.LENGTH_LONG).show();
		}	 
	}

}
