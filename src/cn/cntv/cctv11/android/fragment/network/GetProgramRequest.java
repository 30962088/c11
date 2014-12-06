package cn.cntv.cctv11.android.fragment.network;

import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetProgramRequest extends BaseClient{
	
	public static class Program{
		private String programcontent;
	}
	
	public static class Result{
		private int result;
		private List<Program> programlist;
		public String getContent(){
			return programlist.get(0).programcontent;
		}
		public int getResult() {
			return result;
		}
	}

	public GetProgramRequest(Context context) {
		super(context);
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
		params.add("pageno", "1");
		params.add("pagesize", "1");
		params.add("method", "program");
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "cctv11/program";
	}

	@Override
	protected Method getMethod() {
		// TODO Auto-generated method stub
		return Method.POST;
	}
	
	
	
}
