package no.invisibleink.core.server_comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.UnknownHostException;

import no.invisibleink.helper.DatabaseHelper;
import no.invisibleink.model.Ink;
import no.invisibleink.model.InkList;
import no.invisibleink.view.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Task to get inks from server
 *
 */
public class GetInksTask extends AsyncTask<URI, Void, InkList>{
	
	private static final String LOG = "GetInksTask";	
	
	private Context mContext;	
	
	public GetInksTask(Context context) {
		super();
		this.mContext = context;
	}
	
	/**
	 * Check for necessity in ServerManager; only call this if needed!
	 * 
	 * @param URI request URL
	 * @return List with inks form server or null
	 */
	@Override
	protected InkList doInBackground(URI ... uris) {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet request = new HttpGet(uris[0]);	
			HttpResponse response = client.execute(request);
			Log.d(LOG, "request: " + request.getURI());
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			StringBuilder jsonContent = new StringBuilder();
			while((line = br.readLine()) != null){
				jsonContent.append(line);
			}
			br.close();
			
			InkList inkList = new InkList();
			JSONObject jo = new JSONObject(jsonContent.toString());
			JSONArray ar = jo.getJSONArray("objects");
			Gson gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			
			DatabaseHelper db = new DatabaseHelper(mContext);
			db.clearTableInk();

			for(int i=0;i<ar.length();i++){
				Ink ink = gsonBuilder.fromJson(ar.get(i).toString(), GsonInk.class).toInk();
				inkList.add(ink);
				db.insertInk(ink);
			}
			return inkList;

		} catch (UnknownHostException e) {			
			Log.i(LOG, "UnknownHostException, no internet connection");			
		} catch (JsonSyntaxException e) {
			Log.w(LOG, "JsonSyntaxException, " + e.getMessage());			
	    } catch (JSONException e) {
			Log.w(LOG, "JSONException, " + e.getMessage());				    	
	    } catch (Exception e) {
			Log.e(LOG, "Exception, " + e.getClass().getName() + e.getMessage());			
		} finally {
			client.getConnectionManager().shutdown();
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(InkList inkList) {
		((MainActivity) mContext).onReceivedInkList(inkList);
    }
}
