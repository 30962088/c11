package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import cn.cntv.cctv11.android.utils.Preferences.Session;

public class SetSingerUserRequest extends BaseClient{
	
	
	public static class Params{
		private String singername;
		private String sex;
		private String singerimgguid;
		private String singerimgformat;
		private String address;
		private String phone;
		private String password;
		public Params(String singername, String sex, String singerimgguid,
				String singerimgformat, String address, String phone,
				String password) {
			super();
			this.singername = singername;
			this.sex = sex;
			this.singerimgguid = singerimgguid;
			this.singerimgformat = singerimgformat;
			this.address = address;
			this.phone = phone;
			this.password = password;
		}
		
	}
	
	public static class Result{
		private String pkey;
		private String result;
		private String sid;
		public Result(String pkey, String result, String sid) {
			super();
			this.pkey = pkey;
			this.result = result;
			this.sid = sid;
		}
		public void login(Context context){
			Session session = new Session(context);
			session.login(sid, pkey);
		}
	}
	
	private Params params;

	public SetSingerUserRequest(Context context, Params params) {
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
		params.add("singername", this.params.singername);
		params.add("sex", this.params.sex);
		params.add("singerimgguid", "" + this.params.singerimgguid);
		params.add("singerimgformat", "" + this.params.singerimgformat);
		params.add("address", "" + this.params.address);
		params.add("phone", "" + this.params.phone);
		params.add("password", "" + this.params.password);
		return params;
	}
	
	@Override
	protected boolean isRelativeUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/setSingerUser";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}
	
	
	
	
}
