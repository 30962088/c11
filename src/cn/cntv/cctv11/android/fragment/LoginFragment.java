package cn.cntv.cctv11.android.fragment;

import org.apache.http.Header;

import com.mengle.lib.utils.Utils;

import cn.cntv.cctv11.android.APP;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.fragment.FillInfoFragment.Model;
import cn.cntv.cctv11.android.fragment.network.BaseClient.RequestHandler;
import cn.cntv.cctv11.android.fragment.network.BaseClient.SimpleRequestHandler;
import cn.cntv.cctv11.android.fragment.network.IsHaveSingerRequest;
import cn.cntv.cctv11.android.fragment.network.UserloginVerifyRequest;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import cn.cntv.cctv11.android.utils.OauthUtils;
import cn.cntv.cctv11.android.utils.OauthUtils.OauthCallback;
import cn.cntv.cctv11.android.utils.OauthUtils.Result;
import cn.cntv.cctv11.android.utils.RegexUtils;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginFragment extends BaseFragment implements OnClickListener,
		OauthCallback {

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

	
	private EditText phoneText;
	
	private EditText passwordText;
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		phoneText = (EditText) view.findViewById(R.id.phone);
		passwordText = (EditText) view.findViewById(R.id.password);
		view.findViewById(R.id.signin).setOnClickListener(this);
		view.findViewById(R.id.weibo_btn).setOnClickListener(this);
		view.findViewById(R.id.qq_btn).setOnClickListener(this);
		view.findViewById(R.id.login_btn).setOnClickListener(this);
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
		case R.id.login_btn:
			onlogin();
			break;
		default:
			break;
		}

	}

	private void onlogin() {
		String phone = phoneText.getText().toString();
		String password  = passwordText.getText().toString();
		if(TextUtils.isEmpty(phone)){
			Utils.tip(getActivity(), "请输入手机号码");
			return;
		}
		if(TextUtils.isEmpty(password)){
			Utils.tip(getActivity(), "请输入密码");
			return;
		}
		if(!RegexUtils.checkPhone(phone)){
			Utils.tip(getActivity(), "手机号码格式错误");
			return;
		}
		
		UserloginVerifyRequest request = new UserloginVerifyRequest(getActivity(), 
				new UserloginVerifyRequest.Params(phone, password));
		
		LoadingPopup.show(getActivity());
		request.request(new SimpleRequestHandler(){
			@Override
			public void onComplete() {
				LoadingPopup.hide(getActivity());
			}
			@Override
			public void onSuccess(Object object) {
				UserloginVerifyRequest.Result result = (UserloginVerifyRequest.Result)object;
				if(result.getResult() == 1000){
					((MemberFragment) getParentFragment()).initFragment(UserSettingFragment.newInstance(APP.getSession().getSid()));
				}else{
					Utils.tip(getActivity(), "用户名或密码错误");
				}
				
			}
		});
		
		
		
	}

	@Override
	public void onSuccess(Result result) {
		final Model model = result.toModel();
		IsHaveSingerRequest request = new IsHaveSingerRequest(getActivity(),
				result.toParams());
		request.request(new RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				IsHaveSingerRequest.Result result = (IsHaveSingerRequest.Result) object;
				Fragment fragment;
				if (result.getPkey() == null) {
					fragment = FillInfoFragment.newInstance(model);
				} else {
					fragment = UserSettingFragment.newInstance(""
							+ result.getSid());
				}
				getParentFragment().getChildFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_container, fragment)
						.addToBackStack("fillinfo").commit();
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
