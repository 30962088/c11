package com.cctv.xiqu.android.fragment.network;

import com.cctv.xiqu.android.APP;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class UpdateContentCommentRequest extends BaseClient {
	public static class Params {
		private String sid;
		private String pkey;
		private String commentid;

		public Params(String sid, String pkey, String commentid) {
			super();
			this.sid = sid;
			this.pkey = pkey;
			this.commentid = commentid;
		}
	}

	private Params params;

	public UpdateContentCommentRequest(Context context, Params params) {
		super(context);
		this.params = params;
	}

	@Override
	public Object onSuccess(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onError(int error, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("sid", this.params.sid);
		params.add("pkey", this.params.pkey);
		params.add("commentid", this.params.commentid);
		return params;
	}

	@Override
	protected String getURL() {
		// TODO Auto-generated method stub
		return APP.getAppConfig().getRequest_news()
				+ "cctv11/UpdateContentComment";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}

}
