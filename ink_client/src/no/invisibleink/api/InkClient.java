package no.invisibleink.api;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class InkClient {
	
	private static final String BASE_URL = "http://server.invisibleink.no/api/v1/";
	
	public static final String PARAMETER_NO_META = "no_meta";
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	public static final String HTTP_CONTENT_TYPE_JSON = "application/json";

	
	private AsyncHttpClient client;
	
	public InkClient(final String token) {
		client = new AsyncHttpClient();
		client.addHeader(HTTP_HEADER_AUTHORIZATION, "OAuth " + token);
	}

	public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	private void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
		client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
	}
	
	public void postJson(Context context, String url, JSONObject entity, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
		this.post(context, url, new StringEntity(entity.toString()), HTTP_CONTENT_TYPE_JSON, responseHandler);
	}

	private String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
