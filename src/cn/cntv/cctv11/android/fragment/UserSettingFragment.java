package cn.cntv.cctv11.android.fragment;


import java.io.File;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.BaseActivity;
import cn.cntv.cctv11.android.BaseActivity.OnCitySelectionListener;
import cn.cntv.cctv11.android.BaseActivity.OnModifyPasswordListener;
import cn.cntv.cctv11.android.BaseActivity.OnModifyPhoneListener;
import cn.cntv.cctv11.android.BaseActivity.OnNicknameFillListener;
import cn.cntv.cctv11.android.APP;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.BaseActivity.OnGallerySelectionListener;
import cn.cntv.cctv11.android.fragment.network.BaseClient;
import cn.cntv.cctv11.android.fragment.network.GetSingerInfoRequest;
import cn.cntv.cctv11.android.fragment.network.UpdateSingerInfoRequest;
import cn.cntv.cctv11.android.utils.AliyunUtils;
import cn.cntv.cctv11.android.utils.AliyunUtils.UploadListener;
import cn.cntv.cctv11.android.utils.AliyunUtils.UploadResult;
import cn.cntv.cctv11.android.utils.CropImageUtils;
import cn.cntv.cctv11.android.utils.LoadingPopup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSettingFragment extends BaseFragment implements
		OnClickListener,OnGallerySelectionListener,UploadListener,OnCitySelectionListener,OnNicknameFillListener
		,OnModifyPhoneListener,OnModifyPasswordListener{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static UserSettingFragment newInstance(String sid) {
		Bundle args = new Bundle();
		args.putString("sid", sid);
		UserSettingFragment fragment = new UserSettingFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sid = getArguments().getString("sid");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.user_setting_layout, null);
	}
	
	private String sid;

	private ImageView avatar;

	private TextView city;

	private View weiboBinding;

	private View weiboUnBinding;

	private TextView nickname;
	
	private View phoneContainer;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		avatar = (ImageView) view.findViewById(R.id.avatar);
		
		city = (TextView) view.findViewById(R.id.city);
		
		weiboBinding = view.findViewById(R.id.weibo_binding);
		weiboUnBinding = view.findViewById(R.id.weibo_unbinding);
		nickname = (TextView) view.findViewById(R.id.nickname);
		phoneContainer = view.findViewById(R.id.phone_container);
		view.findViewById(R.id.account_btn).setOnClickListener(this);
		view.findViewById(R.id.avatar_btn).setOnClickListener(this);
		view.findViewById(R.id.city_btn).setOnClickListener(this);
		view.findViewById(R.id.phone_btn).setOnClickListener(this);
		view.findViewById(R.id.pwd_btn).setOnClickListener(this);
		view.findViewById(R.id.setting_btn).setOnClickListener(this);
		view.findViewById(R.id.logout).setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GetSingerInfoRequest request = new GetSingerInfoRequest(getActivity(), 
				new GetSingerInfoRequest.Params(sid));
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onSuccess(Object object) {
				GetSingerInfoRequest.Result result = (GetSingerInfoRequest.Result)object;
				city.setText(result.getModels().getAddress());
				ImageLoader.getInstance().displayImage(result.getModels().getSingerimgurl(), avatar,DisplayOptions.IMG.getOptions());
				nickname.setText(result.getModels().getSingername());
				String accessToken = APP.getSession().getWeiboAccessToken();
				if(accessToken == null){
					weiboBinding.setVisibility(View.GONE);
					weiboUnBinding.setVisibility(View.VISIBLE);
				}else{
					weiboBinding.setVisibility(View.VISIBLE);
					weiboUnBinding.setVisibility(View.GONE);
				}
				if(result.isPhoneLogin()){
					phoneContainer.setVisibility(View.VISIBLE);
				}else{
					phoneContainer.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.account_btn:
			((BaseActivity) getActivity()).getNickname(nickname.getText().toString(), this);
			break;
		case R.id.avatar_btn:
			((BaseActivity) getActivity()).getPhoto(this);
			break;
		case R.id.city_btn:
			((BaseActivity) getActivity()).getCity(this);
			break;
		case R.id.phone_btn:
			((BaseActivity) getActivity()).modifyPhone(this);
			break;
		case R.id.pwd_btn:
			((BaseActivity) getActivity()).modifyPassowrd(this);
			break;
		case R.id.setting_btn:

			break;
		case R.id.logout:
			onlogout();
			break;
		default:
			break;
		}

	}

	private void onlogout() {
		APP.getSession().logout();
		((MemberFragment) getParentFragment()).initFragment(LoginFragment.newInstance());
		
	}

	@Override
	public void onGallerySelection(File file) {
		
		AliyunUtils.getInstance().upload(CropImageUtils.cropImage(file, 300, 300),this);
		
	}

	@Override
	public void onsuccess(final UploadResult result) {
		UpdateSingerInfoRequest request = new UpdateSingerInfoRequest(getActivity(), 
				new UpdateSingerInfoRequest.Params(sid, result.getGuid(), result.getExt()));
		LoadingPopup.show(getActivity());
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onComplete() {
				LoadingPopup.hide(getActivity());
			}
			@Override
			public void onSuccess(Object object) {
				onResume();
			}
		});
		
	}

	@Override
	public void onCitySelection(String city) {
		UpdateSingerInfoRequest request = new UpdateSingerInfoRequest(getActivity(), 
				new UpdateSingerInfoRequest.Params(sid, city));
		LoadingPopup.show(getActivity());
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onComplete() {
				LoadingPopup.hide(getActivity());
			}
			@Override
			public void onSuccess(Object object) {
				onResume();
			}
		});
		
	}

	@Override
	public void onNicknameFill(String nickname) {
		
		UpdateSingerInfoRequest request = new UpdateSingerInfoRequest(getActivity(), 
				new UpdateSingerInfoRequest.Params(sid, new UpdateSingerInfoRequest.Singername(nickname)));
		LoadingPopup.show(getActivity());
		request.request(new BaseClient.SimpleRequestHandler(){
			@Override
			public void onComplete() {
				LoadingPopup.hide(getActivity());
			}
			@Override
			public void onSuccess(Object object) {
				onResume();
			}
		});
	}

	@Override
	public void onModifyPhone(String phone) {
		
		
	}

	@Override
	public void onModifyPassword(String password) {
		// TODO Auto-generated method stub
		
	}

}
