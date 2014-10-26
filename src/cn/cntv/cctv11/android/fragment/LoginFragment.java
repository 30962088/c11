package cn.cntv.cctv11.android.fragment;

import cn.cntv.cctv11.android.R;
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
	public void onSuccess(Result params) {
		System.out.println(params);
		
	}

}
