package cn.cntv.cctv11.android.fragment.network;

import org.apache.http.Header;

public interface HttpResponseHandler {
	public Object onSuccess(String str);
	public void onError(int error,String msg);
}
