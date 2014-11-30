package cn.cntv.cctv11.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebViewActivity extends BaseActivity{

	public static final String PARAM_URL = "url";
	
	public static final String PARAM_TITLE = "title";
	
	private WebView webView;
	
	private TextView titleView;
	
	private String title;
	
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		title = getIntent().getStringExtra(PARAM_TITLE);
		setContentView(R.layout.webview_layout);
		titleView = (TextView) findViewById(R.id.title);
		if(title != null){
			titleView.setText(title);
		}
		webView = (WebView) findViewById(R.id.webview);
		final View loading = findViewById(R.id.loading);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.loadUrl(getIntent().getStringExtra(PARAM_URL));
		webView.setWebViewClient(new WebViewClient() {
	        @Override
	        public void onPageFinished(WebView view, String url) {
	        	loading.setVisibility(View.GONE);
	        	if(title == null){
	        		titleView.setText(view.getTitle());
	        	}
	        	
	        }
	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        	loading.setVisibility(View.VISIBLE);
	        	super.onPageStarted(view, url, favicon);
	        }
	    });
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	
	
	public static void open(Context context,String title,String url){
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra(PARAM_TITLE, title);
		intent.putExtra(WebViewActivity.PARAM_URL,url);
		context.startActivity(intent);
	}
	
	public static void open(Context context,String url){
		open(context, null, url);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(webView.canGoBack()){
				webView.goBack();
			}else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	

	
}
