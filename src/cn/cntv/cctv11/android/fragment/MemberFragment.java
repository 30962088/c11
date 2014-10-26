package cn.cntv.cctv11.android.fragment;

import cn.cntv.cctv11.android.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;

public class MemberFragment extends BaseFragment {
	public static MemberFragment newInstance() {
		return new MemberFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.member_layout, null);
	}

	public void backFragment() {
		getChildFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		getChildFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, LoginFragment.newInstance())
				.commit();
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					backFragment();
					return true;
				} else {
					return false;
				}
			}
		});
	}

}
