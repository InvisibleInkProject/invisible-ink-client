package no.invisibleink.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

public class GetMessageTask extends AsyncTask<Object, Void, List<Ink>>{

	//TODO: globalise me ! 
		private String SERVER = "http://server.invisibleink.no/api/v1/message/60.0,10.0/";
	
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
			List<Ink> inks = new ArrayList<Ink>();
			
			JSONObject jo = new JSONObject(jsonContent.toString());
			JSONArray ar = jo.getJSONArray("objects");
			for(int i=0;i<ar.length();i++){
				GsonInk ink = new Gson().fromJson(ar.get(i).toString(), GsonInk.class);
				Log.d("ink:"+i,ink.toString());
			}
			
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//parse&return:		
		return null;
	}
}
