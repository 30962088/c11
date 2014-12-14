package cn.cntv.cctv11.android.fragment.network;


import org.apache.http.Header;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.*;

public abstract class BaseClient implements HttpResponseHandler {
	
	public static final String BASE_URL = "http://cctv11news.1du1du.com/";

	public static String getImage(String filename,String format){
		
		return BASE_URL+"cctv11/getTheImage?fileName="+filename+format;
		
	}
	
	public static String getSharecontent(String id){
		return BASE_URL+"ContentsShares/ContentsShare?contentid="+id;
	}
	
	private static AsyncHttpClient client = new AsyncHttpClient();

	protected Context context;
	
	public BaseClient(Context context) {
		this.context = context;
	}
	
	static {
		client.setMaxConnections(10);
		client.setTimeout(10 * 1000);

	}

	private class ResponseHandler extends AsyncHttpResponseHandler {

		private HttpResponseHandler handler;

		private ResponseHandler(HttpResponseHandler handler) {

			this.handler = handler;

		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			
			handler.onError(500, "请求失败");
			requestHandler.onError(500, "请求失败");
			requestHandler.onComplete();
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
			
			requestHandler.onSuccess(handler.onSuccess(new String(arg2)));
			requestHandler.onComplete();
			/*String json = new String(arg2);
			if (json.startsWith("[")) {
				requestHandler.onSuccess(handler.onSuccess(json));
			} else {
				try {
					JSONObject object = new JSONObject(json);
					int error = object.getInt("err");
					if (error == 0) {
						String data = object.getString("data");
						requestHandler.onSuccess(handler.onSuccess(data));
						if (useOffline()) {
							OfflineDataField.create(new OfflineDataField(
									getOfflineHash(), data));
						}
					} else {
						handler.onError(error);
						requestHandler.onError(error);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/

		}

	}

	public enum Method {

		GET, POST
	}

	protected abstract RequestParams getParams();

	protected abstract String getUrl();

	protected abstract Method getMethod();

	public static interface RequestHandler {
		
		public void onComplete();
		
		public void onSuccess(Object object);

		public void onError(int error,String msg);

	}
	
	

	public static class SimpleRequestHandler implements RequestHandler {

		@Override
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub

		}

		



		@Override
		public void onComplete() {
			// TODO Auto-generated method stub
			
		}





		@Override
		public void onError(int error, String msg) {
			// TODO Auto-generated method stub
			
		}

	}

	private RequestHandler requestHandler;

	public void request(RequestHandler requestHandler) {
		
		this.requestHandler = requestHandler;
		
		if(!isOnline(context)){
			onError(501, "未发现网络");
			requestHandler.onError(501, "未发现网络");
			return;
		}
		
		Method method = getMethod();
		if (method == Method.GET) {
			get(getUrl(), getParams(), this);
		} else if (method == Method.POST) {
			post(getUrl(), getParams(), this);
		}
	}
	
	public static boolean isOnline(Context context) {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	private RequestHandle handle;

	private void get(String url, RequestParams params,
			HttpResponseHandler handler) {
		Header[] headers = fillHeaders();
		if(headers == null){
			handle = client.get(getAbsoluteUrl(url), params, new ResponseHandler(
					handler));
		}else{
			handle = client.get(context,getAbsoluteUrl(url),headers, params, new ResponseHandler(
					handler));
		}
		

	}

	private void post(String url, RequestParams params,
			HttpResponseHandler handler) {
		Header[] headers = fillHeaders();
		if(headers == null){
			handle = client.post(getAbsoluteUrl(url),params, new ResponseHandler(
					handler));
		}else{
			handle = client.post(context,getAbsoluteUrl(url),headers, params,contentType(), new ResponseHandler(
					handler));
		}
		
	}

	public void cancel(boolean mayInterruptIfRunning) {
		if (handle != null && !handle.isFinished()) {
			handle.cancel(mayInterruptIfRunning);
		}

	}
	
	protected Header[] fillHeaders(){
		return null;
	}
	
	protected boolean isRelativeUrl(){
		return true;
	}
	
	protected String contentType(){
		return "application/json";
	}

	private  String getAbsoluteUrl(String relativeUrl) {
		String url = "";
		if(isRelativeUrl()){
			url = BASE_URL + relativeUrl;
		}else{
			url = relativeUrl;
		}
		return url;
	}

	protected boolean useOffline() {
		return true;
	}

}
