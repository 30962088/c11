package cn.cntv.cctv11.android.fragment;

import java.io.Serializable;

import cn.cntv.cctv11.android.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSettingFragment extends BaseFragment implements
		OnClickListener {

	public static class Model implements Serializable {
		private String nickname;
		private String avatar;
		private String city;
		private String phone;

		public Model(String nickname, String avatar, String city, String phone) {
			super();
			this.nickname = nickname;
			this.avatar = avatar;
			this.city = city;
			this.phone = phone;
		}

		// private String passowrd;

	}

	public static UserSettingFragment newInstance(Model model) {
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		UserSettingFragment fragment = new UserSettingFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.user_setting_layout, null);
	}

	private ImageView avatar;

	private TextView city;

	private View weiboBinding;

	private View weiboUnBinding;

	private TextView nickname;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		avatar = (ImageView) view.findViewById(R.id.avatar);
		city = (TextView) view.findViewById(R.id.city);
		weiboBinding = view.findViewById(R.id.weibo_binding);
		weiboUnBinding = view.findViewById(R.id.weibo_unbinding);
		nickname = (TextView) view.findViewById(R.id.nickname);
		view.findViewById(R.id.account_btn).setOnClickListener(this);
		view.findViewById(R.id.avatar_btn).setOnClickListener(this);
		view.findViewById(R.id.city_btn).setOnClickListener(this);
		view.findViewById(R.id.phone_btn).setOnClickListener(this);
		view.findViewById(R.id.pwd_btn).setOnClickListener(this);
		view.findViewById(R.id.setting_btn).setOnClickListener(this);
		view.findViewById(R.id.logout).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.account_btn:

			break;
		case R.id.avatar_btn:

			break;
		case R.id.city_btn:

			break;
		case R.id.phone_btn:

			break;
		case R.id.pwd_btn:

			break;
		case R.id.setting_btn:

			break;
		case R.id.logout:

			break;
		default:
			break;
		}

	}

}
