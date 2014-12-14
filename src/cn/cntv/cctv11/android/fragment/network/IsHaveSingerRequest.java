package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import cn.cntv.cctv11.android.APP;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class IsHaveSingerRequest extends BaseClient{

	
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
			APP.getSession().login(""+sid, pkey);
		}
	}
	
	public static class Params{
		private String wbqqid;
		private int type;
		public Params(String wbqqid, int type) {
			super();
			this.wbqqid = wbqqid;
			this.type = type;
		}
		
	}
	
	private Params params;
	
	

	public IsHaveSingerRequest(Context context, Params params) {
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
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("wbqqid", this.params.wbqqid);
		params.add("type", ""+this.params.type);
		return params;
	}


	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/isHaveSinger";
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


	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub
		
	}

}
