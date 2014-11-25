package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetWeiboUserRequest extends BaseClient{

	public static class Params{
		private String access_token;
		private String uid;
		public Params(String access_token, String uid) {
			super();
			this.access_token = access_token;
			this.uid = uid;
		}
		
	}
	
	private Params params;

	public GetWeiboUserRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}
	
	public static class Result{
		private String screen_name;
		public String getScreen_name() {
			return screen_name;
		}
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(str, Result.class);
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
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("access_token", "" + this.params.access_token);
		params.add("uid", "" + this.params.uid);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "https://api.weibo.com/2/users/show.json";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
	
	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}
	
}