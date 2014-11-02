package cn.cntv.cctv11.android.fragment.network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;

import cn.cntv.cctv11.android.adapter.LiveListAdapter;
import cn.cntv.cctv11.android.adapter.LiveListAdapter.Model.State;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class GetLiveUrlRequest extends BaseClient {

	
	
	public static class HLSURL{
		private String hls1;
		private String hls2;
		private String hls3;
		private String hls4;
		private String hls5;
		public String[] toList(){
			return new String[]{hls4,hls3,hls5,hls2,hls1};
		}
	}

	public static class Result {
		private HLSURL hls_url;
		public HLSURL getHls_url() {
			return hls_url;
		}
	}

	public GetLiveUrlRequest(Context context) {
		super(context);
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
		params.add("client", "androidapp");
		params.add("channel", "pa://cctv_p2p_hdcctv11");
		return params;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://vdn.live.cntv.cn/api2/live.do";
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
