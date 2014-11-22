package cn.cntv.cctv11.android.fragment;

import org.apache.http.Header;

import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.fragment.FillInfoFragment.Model;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.IsHaveSingerRequest;
import cn.cntv.cctv11.android.utils.OauthUtils;
import cn.cntv.cctv11.android.utils.OauthUtils.OauthCallback;
import cn.cntv.cctv11.android.utils.OauthUtils.Result;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class LoginFragment extends BaseFragment implements OnClickListener,OauthCallback {

	public static LoginFragment newInstance() {
		return new LoginFragment();
	}

	private OauthUtils oauthUtils;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		oauthUtils = new OauthUtils(getActivity());
		oauthUtils.setOauthCallback(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.login_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.signin).setOnClickListener(this);
		view.findViewById(R.id.weibo_btn).setOnClickListener(this);
		view.findViewById(R.id.qq_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin:
			getParentFragment()
					.getChildFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container,
							SigninFragment.newInstance())
					.addToBackStack("sigin").commit();
			break;
		case R.id.weibo_btn:
			oauthUtils.sinaOauth();
			break;
		case R.id.qq_btn:
			oauthUtils.qqOauth();
			break;
		default:
			break;
		}

	}

	@Override
	public void onSuccess(Result result) {
		final Model model = result.toModel();
		IsHaveSingerRequest request = new IsHaveSingerRequest(getActivity(), result.toParams());
		request.request(new RequestHandler() {
			
			@Override
			public void onSuccess(Object object) {
				IsHaveSingerRequest.Result result = (IsHaveSingerRequest.Result)object;
//				if(result.getPkey() == null){
					getParentFragment()
					.getChildFragmentManager()
					.beginTransaction()
					.replace(R.id.fragment_container,
							FillInfoFragment.newInstance(model))
					.addToBackStack("fillinfo").commit();
//				}
				
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
		
	}

}
