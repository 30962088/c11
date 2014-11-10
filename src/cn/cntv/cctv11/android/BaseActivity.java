package cn.cntv.cctv11.android;

import java.io.Serializable;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.utils.Dirctionary;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow.OnItemClickListener;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity implements Serializable{

	private Uri cameraPic;

	private static final int ACTION_REQUEST_GALLERY = 1;
	
	private static final int ACTION_REQUEST_CAMERA = 2;
	
	private static final int ACTION_REQUEST_CITY = 3;
	
	public static interface OnGallerySelectionListener{
		public void onGallerySelection(Uri uri);
	}
	
	public static interface OnCitySelectionListener{
		public void onCitySelection(String city);
	}
	
	private OnGallerySelectionListener onGallerySelectionListener;
	
	private OnCitySelectionListener onCitySelectionListener;
	
	public void getCity(OnCitySelectionListener onCitySelectionListener){
		this.onCitySelectionListener = onCitySelectionListener;
		Intent intent = new Intent(this, CityActivity.class);
		startActivityForResult(intent, ACTION_REQUEST_CITY);
	}
	
	public void getPhoto(OnGallerySelectionListener onGallerySelectionListener){
		this.onGallerySelectionListener = onGallerySelectionListener;
		new PhotoSelectPopupWindow(this,new OnItemClickListener() {
			
			@Override
			public void onItemClick(int id) {
				switch (id) {
				case R.id.take_photo:
					Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");
		            cameraPic = Uri.fromFile(Dirctionary.creatPicture());
		            getCameraImage.putExtra(MediaStore.EXTRA_OUTPUT, cameraPic);
		            startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);
					break;
				case R.id.read_photo:
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		            intent.setType("image/*");

		            Intent chooser = Intent.createChooser(intent, "从本地相册读取");
		            startActivityForResult(chooser, ACTION_REQUEST_GALLERY);
					break;
				default:
					break;
				}
			}
		},"设置头像");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_REQUEST_CITY:
			if (resultCode == Activity.RESULT_OK) { 
				if(onCitySelectionListener != null){
					onCitySelectionListener.onCitySelection(data.getStringExtra("city"));
				}
			}
			break;
		case ACTION_REQUEST_GALLERY:
			if (resultCode == Activity.RESULT_OK) { 
				if(onGallerySelectionListener != null){
					onGallerySelectionListener.onGallerySelection(data.getData());
				}
			}
			break;
		case ACTION_REQUEST_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				if(onGallerySelectionListener != null){
					onGallerySelectionListener.onGallerySelection(cameraPic);
				}
			}
			break;
		
		default:
			break;
		}
	}
	
}
