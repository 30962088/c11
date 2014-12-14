package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class InsertForumRequest extends BaseClient {

	public static class Result {
		
	}

	public static class Params {
		private String topicid;
		private String iscommentid = "0";
		private String issid = "0";
		private String sid;
		private String remark;

		public Params(String topicid, String iscommentid, String issid,
				String sid, String remark) {
			super();
			this.topicid = topicid;
			this.iscommentid = iscommentid;
			this.issid = issid;
			this.sid = sid;
			this.remark = remark;
		}

		public Params(String topicid, String sid, String remark) {
			super();
			this.topicid = topicid;
			this.sid = sid;
			this.remark = remark;
		}

	}

	private Params params;

	public InsertForumRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		return new Gson().fromJson(str, Result.class);

	}

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("method", "insertforumcomment");
		params.add("topicid", this.params.topicid);
		params.add("iscommentid", this.params.iscommentid);
		params.add("issid", this.params.issid);
		params.add("sid", this.params.sid);
		params.add("remark", this.params.remark);
		return params;
	}

	@Override
	protected String getUrl() {

		return "cctv11/insertforumcomment";
	}

	@Override
	protected Method getMethod() {

		return Method.POST;
	}

	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub
		
	}
}
