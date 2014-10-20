package cn.cntv.cctv11.android.fragment.network;


import java.util.Date;

import org.apache.http.Header;

import android.content.Context;
import cn.cntv.cctv11.android.SpecialDetailActivity;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class DescripitionRequest extends BaseClient{

	public static class Result {
		private String contentsid;
		private String contentstitle;
		private String poemauthor;
		private String contentsdate;
		private String description;
		private String videositeurl;
		private String commentcount;
		public SpecialDetailActivity.Model toDetailModel(){
			return new SpecialDetailActivity.Model(description);
		}
	}
	
	
	private String contentsid;
	

	public DescripitionRequest(Context context, String contentsid) {
		super(context);
		this.contentsid = contentsid;
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
		// TODO Auto-generated method stub

	}

	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("contentsid", contentsid);
		params.add("method", "description");
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/description";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.GET;
	}
}
