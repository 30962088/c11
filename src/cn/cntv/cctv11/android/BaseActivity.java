package cn.cntv.cctv11.android;

import java.io.File;
import java.io.Serializable;

import cn.cntv.cctv11.android.APP.DisplayOptions;
import cn.cntv.cctv11.android.utils.Dirctionary;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow;
import cn.cntv.cctv11.android.widget.PhotoSelectPopupWindow.OnItemClickListener;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity implements Serializable{

	private Uri cameraPic;

	private static final int ACTION_REQUEST_GALLERY = 1;
	
	private static final int ACTION_REQUEST_CAMERA = 2;
	
	private static final int ACTION_REQUEST_CITY = 3;
	
	public static interface OnGallerySelectionListener{
		public void onGallerySelection(File file);
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
	
	public static String convertMediaUriToPath(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		cursor.moveToFirst();
		String document_id = cursor.getString(0);
		document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
		cursor.close();

		cursor = context.getContentResolver().query(
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				null, MediaStore.Images.Media._ID + " = ? ",
				new String[] { document_id }, null);
		cursor.moveToFirst();
		String path = cursor.getString(cursor
				.getColumnIndex(MediaStore.Images.Media.DATA));
		cursor.close();

		return path;
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
					File file = new File(convertMediaUriToPath(this, data.getData()));
					onGallerySelectionListener.onGallerySelection(file);
				}
			}
			break;
		case ACTION_REQUEST_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				if(onGallerySelectionListener != null){
					File file = new File(convertMediaUriToPath(this, cameraPic));
					onGallerySelectionListener.onGallerySelection(file);
				}
			}
			break;
		
		default:
			break;
		}
	}
	
}
