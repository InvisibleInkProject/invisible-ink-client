package no.invisibleink.core.server_comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import no.invisibleink.core.InkWell;
import no.invisibleink.model.InkList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GetInksTask extends AsyncTask<URI, Void, InkList>{
	
	/**
	 * Check for necessity in ServerManager; only call this if needed!
	 * 
	 * @param URI request URL
	 * @return List with inks form server or null
	 */

	@Override
	protected InkList doInBackground(URI ... uris) {		
		try {
			HttpGet request = new HttpGet(uris[0]);			
			Log.d(this.getClass().getName(), "request: " + request.getURI());

			HttpResponse response = new DefaultHttpClient().execute(request);
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
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    protected void onPostExecute(InkList inkList) {
    	if (inkList != null) {
			Log.d(this.getClass().getName(), "received: " + inkList.size() + " inks");
	        InkWell.getInstance().setInkList(inkList);
    	} else {
			Log.d(this.getClass().getName(), "received: FAILED");    		
    	}
    }
}
