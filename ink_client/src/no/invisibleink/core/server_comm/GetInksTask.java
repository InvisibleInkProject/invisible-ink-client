package no.invisibleink.core.server_comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import no.invisibleink.core.inks.Ink;
import no.invisibleink.core.inks.InkList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

public class GetInksTask extends AsyncTask<Object, Void, List<Ink>>{

	//TODO: globalise me ! 
	private final String SERVER = "http://server.invisibleink.no/api/v1/message/60.0,10.0/";
	
	@Override
	protected List<Ink> doInBackground(Object... params) {
		// TODO Auto-generated method stub
		//location (60.0, 10.0)
		Location l = (Location)params[0];
		
		// TODO Auto-generated method stub
		return request(l);
	}

	//check for necessity in ServerManager; only call this if needed!
	public List<Ink> request(Location location){
		//retrieve JSON
		HttpClient client = new DefaultHttpClient();
		
		
		//TODO: append location to url !!! 
		HttpGet request = new HttpGet(SERVER);
		try {
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			String line = "";
			StringBuilder jsonContent = new StringBuilder();
			while((line = br.readLine()) != null){
				jsonContent.append(line);
			}
			br.close();
			//Log.d("jsonResponse",jsonContent.toString());
			InkList inkList = new InkList();
			
			JSONObject jo = new JSONObject(jsonContent.toString());
			JSONArray ar = jo.getJSONArray("objects");
			for(int i=0;i<ar.length();i++){
				GsonInk ink = new Gson().fromJson(ar.get(i).toString(), GsonInk.class);
				Log.d("ink:"+i,ink.toString());
				// TODO: added line:
				inkList.add(ink);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//parse&return:		
		return null;
	}
}
