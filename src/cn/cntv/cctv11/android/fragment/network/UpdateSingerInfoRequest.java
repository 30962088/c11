package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;

import android.content.Context;

public class UpdateSingerInfoRequest extends BaseClient{

	public static class Singername{
		private String name;

		public Singername(String name) {
			super();
			this.name = name;
		}
		
	}
	
	public static class Params{
		private String sid;
		private String singerimgguid;
		private String singerimgformat;
		private String address;
		private String singername;
		public Params(String sid, String singerimgguid, String singerimgformat) {
			super();
			this.sid = sid;
			this.singerimgguid = singerimgguid;
			this.singerimgformat = singerimgformat;
		}
		public Params(String sid, String address) {
			super();
			this.sid = sid;
			this.address = address;
		}
		public Params(String sid, Singername singername) {
			super();
			this.sid = sid;
			this.singername = singername.name;
		}
		
		
	}
	
	
	private Params params;

	public UpdateSingerInfoRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
		return null;
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
		params.add("sid", this.params.sid);
		if(this.params.singerimgguid != null){
			params.add("singerimgguid", ""+this.params.singerimgguid);
		}
		if(this.params.singerimgformat != null){
			params.add("singerimgformat", this.params.singerimgformat);
		}
		if(this.params.address != null){
			params.add("address", this.params.address);
		}
		if(this.params.singername != null){
			params.add("singername", this.params.singername);
		}
		
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/updateSingerInfo";
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
