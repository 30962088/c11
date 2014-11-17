package cn.cntv.cctv11.android.fragment;

import java.io.File;
import java.io.Serializable;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cntv.cctv11.android.BaseActivity;
import cn.cntv.cctv11.android.R;
import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.BaseActivity.OnCitySelectionListener;
import cn.cntv.cctv11.android.BaseActivity.OnGallerySelectionListener;
import cn.cntv.cctv11.android.utils.AliyunUtils;
import cn.cntv.cctv11.android.utils.AliyunUtils.UploadListener;
import cn.cntv.cctv11.android.utils.Dirctionary;
import cn.cntv.cctv11.android.utils.AliyunUtils.UploadResult;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow.OnItemClickListener;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FillInfoFragment extends BaseFragment implements OnClickListener,OnCitySelectionListener,
		OnGallerySelectionListener {

	public enum Sex {
		Female("female"), Male("male"), UnKouwn("unkouwn");
		private String code;
		private Sex(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
	}

	public static class Model implements Serializable {
		private Sex sex;
		private String nickname;
		private String sid;
		private String avatar;

		public Model(Sex sex, String nickname, String sid, String avatar) {
			super();
			this.sex = sex;
			this.nickname = nickname;
			this.sid = sid;
			this.avatar = avatar;
		}

	}

	public static FillInfoFragment newInstance(Model model) {
		FillInfoFragment fragment = new FillInfoFragment();
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		fragment.setArguments(args);
		return fragment;
	}

	private Model model;

	private EditText nicknameEditText;

	private RadioButton maleRadioButton;

	private RadioButton femaleRadioButton;

	private RadioGroup sexRadioGroup;

	private TextView cityTextView;

	private ImageView avatarImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		model = (Model) getArguments().getSerializable("model");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fill_info_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		avatarImageView = (ImageView) view.findViewById(R.id.avatar);
		maleRadioButton = (RadioButton) view.findViewById(R.id.male);
		femaleRadioButton = (RadioButton) view.findViewById(R.id.female);
		sexRadioGroup = (RadioGroup) view.findViewById(R.id.sexGroup);
		nicknameEditText = (EditText) view.findViewById(R.id.nickname);
		cityTextView = (TextView) view.findViewById(R.id.city);
		view.findViewById(R.id.avatar_setting).setOnClickListener(this);
		view.findViewById(R.id.cityBtn).setOnClickListener(this);
		view.findViewById(R.id.cancel).setOnClickListener(this);
		init();

	}

	private void init() {
		ImageLoader.getInstance().displayImage(model.avatar, avatarImageView,
				DisplayOptions.IMG.getOptions());
		nicknameEditText.setText(model.nickname);
		switch (model.sex) {
		case Female:
			femaleRadioButton.setChecked(true);
			break;
		case Male:
			maleRadioButton.setChecked(true);
			break;
		default:
			break;
		}
	}

	// private static final int ACTION_REQUEST_SELECTION = 3;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.avatar_setting:
			((BaseActivity) getActivity()).getPhoto(this);
			break;
		case R.id.cityBtn:
			((BaseActivity) getActivity()).getCity(this);
			break;
		case R.id.cancel:
			((MemberFragment)getParentFragment()).backFragment();
			break;
		default:
			break;
		}

	}
	private File uri2File(Uri uri) {  
        File file = null;  
        String[] proj = { MediaStore.Images.Media.DATA };  
        Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null,  
                null, null);  
        int actual_image_column_index = actualimagecursor  
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
        actualimagecursor.moveToFirst();  
        String img_path = actualimagecursor  
                .getString(actual_image_column_index);  
        file = new File(img_path);  
        return file;  
    }   
	@Override
	public void onGallerySelection(Uri uri) {
		
		AliyunUtils.getInstance().upload(uri2File(uri), new UploadListener() {
			
			@Override
			public void onsuccess(UploadResult result) {
				// TODO Auto-generated method stub
				
			}
		});
		ImageLoader.getInstance().displayImage(uri.toString(), avatarImageView,
				DisplayOptions.IMG.getOptions());

	}

	@Override
	public void onCitySelection(String city) {
		cityTextView.setText(city);
		
	}

}
