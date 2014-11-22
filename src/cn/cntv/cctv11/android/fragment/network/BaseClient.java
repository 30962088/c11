package cn.cntv.cctv11.android.fragment.network;


import org.apache.http.Header;

import android.content.Context;

import com.loopj.android.http.*;

public abstract class BaseClient implements HttpResponseHandler {
	
	public static final String BASE_URL = "http://cctv11news.1du1du.com:8081/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	protected Context context;
	
	public BaseClient(Context context) {
		this.context = context;
	}
	
	static {
		client.setMaxConnections(10);
		client.setTimeout(30 * 1000);

	}

	private class ResponseHandler extends AsyncHttpResponseHandler {

		private HttpResponseHandler handler;

		private ResponseHandler(HttpResponseHandler handler) {

			this.handler = handler;

		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			
			handler.onServerError(arg0, arg1, arg2, arg3);
			requestHandler.onServerError(arg0, arg1, arg2, arg3);
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

		public void onError(int error);

		public void onServerError(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3);

	}
	
	

	public static class SimpleRequestHandler implements RequestHandler {

		@Override
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(int error) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServerError(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub
			
		}

	}

	private RequestHandler requestHandler;

	public void request(RequestHandler requestHandler) {
		/*if (useOffline()) {

			OfflineDataField dataField = OfflineDataField
					.getOffline(getOfflineHash());
			if (dataField != null) {
				requestHandler.onSuccess(onSuccess(dataField.getData()));
				return;
			}

		}*/
		this.requestHandler = requestHandler;
		Method method = getMethod();
		if (method == Method.GET) {
			get(getUrl(), getParams(), this);
		} else if (method == Method.POST) {
			post(getUrl(), getParams(), this);
		}
	}

	private String getOfflineHash() {
		String hash = getUrl();
		RequestParams params = getParams();
		if (params != null) {
			hash += params.toString();
		}
		return "" + hash.hashCode();
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
