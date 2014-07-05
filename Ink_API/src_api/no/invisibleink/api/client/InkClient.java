package no.invisibleink.api.client;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class InkClient {
	
	public static final String TAG = InkClient.class.getName();
	
	private static final String BASE_URL = "http://server.invisibleink.no/api/v1/";
	
	public static final String PARAMETER_NO_META = "no_meta";
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	public static final String HTTP_CONTENT_TYPE_JSON = "application/json";
	
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	private static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
		client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
	}
	
	public static void postJson(Context context, String url, JSONObject entity, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
		Log.d(TAG, "POST " + url + ":" + entity);
		post(context, url, new StringEntity(entity.toString()), HTTP_CONTENT_TYPE_JSON, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
	
	public static void setAuthorizationToken(String token) {
		client.addHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + token);		
	}
}
