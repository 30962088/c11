package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.content.Context;

public class GetProvinceCityRequest extends BaseClient{

	
	public static class Result{
		private int result;
		private String provincecity;
		public int getResult() {
			return result;
		}
		public String getProvincecity() {
			return provincecity;
		}
	}
	
	public static class Params{
		private double axisY;
		private double axisX;
		public Params(double axisX,double axisY) {
			this.axisY = axisY;
			this.axisX = axisX;
		}
		public double getAxisX() {
			return axisX;
		}
		public double getAxisY() {
			return axisY;
		}
		
		
	}
	
	private Params params;
	
	public GetProvinceCityRequest(Context context, Params params) {
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
		
		
	}


	@Override
	public void onServerError(int arg0, Header[] arg1, byte[] arg2,
			Throwable arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected RequestParams getParams() {
		RequestParams params = new RequestParams();
		params.add("axisX", ""+this.params.axisX);
		params.add("axisY", ""+this.params.axisY);
		return params;
	}


	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return "http://cctv11.1du1du.com/get.mvc/getprovincecity";
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
