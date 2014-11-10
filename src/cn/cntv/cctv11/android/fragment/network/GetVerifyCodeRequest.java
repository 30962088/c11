package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetVerifyCodeRequest extends BaseClient{

	public static class Params{
		private String phone;
		private int type;
		public Params(String phone, int type) {
			super();
			this.phone = phone;
			this.type = type;
		}
		
	}
	
	public static class Result{
		private int result;
		private String code;
		public int getResult() {
			return result;
		}
		public String getCode() {
			return code;
		}
	}
	
	private Params params;
	
	

	public GetVerifyCodeRequest(Context context, Params params) {
		super(context);
		this.params = params;
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
		params.add("phone", this.params.phone);
		params.add("type", ""+this.params.type);
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/getVerifyCode";
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
	
	

}
