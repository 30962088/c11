package cn.cntv.cctv11.android.fragment;


import java.io.IOException;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.handmark.pulltorefresh.library.extras.PullToRefreshWebView2;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetProgramRequest;
import cn.cntv.cctv11.android.utils.HtmlUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ProgramFragment extends BaseFragment{
	
	
	public static ProgramFragment newInstance(){
		return new ProgramFragment();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.program_layout, null);
	}
	
	private WebView webView;

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		webView = (WebView) view.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		request();
	}
	
	private void request(){
		GetProgramRequest request = new GetProgramRequest(getActivity());
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onSuccess(Object object) {
				GetProgramRequest.Result result = (GetProgramRequest.Result)object;
				if(result.getResult() == 1000){
					String html;
					try {
						html = HtmlUtils.getHtml(getActivity(), "template.html", result.getContent());
						webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
	}

	
	
	
}
