package cn.cntv.cctv11.android.fragment.network;



import org.apache.http.Header;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class InsertCommentRequest extends BaseClient {

	public static class Result {
		
	}
	
	public static class Params {
		private String contentsid;
		private String iscommentid;
		private String issid;
		private String sid;
		private String remark;
		public Params(String contentsid, String iscommentid, String issid,
				String sid, String remark) {
			super();
			this.contentsid = contentsid;
			this.iscommentid = iscommentid;
			this.issid = issid;
			this.sid = sid;
			this.remark = remark;
		}

	}
	
	private Params params;
	

	

	public InsertCommentRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		return new Gson().fromJson(str, Result.class);

	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onServerError(int arg0, Header[] arg1, byte[] arg2,
			Throwable arg3) {
		

	}
	
	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "insertcomment");
		params.add("contentsid", this.params.contentsid);
		params.add("iscommentid", this.params.iscommentid);
		params.add("issid", this.params.issid);
		params.add("sid", this.params.sid);
		params.add("remark", this.params.remark);
		return params;
	}

	@Override
	protected String getUrl() {
		
		return "cctv11/insertcomment";
	}

	@Override
	protected Method getMethod() {
		
		return Method.POST;
	}
}
