package no.invisibleink.app.view.fragment;

import no.invisibleink.app.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MapViewFragment extends Fragment {

	public static final String PAGE = "file:///android_asset/mapView.html";	
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.fragment_section_map,	container, false);

		WebView mapWebView = (WebView) rootView.findViewById(R.id.webview_map);
//        mapWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true); // enable viewport meta tag
//        webSettings.setLoadWithOverviewMode(true);
        mapWebView.loadUrl(PAGE);
				
		return rootView;
	}

}
