package no.invisibleink.core.server_comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import no.invisibleink.model.InkList;
import no.invisibleink.view.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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
			Log.d(this.getClass().getName(), "request: " + request.getURI());
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
			for(int i=0;i<ar.length();i++){
				GsonInk ink = gsonBuilder.fromJson(ar.get(i).toString(), GsonInk.class);
				inkList.add(ink.toInk());
			}
			return inkList;

		} catch (JsonSyntaxException e) {
			Log.w(this.getClass().getName(), "JsonSyntaxException, " + e.getMessage());			
	    } catch (Exception e) {
			Log.e(this.getClass().getName(), "Exception, " + e.getMessage());			
		} finally {
			client.getConnectionManager().shutdown();
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(InkList inkList) {
    	if (inkList != null) {
			Log.d(this.getClass().getName(), "received: " + inkList.size() + " inks");
			try {
				((MainActivity) mContext).onReceivedInkList(inkList);
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "onPostExecute " + e.getMessage());
			}
    	} else {
    		//TODO: When there is a current list it should perhaps retry later.
    		//      It should retry again when there is no current list
			Log.d(this.getClass().getName(), "received: FAILED");    		
    	}
    }
}
