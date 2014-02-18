package no.invisibleink.core.server_comm;

import java.net.HttpURLConnection;

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
			String stringEntity = (String) params[0];

			HttpPost request = new HttpPost(Settings.SERVER_URL);
			
			StringEntity ent = new StringEntity(stringEntity);
			ent.setContentType("application/json");
			request.setEntity(ent);
			
			HttpResponse response = client.execute(request);
			
			int statusCode = response.getStatusLine().getStatusCode();
			Log.d(this.getClass().getName(), "request: " + Settings.SERVER_URL + stringEntity);
			
			return statusCode;

		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Exception, " + e.getMessage());			
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
