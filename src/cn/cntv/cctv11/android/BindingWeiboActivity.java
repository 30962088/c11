package cn.cntv.cctv11.android;

import org.apache.http.Header;


import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.GetWeiboUserRequest;
import cn.cntv.cctv11.android.utils.OauthUtils;
import cn.cntv.cctv11.android.utils.OauthUtils.OauthCallback;
import cn.cntv.cctv11.android.utils.OauthUtils.Result;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BindingWeiboActivity extends BaseActivity implements OnClickListener,OauthCallback{
	
	private View bindingBtn;
	
	private TextView nicknameView;
	
	private View unbindingView;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_weibo_binding);
		bindingBtn = findViewById(R.id.binding_btn);
		unbindingView = findViewById(R.id.unbinding_view);
		findViewById(R.id.back).setOnClickListener(this);
		bindingBtn.setOnClickListener(this);
		nicknameView = (TextView) findViewById(R.id.nickname);
		findViewById(R.id.unbinding_btn).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.binding_btn:
			onbinding();
			break;
		case R.id.unbinding_btn:
			onunbinding();
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
		
	}
	private void onunbinding() {
		APP.getSession().clearWeibo();
		onResume();
		
	}
	private void onbinding() {
		OauthUtils oauthUtils = new OauthUtils(this);
		oauthUtils.setOauthCallback(this);
		oauthUtils.sinaOauth();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		String accessToken = APP.getSession().getWeiboAccessToken();
		String uid = APP.getSession().getWeiboUid();
		if(accessToken != null){
			unbindingView.setVisibility(View.VISIBLE);
			bindingBtn.setVisibility(View.GONE);
			GetWeiboUserRequest request = new GetWeiboUserRequest(this, 
					new GetWeiboUserRequest.Params(accessToken, uid));
			request.request(new RequestHandler() {
				
				@Override
				public void onSuccess(Object object) {
					GetWeiboUserRequest.Result result = (GetWeiboUserRequest.Result)object;
					nicknameView.setText(result.getScreen_name());
				}
				
				@Override
				public void onServerError(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError(int error) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete() {
					// TODO Auto-generated method stub
					
				}
			});
		}else{
			unbindingView.setVisibility(View.GONE);
			bindingBtn.setVisibility(View.VISIBLE);
		}
		
	}
	
	@Override
	public void onSuccess(Result params) {
		onResume();
	}
	
	
	
}