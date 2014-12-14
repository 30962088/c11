package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class UpdateSingeruserInfoRequest extends BaseClient{

	public static class Params{
		private String pkey;
		private String sid;
		private String oldpassword;
		private String newpassword;
		public Params(String pkey, String sid, String oldpassword,
				String newpassword) {
			super();
			this.pkey = pkey;
			this.sid = sid;
			this.oldpassword = oldpassword;
			this.newpassword = newpassword;
		}
		
	}
	
	public static class Result{
		private int result;
		public int getResult() {
			return result;
		}
	}
	
	private Params params;

	public UpdateSingeruserInfoRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(str, Result.class);
	}

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("sid", this.params.sid);
		params.add("pkey", this.params.pkey);
		if(this.params.oldpassword != null){
			params.add("oldpassword", ""+this.params.oldpassword);
		}
		if(this.params.newpassword != null){
			params.add("newpassword", ""+this.params.newpassword);
		}
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/updateSingeruserInfo";
	}

	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}

	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
