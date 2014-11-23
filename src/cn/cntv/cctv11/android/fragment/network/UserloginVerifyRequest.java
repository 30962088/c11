package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import cn.cntv.cctv11.android.APP;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class UserloginVerifyRequest extends BaseClient{

	
	public static class Result{
		private int result;
		private Integer sid;
		private String pkey;
		public String getPkey() {
			return pkey;
		}
		public int getResult() {
			return result;
		}
		public Integer getSid() {
			return sid;
		}
		public void login(Context context){
			if(result == 1000){
				APP.getSession().login(""+sid, pkey);
			}
			
		}
	}
	
	public static class Params{
		private String phone;
		private String password;
		public Params(String phone, String password) {
			super();
			this.phone = phone;
			this.password = password;
		}
		
		
	}
	
	private Params params;
	
	

	public UserloginVerifyRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}


	@Override
	public Object onSuccess(String str) {
		
		Result result = new Gson().fromJson(str, Result.class);
		result.login(context);
		
		return result;
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
		params.add("phone", this.params.phone);
		params.add("password", ""+this.params.password);
		return params;
	}


	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/userloginVerify";
	}
	
	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}

}
