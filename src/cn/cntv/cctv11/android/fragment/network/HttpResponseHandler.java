package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

public interface HttpResponseHandler {
	public Object onSuccess(String str);
	public void onError(int error);
	public void onServerError(int arg0, Header[] arg1, byte[] arg2,Throwable arg3);
}
